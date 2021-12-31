package com.example.mindgames.reaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import com.example.mindgames.R;
import com.example.mindgames.utils.Effects;
import com.example.mindgames.utils.MillisecondCounter;
import com.example.mindgames.utils.Storage;

import java.util.Random;

public class ReactionActivity extends AppCompatActivity {
    private Fragment startFrag;
    private Fragment mainFrag;
    private ConstraintLayout sensor;
    private Handler handler;
    private boolean startIsActive;
    private MillisecondCounter counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction);
        startIsActive = true;
        handler = null;
        startFrag = new StartFragment();
        mainFrag = new ReactionLayout();
        counter = new MillisecondCounter();

        //add fragments to this activity
        getSupportFragmentManager().beginTransaction()
                .add(R.id.root, mainFrag)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.root, startFrag)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //on start set all listener
        sensor = mainFrag.getView().findViewById(R.id.sensor);
        sensor.setOnClickListener(x->{ gameOver(); });
        (startFrag.getView()).findViewById(R.id.start_btn)
                .setOnClickListener(x->{ startGame(); });
    }

    //when pause end the game
    @Override
    protected void onPause() {
        super.onPause();
        if (!startIsActive) gameOver();
    }

    private void startGame(){
        handler = new Handler();
        Random rand = new Random();

        hideStart();//hide the start fragment
        handler.postDelayed(()->{
            setSensorState(true);
            counter.start(); //let the ms counter start calculating
        }, rand.nextInt(20000) + 5000); //random delay number
    }

    //sensor state -> if can be clicked or not
    private void setSensorState(boolean isItGreen){
        //if true that means it is time to click
        if (isItGreen){
            sensor.setBackgroundColor(getColor(R.color.green));
            ((TextView)mainFrag.getView().findViewById(R.id.title))
                    .setText(R.string.click);
        }else{
            sensor.setBackgroundColor(getColor(R.color.light_red));
            ((TextView)mainFrag.getView().findViewById(R.id.title))
                    .setText(R.string.wait);
        }
    }

    private void gameOver(){
        handler.removeCallbacksAndMessages(null);
        Long res = counter.stop(); //get the counter result -> if it did not start return null
        String resString;

        if (res == null){
            Effects.vibrate(sensor.getContext());
            resString = "way too early...";
        }else{
            resString = res.toString() + " ms";

            Storage.getStorage()
                    .newScore(getString(R.string.reaction), Integer.parseInt(res.toString()), true);
        }

        setSensorState(false);

        showStart(resString, R.string.restart);
    }

    private void showStart(String m, int btnTextId){

        ((Button)startFrag.getView().findViewById(R.id.start_btn))
                .setText(getText(btnTextId));

        ((TextView)startFrag.getView().findViewById(R.id.title))
                .setText("Results");

        ((TextView)startFrag.getView().findViewById(R.id.result))
                .setText(m);

        getSupportFragmentManager().beginTransaction()
                .show(startFrag)
                .commit();
        startIsActive = true;
    }

    private void hideStart(){
        getSupportFragmentManager().beginTransaction()
                .hide(startFrag)
                .commit();
        startIsActive = false;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}


