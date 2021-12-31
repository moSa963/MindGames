package com.example.mindgames.numbers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import com.example.mindgames.R;
import com.example.mindgames.utils.Layouts;
import com.example.mindgames.utils.Storage;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        setStartLayout();
    }

    private void setStartLayout(){
        ConstraintLayout mainFrame = findViewById(R.id.root);
        mainFrame.removeAllViews();

        Layouts.createStartLayout(mainFrame, "Matching", this::setMainLayout);
    }
    private void setMainLayout(){
        ConstraintLayout mainFrame = findViewById(R.id.root);
        mainFrame.removeAllViews();
        new Gui(mainFrame){
            @Override
            public void onGameOver(int res) {
                seResultLayout(res);
            }
        };
    }
    private void seResultLayout(int res){
        Storage storage = Storage.getStorage();

        storage.newScore(getString(R.string.numbers), res, false);
        ConstraintLayout mainFrame = findViewById(R.id.root);
        Layouts.createResultLayout(mainFrame, "Game Over\n" + "Result " + res +  " round", this::setStartLayout);
    }
}