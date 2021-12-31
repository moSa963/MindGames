package com.example.mindgames.utils;

public class MillisecondCounter {
    private boolean flag;
    private long start;

    public MillisecondCounter(){
        this.start = 0;
        this.flag = false;
    }

    public void start(){
        start = System.currentTimeMillis();
        flag = true;
    }

    public Long stop(){
        Long res = null;
        if (flag){
            long end = System.currentTimeMillis();
            res =  end - start;
        }
        start = 0;
        flag = false;
        return res;
    }
}
