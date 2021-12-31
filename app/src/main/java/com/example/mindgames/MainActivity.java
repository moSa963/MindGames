package com.example.mindgames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import com.example.mindgames.utils.Storage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Storage.createStorage(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, new HomeFragment(), null)
                .commit();

        ((BottomNavigationView)findViewById(R.id.bottomNavigationView))
                .setOnItemSelectedListener(i->{
                    Fragment f = null;
                    switch (i.getItemId()){
                        case R.id.home_btn:{
                            f = new HomeFragment();
                            break;
                        }
                        case R.id.info_btn:{
                            f = new InfoFragment();
                            break;
                        }
                        default: return false;
                    }
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_frame, f, null)
                            .commit();
                    return true;
                });

        startBackgroundAnimation();
    }


    private void startBackgroundAnimation(){
        ConstraintLayout mainLayout = findViewById(R.id.root);
        LayerDrawable backGround = (LayerDrawable)mainLayout.getBackground();

        AnimatedVectorDrawable avd = (AnimatedVectorDrawable)backGround.getDrawable(1);

        avd.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                avd.start();
            }
        });
        avd.start();
    }
}