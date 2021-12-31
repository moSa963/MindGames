package com.example.mindgames.visualmemory;

import java.util.ArrayList;
import java.util.Random;

interface GeneratorListeners {
    void onGameOver(int res);
    void onLevelChanged(int Level, int tableSize);
    void onRoundChanged(int round);
    void onLivesChanged(int lives);
    void onPatternGenerated(ArrayList<Integer> cells);
    void onUserInput(int index, boolean right);
    void onUserInputCleared(ArrayList<Integer> cells);
}


public abstract class Generator implements GeneratorListeners{
    private int level;
    private int tableSize;
    private ArrayList<Integer> cells;
    private ArrayList<Integer> oldCells;
    private int round;
    private int lives;

    public Generator(){

    }

    public void setLevel(int newLevel){
        level = newLevel;
        tableSize = (level + 2);
        tableSize *= tableSize;

        onLevelChanged(level, level + 2);
    }

    public void generatePattern(){
        Random rand = new Random();

        while (cells.size() < round){
            int index = rand.nextInt(tableSize);

            if (!cells.contains(index)){
                cells.add(index);
            }
        }
        nextRound();
        onPatternGenerated(cells);
    }

    private void nextRound(){
        onRoundChanged(round);
        ++round;
        if (round * 2 > tableSize){
            setLevel(++level);
        }
    }

    public void userInput(int index){
        if (lives > 0){
            if(cells.contains(index)){
                cells.remove((Object)index);
                oldCells.add(index);
                onUserInput(index, true);

                if (cells.isEmpty()){
                    onUserInputCleared(oldCells);
                    oldCells.clear();
                }

                return;
            }else{
                --lives;
                onLivesChanged(lives);
                onUserInput(index, false);

                if (lives > 0){
                    return;
                }
            }
        }
        onGameOver(round);
    }

    public void startNewGame(){
        this.level = 1;
        this.round = 1;
        this.lives = 3;
        tableSize = 3 * 3;
        cells = new ArrayList<>();
        oldCells = new ArrayList<>();
        generatePattern();
    }
}
