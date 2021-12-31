package com.example.mindgames.visualmemory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mindgames.R;
import com.example.mindgames.utils.Layouts;
import com.example.mindgames.utils.Storage;

public class VisualMemoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_memory);

        setStartLayout();
    }

    private void setStartLayout(){
        ConstraintLayout mainFrame = findViewById(R.id.root);
        mainFrame.removeAllViews();

        Layouts.createStartLayout(mainFrame, "Visual Memory", this::setMainLayout);
    }

    private void setMainLayout(){
        ConstraintLayout mainGame = findViewById(R.id.root);
        mainGame.removeAllViews();

        View gameFrame = View.inflate(mainGame.getContext(), R.layout.visual_memory_layout, mainGame);

        FrameLayout mainFrame = gameFrame.findViewById(R.id.main_frame);

        TextView Round = gameFrame.findViewById(R.id.round_text);
        TextView level = gameFrame.findViewById(R.id.level_text);
        TextView Lives = gameFrame.findViewById(R.id.lives_text);

        new Gui(mainFrame){
            @Override
            public void onLevelChanged(int Level, int tableSize) {
                super.onLevelChanged(Level, tableSize);
                level.setText(Level + "");
            }

            @Override
            public void onRoundChanged(int round) {
                super.onRoundChanged(round);
                Round.setText(round + "");
            }

            @Override
            public void onLivesChanged(int lives) {
                super.onLivesChanged(lives);
                Lives.setText(lives + "");
            }

            @Override
            public void onGameOver(int res) {
                super.onGameOver(res);
                seResultLayout(res);
            }
        }.startNewGame();
    }

    private void seResultLayout(int res){
        Storage storage = Storage.getStorage();

        ConstraintLayout mainFrame = findViewById(R.id.root);
        storage.newScore(getString(R.string.cards), res, false);
        Layouts.createResultLayout(mainFrame, "Game Over\nRound: "  + res, this::setStartLayout);
    }
}