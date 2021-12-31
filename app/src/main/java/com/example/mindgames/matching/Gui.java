package com.example.mindgames.matching;

import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.ArraySet;
import android.widget.FrameLayout;
import android.widget.TableLayout;

import com.example.mindgames.R;
import com.example.mindgames.utils.Effects;
import com.example.mindgames.utils.SetOperations;
import com.example.mindgames.utils.Table;

import java.util.ArrayList;

interface MatchingLis{
    void onGameOver(int res);
}

public abstract class Gui implements MatchingLis{
    private final FrameLayout mainSet1;
    private final FrameLayout mainSet2;
    private final FrameLayout signCon;
    private final FrameLayout timerCon;
    private AnimatedVectorDrawable timer;
    private final ArrayList<FrameLayout> con_answers;
    private Generator gen;
    private int size;
    private int level;
    private boolean timerFlag;

    public Gui(FrameLayout mainSet1, FrameLayout mainSet2,
                       FrameLayout singCon, FrameLayout timerCon,
                       ArrayList<FrameLayout> con_answers){
        this.con_answers = con_answers;
        this.mainSet1 = mainSet1;
        this.mainSet2 = mainSet2;
        this.signCon = singCon;
        this.timerCon = timerCon;
        this.size = 4;
        this.timer = null;
        this.level = 0;
        timerFlag = false;
        this.gen = new Generator(size * size, 4);

        genNewGame();
    }

    private void genNewGame(){
        level++;
        if (size < 7) size = 4 + level / 10;

        gen.newGame();
        createTable(mainSet1, gen.getMainSet1(), true);
        createTable(mainSet2, gen.getMainSet2(), true);

        setSign(gen.getOperation());

        for (int i = 0; i < con_answers.size(); ++i){
            TableLayout al = createTable(con_answers.get(i), gen.getAnswers().get(i), false);
            int index = i;

            al.setOnClickListener(v->{
                timerFlag = false;
                timer.reset();
                answerClicked(index);
            });

            Effects.ScaleAnimation(con_answers.get(i), true).setDuration(1000).start();
        }
        startTimer();
    }


    private void answerClicked(int index){
        if (index == gen.getRightAnswerIndex()){
            genNewGame();
        }else{
            Effects.vibrate(mainSet1.getContext());
            onGameOver(level);
        }
    }

    private TableLayout createTable(FrameLayout con, ArraySet<Integer> fill, boolean anim){
        Table table = new Table(con.getContext(), size, size);
        table.createNewTable(size, size, 5, false);

        for(Integer index : fill){
            table.cellAt(index).setBackgroundResource(R.drawable.background_circle_1);

            if (anim){
                Effects.ScaleAnimation(table.cellAt(index), true).setDuration(500).start();
            }
        }

        con.removeAllViews();
        con.addView(table.getTable());
        return table.getTable();
    }

    private void setSign(SetOperations.Operation op){
        int res = 0;
        switch (op){
            case INTERSECTION:{
                res = R.drawable.sign_intersection;
                break;
            }
            case DIFFERENCE:{
                res = R.drawable.sign_difference;
                break;
            }
            case UNION:{
                res = R.drawable.sign_union;
                break;
            }
        }

        signCon.setBackgroundResource(res);

        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) signCon.getBackground();

        avd.start();
    }


    private void startTimer(){
        timer = (AnimatedVectorDrawable) timerCon.getBackground();
        timer.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                if (timerFlag)
                    onGameOver(level);
            }
        });
        timerFlag = true;
        timer.start();
    }

}