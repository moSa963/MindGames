package com.example.mindgames.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;

import java.util.ArrayList;

public class Effects {
    public static void vibrate(Context context){
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public static Animator ScaleAnimation(View cell, boolean revers){
        ArrayList<Animator> animatorArrayList = new ArrayList<>();

        if (revers){
            animatorArrayList.add(ObjectAnimator.ofFloat(cell, "ScaleX",
                    0.0f, 0.2f, 0.1f, 1.0f));
            animatorArrayList.add(ObjectAnimator.ofFloat(cell, "ScaleY",
                    0.0f, 0.2f, 0.1f, 1.0f));
        }else{
            animatorArrayList.add(ObjectAnimator.ofFloat(cell, "ScaleX",
                    1.0f, 0.1f, 0.2f, 0.0f));
            animatorArrayList.add(ObjectAnimator.ofFloat(cell, "ScaleY",
                    1.0f, 0.1f, 0.2f, 0.0f));
        }

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorArrayList);
        return set;
    }
}
