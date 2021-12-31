package com.example.mindgames.visualmemory;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.mindgames.R;
import com.example.mindgames.utils.Effects;
import com.example.mindgames.utils.Table;

import java.util.ArrayList;

public class Gui extends Generator {
    private final Table table;
    private final FrameLayout tableContainer;
    private boolean userInput;

    public Gui(FrameLayout tableContainer){
        super();
        this.tableContainer = tableContainer;

        table = new Table(tableContainer.getContext(),
                3 , 3){
            @Override
            public void onClickCell(ViewGroup cell, int index) {
                super.onClickCell(cell, index);

                if (userInput) {
                    userInput(index);
                }
            }
        };
        createNewTable(3);
    }

    private void createNewTable(int tableSize){
        tableContainer.removeAllViews();
        table.createNewTable(tableSize, tableSize, 15, true, R.drawable.background_7);
        tableContainer.addView(table.getTable());
    }

    @Override
    public void onGameOver(int res) {
        FrameLayout f = table.cellAt(0);
        LayerDrawable layerDrawable = (LayerDrawable) f.getBackground();
        ((GradientDrawable)layerDrawable.getDrawable(2))
                .setColor(table.getContext().getColor(R.color.light_gray));
        userInput = false;
    }

    @Override
    public void onLevelChanged(int Level, int tableSize) {
        createNewTable(tableSize);
    }

    @Override
    public void onRoundChanged(int round) {

    }

    @Override
    public void onLivesChanged(int lives) {

    }

    @Override
    public void onPatternGenerated(ArrayList<Integer> cells) {
        ArrayList<View> v = new ArrayList<>();

        for (Integer index: cells) {
            v.add(table.cellAt(index));
        }

        AnimatorSet set = startFlipAnim(v, table.getContext().getColor(R.color.dark_white));
        AnimatorSet set2 = startFlipAnim(v, table.getContext().getColor(R.color.light_gray));

        set2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                userInput = true;
            }
        });

        set2.setStartDelay(1000);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                set2.start();
            }
        });


        set.start();
    }

    @Override
    public void onUserInput(int index, boolean right) {
        View v = table.cellAt(index);
        if (right){
            v.setClickable(false);
            startFlipAnim(v, table.getContext().getColor(R.color.dark_white))
                    .start();
        }else{
            Effects.vibrate(table.getContext());
            startStrokeAnim(v);
        }
    }

    @Override
    public void onUserInputCleared(ArrayList<Integer> cells) {
        userInput = false;
        ArrayList<View> v = new ArrayList<>();

        for (Integer index: cells) {
            View view = table.cellAt(index);
            view.setClickable(true);
            v.add(view);
        }

        AnimatorSet scale = startScaleAnim(v);
        AnimatorSet flip = startFlipAnim(v, table.getContext().getColor(R.color.light_gray));
        flip.setStartDelay(300);

        flip.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                generatePattern();
            }
        });

        scale.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                flip.start();
            }
        });

        scale.start();
    }

    private void startStrokeAnim(View v){
        LayerDrawable layerDrawable = (LayerDrawable) v.getBackground();

        GradientDrawable gradientDrawable = (GradientDrawable)layerDrawable.getDrawable(1);
        ObjectAnimator a = ObjectAnimator.ofInt(gradientDrawable, "Alpha",
                255, 0, 255);
        a.setDuration(400);

        v.setClickable(false);

        a.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v.setClickable(true);
            }
        });
        a.start();
    }

    private AnimatorSet startScaleAnim(Iterable<View> vs){
        ArrayList<Animator> animators = new ArrayList<>();
        AnimatorSet set = new AnimatorSet();

        for (View v: vs) {

            ValueAnimator a1 = ValueAnimator.ofFloat(1.0f, 1.08f, 1.0f);

            a1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float)animation.getAnimatedValue();
                    v.setScaleX(value);
                    v.setScaleY(value);
                }
            });

            animators.add(a1);
        }

        set.playSequentially(animators);
        set.setDuration(200);
        return set;
    }

    private AnimatorSet startFlipAnim(View v, int toColor){
        ObjectAnimator flip = ObjectAnimator.ofFloat(v, "RotationX",
                0.0f, 180.0f, 0.0f);

        LayerDrawable layerDrawable = (LayerDrawable) v.getBackground();

        GradientDrawable gradientDrawable = (GradientDrawable)layerDrawable.getDrawable(2);

        ObjectAnimator color = ObjectAnimator.ofArgb(gradientDrawable, "Color",
                toColor);

        AnimatorSet set = new AnimatorSet();

        set.playTogether(flip, color);

        set.setDuration(400);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        return set;
    }

    private AnimatorSet startFlipAnim(Iterable<View> vs, int toColor){
        ArrayList<Animator> animators = new ArrayList<>();
        AnimatorSet set = new AnimatorSet();

        for (View v: vs) {
            ObjectAnimator a1 = ObjectAnimator.ofFloat(v, "RotationX",
                    0.0f, 180.0f, 0.0f);
            animators.add(a1);
            LayerDrawable layerDrawable = (LayerDrawable) v.getBackground();

            GradientDrawable gradientDrawable = (GradientDrawable)layerDrawable.getDrawable(2);

            ObjectAnimator a2 = ObjectAnimator.ofArgb(gradientDrawable, "Color",
                    toColor);

            animators.add(a2);
        }

        set.playTogether(animators);
        set.setDuration(400);
        return set;
    }
}
