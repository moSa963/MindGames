package com.example.mindgames.numbers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mindgames.R;
import com.example.mindgames.utils.Effects;
import com.example.mindgames.utils.Table;

import java.util.ArrayList;
import java.util.Random;

public abstract class Gui extends Generator {
    private final Table table;
    private ArrayList<Integer> cells;
    private boolean userInput;

    public Gui(ViewGroup container) {
        userInput = false;
        table = new Table(container.getContext(), 10, 5);
        table.createNewTable(10, 5, 15, true);
        container.removeAllViews();
        container.addView(table.getTable());
        newRound();
    }

    @Override
    public void onUserInput(boolean gameOver, boolean roundEnded, int index) {
        if (!gameOver) {
            borderAnimation(table.cellAt(index), true);
            Animator a = flipAnimation(table.cellAt(index), false)
                    .setDuration(300);
            if (roundEnded) {
                userInput = false;
                a.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        onRoundEnded();
                    }
                });
            }

            a.start();
        } else {
            Effects.vibrate(table.getContext());
            borderAnimation(table.cellAt(index), false);
            Animator a = flipAnimation(table.cellAt(index), false)
                    .setDuration(300);
            a.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    onGameOver(level);
                }
            });
            a.start();
        }
    }

    @Override
    public void onNewRoundGenerated(int count) {
        newCells(count);

    }

    public void onRoundEnded() {
        ArrayList<FrameLayout> f = new ArrayList<>();

        for (Integer cell : cells) {
            f.add(table.cellAt(cell));
        }
        clearAllItems(f);
    }

    @Override
    public void onFirstClick(int index) {
        ArrayList<FrameLayout> f = new ArrayList<>();

        for (Integer cell : cells) {
            if (cell != index) {
                f.add(table.cellAt(cell));
            }
        }
        hideAll(f).start();
    }

    private void newCells(int cellsCount) {
        cells = new ArrayList<>(cellsCount);
        ArrayList<FrameLayout> f = new ArrayList<>();
        int counter = 1;
        Random rand = new Random();

        while (counter <= cellsCount) {
            Integer newIndex = rand.nextInt(50);

            if (!cells.contains(newIndex)) {
                cells.add(newIndex);
                f.add(addCell(newIndex, counter));
                ++counter;
            }
        }

        Animator set = ScaleAnimation(f, true);
        set.setDuration(500);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                userInput = true;
            }
        });

        set.start();
    }

    private FrameLayout addCell(int index, int number) {
        FrameLayout container = table.cellAt(index);

        View newObj = ConstraintLayout.inflate(
                table.getContext(),
                R.layout.numbers_item,
                table.cellAt(index)
        );

        ((TextView) newObj.findViewById(R.id.text_number)).setText(number + "");

        container.findViewById(R.id.root).setOnClickListener(v -> {
            cellClicked(v, index, number);
        });

        cells.add(index);
        return container;
    }

    private void cellClicked(View v, int index, int number) {
        if (userInput) {
            userInput(index, number);
        }
    }

    private void clearAllItems(ArrayList<FrameLayout> cells) {
        Animator set = ScaleAnimation(cells, false);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                for (FrameLayout f : cells) {
                    f.removeAllViews();
                }
                newRound();
            }
        });
        set.setDuration(500);
        set.start();
    }

    private Animator ScaleAnimation(ArrayList<FrameLayout> cells, boolean revers) {
        ArrayList<Animator> animatorArrayList = new ArrayList<>();

        for (FrameLayout cell : cells) {
            FrameLayout layout = cell.findViewById(R.id.root);

            if (revers) {
                animatorArrayList.add(ObjectAnimator.ofFloat(layout, "ScaleX",
                        0.0f, 0.2f, 0.1f, 1.0f));
                animatorArrayList.add(ObjectAnimator.ofFloat(layout, "ScaleY",
                        0.0f, 0.2f, 0.1f, 1.0f));
            } else {
                animatorArrayList.add(ObjectAnimator.ofFloat(layout, "ScaleX",
                        1.0f, 0.1f, 0.2f, 0.0f));
                animatorArrayList.add(ObjectAnimator.ofFloat(layout, "ScaleY",
                        1.0f, 0.1f, 0.2f, 0.0f));
            }
        }

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorArrayList);
        return set;
    }

    private Animator flipAnimation(FrameLayout cell, boolean revers) {
        FrameLayout layout = cell.findViewById(R.id.frame);
        TextView textView = layout.findViewById(R.id.text_number);
        cell.setClickable(false);

        float alpha;
        float[] nums;

        if (revers) {
            alpha = 0.0f;
            nums = new float[]{0.0f, 90.0f, 90.0f, 180.0f, 200.0f, 180.0f};
        } else {

            alpha = 1.0f;
            nums = new float[]{180.0f, 200.0f, 200.0f, 180.0f, 90.0f, 0.0f};
        }

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(layout, "RotationX",
                nums[0], nums[1]);

        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                textView.setAlpha(alpha);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(layout, "RotationX",
                nums[2], nums[3], nums[4], nums[5]);

        AnimatorSet set = new AnimatorSet();

        set.playSequentially(animator1, animator2);


        return set;
    }

    //run a stroke animation
    private void borderAnimation(FrameLayout cell, boolean rightAnswer) {
        FrameLayout layout = cell.findViewById(R.id.root);

        //set the proper drawable as background
        if (rightAnswer) {
            layout.setBackgroundResource(R.drawable.stroke_circle_1);
        } else {
            layout.setBackgroundResource(R.drawable.stroke_circle_2);
        }

        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) layout.getBackground();
        avd.start();
    }

    private AnimatorSet hideAll(ArrayList<FrameLayout> cells) {
        ArrayList<Animator> arraySet = new ArrayList<>();

        for (FrameLayout cell : cells) {
            arraySet.add(flipAnimation(cell, true));
        }

        AnimatorSet set = new AnimatorSet();

        set.playTogether(arraySet);

        return set;
    }
}