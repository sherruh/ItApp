package com.rentapp.utils;

import android.util.Log;

public class Logger {
    private static final String TAG = "MyApp#";
    private static final boolean IS_SHOW = true;

    public static void message(String message){
        if (IS_SHOW) Log.d(TAG,message);
    }
}
