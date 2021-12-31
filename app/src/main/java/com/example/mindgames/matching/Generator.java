package com.example.mindgames.matching;

import android.util.ArraySet;

import com.example.mindgames.utils.SetOperations;

import java.util.ArrayList;
import java.util.Random;

public class Generator {
    private int max;
    private SetOperations.Operation operation;
    private ArraySet<Integer> mainSet1;
    private ArraySet<Integer> mainSet2;
    private ArrayList<ArraySet<Integer>> answers;
    private int answerPos;
    private final int ANSWERS_COUNT;

    protected Generator(int max, int answersCount){
        this.max = max;
        operation = null;
        mainSet1 = null;
        mainSet2 = null;
        answerPos = -1;
        this.ANSWERS_COUNT = answersCount;
    }

    public void newGame(){
        setOperation();
        setMainSet();
        setAnswers();
    }

    //set a random "set" operation
    private void setOperation(){
        Random rand = new Random();
        int res = rand.nextInt(3);

        switch (res){
            case 0: operation = SetOperations.Operation.UNION; break;
            case 1: operation = SetOperations.Operation.INTERSECTION; break;
            case 2: operation = SetOperations.Operation.DIFFERENCE; break;
        }
    }

    private void setMainSet(){
        Random rand = new Random();
        int set1Size = (int)(max * 0.25) + rand.nextInt(max / 2);
        int set2Size = (int)(max * 0.25) + rand.nextInt(max / 2);

        mainSet1 = new ArraySet<>(set1Size);
        mainSet2 = new ArraySet<>(set2Size);

        while (set1Size > 0){
            int newIndex = rand.nextInt(max);
            if (mainSet1.add(newIndex)) --set1Size;
        }
        while (set2Size > 0){
            int newIndex = rand.nextInt(max);
            if (mainSet2.add(newIndex)) --set2Size;
        }
    }

    private void setAnswers(){
        ArraySet<Integer> finalAnswer = SetOperations.get(mainSet1, mainSet2, operation);

        ArrayList<ArraySet<Integer>> answers = new ArrayList<>(ANSWERS_COUNT);
        Random rand = new Random();
        answerPos = rand.nextInt(ANSWERS_COUNT);

        int startWith = 1 + rand.nextInt(max / 4);

        if (finalAnswer.size() > max / 2){
            startWith = -startWith;
        }

        for(int i = 1; i <= ANSWERS_COUNT; ++i){
            if (i - 1 == answerPos){
                answers.add(finalAnswer);
            }else{
                float switchRatio = (1 + rand.nextInt(7)) * 0.1f;
                answers.add(getWrongAnswer(finalAnswer, startWith + i, switchRatio));
            }
        }

        this.answers = answers;
    }

    private ArraySet<Integer> getWrongAnswer(ArraySet<Integer> rightAnswer,
                                             int sizeChange, float switchRatio){
        ArraySet<Integer> wrongAnswer;
        int newSize = rightAnswer.size() + sizeChange;

        if (sizeChange < 0){
            wrongAnswer = new ArraySet<>();
            for(int i = 0; i < newSize; ++i){
                wrongAnswer.add(rightAnswer.valueAt(i));
            }
        }else{
            wrongAnswer = new ArraySet<>(rightAnswer);
            Random rand = new Random();

            while (wrongAnswer.size() < newSize){
                int newIndex = rand.nextInt(max);
                wrongAnswer.add(newIndex);
            }

        }
        Random rand = new Random();
        int swichCount = (int)Math.ceil(wrongAnswer.size() * switchRatio);

        for(int i = 0; i < swichCount; ++i){
            wrongAnswer.removeAt(0);
            while (!wrongAnswer.add(rand.nextInt(max)));
        }

        return wrongAnswer;
    }

    public void setMax(int max) { this.max = max; }

    public int getRightAnswerIndex() { return answerPos; }
    public SetOperations.Operation getOperation() { return operation; }
    public ArraySet<Integer> getMainSet1() { return mainSet1; }
    public ArraySet<Integer> getMainSet2() { return mainSet2; }
    public ArrayList<ArraySet<Integer>> getAnswers() { return answers; }
}
