package com.example.mindgames.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Storage {
    public static Storage storage = null;
    public Activity activity;

    private Storage(Activity activity){
        this.activity = activity;
    }

    public static void createStorage(Activity activity){
        if (storage == null){
            storage = new Storage(activity);
        }
    }

    public static Storage getStorage(){
        return storage;
    }

    public void setData(String key, String value){
        if (activity != null){
            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(key, value);
            editor.apply();
        }
    }

    public String getData(String key){
        if (activity != null){
            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            return sharedPref.getString(key, "-");
        }
        return "-";
    }

    public void newScore(String game, int score, boolean lessIsBetter){
        String data = getData(game);
        if (data.equals("-")){
            setData(game, score + "");
        }else{
            int oldScore = Integer.parseInt(data);
            if (lessIsBetter){
                if (oldScore > score){
                    setData(game, score + "");
                }
            }else{
                if (oldScore < score){
                    setData(game, score + "");
                }
            }
        }
    }
}
