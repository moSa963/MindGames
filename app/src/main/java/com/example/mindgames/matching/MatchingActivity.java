package com.example.mindgames.matching;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.mindgames.R;
import com.example.mindgames.utils.Layouts;
import com.example.mindgames.utils.Storage;

import java.util.ArrayList;

public class MatchingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        setStartLayout();
    }

    //layout that wait until the user start the game
    private void setStartLayout(){
        ConstraintLayout mainFrame = findViewById(R.id.root);
        mainFrame.removeAllViews();

        //when user click on start button "setMainLayout" will start the game
        Layouts.createStartLayout(mainFrame, "Matching", this::setMainLayout);
    }

    private void setMainLayout(){
        ConstraintLayout mainFrame = findViewById(R.id.root);
        mainFrame.removeAllViews();

        View game = View.inflate(this, R.layout.matching_layout,
                mainFrame);

        ArrayList<FrameLayout> ansCon = new ArrayList<>();
        ansCon.add(findViewById(R.id.answer_1));
        ansCon.add(findViewById(R.id.answer_2));
        ansCon.add(findViewById(R.id.answer_3));
        ansCon.add(findViewById(R.id.answer_4));

        new Gui(game.findViewById(R.id.set1),
                game.findViewById(R.id.set2),
                game.findViewById(R.id.operation),
                game.findViewById(R.id.timer),
                ansCon){

            @Override
            public void onGameOver(int res) {
                seResultLayout(res);
            }
        };
    }

    //game ends so show the results
    private void seResultLayout(int res){
        Storage storage = Storage.getStorage();

        storage.newScore(getString(R.string.match), res, false);
        ConstraintLayout mainFrame = findViewById(R.id.root);
        Layouts.createResultLayout(mainFrame, "Game Over\n" + "Result " + res +  " round", this::setStartLayout);
    }
}