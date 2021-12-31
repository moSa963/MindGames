package com.example.mindgames.numbers;

interface NumbersGameListeners{
    void onUserInput(boolean gameOver, boolean roundEnded, int index);
    void onNewRoundGenerated(int count);
    void onFirstClick(int index);
    void onGameOver(int res);
}

//class will generate and process the game
public abstract class Generator implements NumbersGameListeners{
    private int numbersCount; //count the number of cards
    private int inputCounter; //count the input from user
    private boolean firstNumber;
    protected int level;

    public Generator(){
        numbersCount = 2;
        inputCounter = 1;
        level = 0;
        firstNumber = false;
    }

    //new user input
    protected void userInput(int index, int number){
        if (firstNumber){
            //if the user input correct
            if (number == inputCounter){
                ++inputCounter;

                if (number >= numbersCount){
                    onUserInput(false, true, index);
                    return;
                }
                onUserInput(false, false, index);
                return;
            }
            onUserInput(true, false, index);
        }else if (number == 1){
            onFirstClick(index);
            ++inputCounter;
            firstNumber = true;
        }
    }

    protected void newRound(){
        ++level;

        if (numbersCount < 45){
            ++numbersCount;
        }

        inputCounter = 1;
        firstNumber = false;
        onNewRoundGenerated(numbersCount);
    }
}