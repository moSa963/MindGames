package com.example.mindgames.utils;

import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mindgames.R;

public class Layouts {

    public interface LayoutClickListener {
        void onClick();
    }

    public static View createStartLayout(ViewGroup container, String title, LayoutClickListener onClick){
        View view = View.inflate(container.getContext(), R.layout.start_layout, container);
        ((TextView)view.findViewById(R.id.title)).setText(title);
        FrameLayout fr = (FrameLayout)view.findViewById(R.id.button);

        fr.setOnClickListener(v->{
            v.setClickable(false);
            AnimatedVectorDrawable back = (AnimatedVectorDrawable)v.getBackground();
            back.registerAnimationCallback(new Animatable2.AnimationCallback() {
                @Override
                public void onAnimationEnd(Drawable drawable) {
                    super.onAnimationEnd(drawable);
                    onClick.onClick();
                }
            });
            back.start();
        });

        return view;
    }

    public static View createResultLayout(ViewGroup container, String res, LayoutClickListener onClick){
        View view = View.inflate(container.getContext(), R.layout.result_layout, container);
        TextView textView = (TextView)view.findViewById(R.id.title);
        textView.setText(res);

        textView.setOnClickListener(v-> {
            v.setClickable(false);
            onClick.onClick();
        });

        return view;
    }

}
