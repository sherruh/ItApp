package com.rentapp.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class Toaster{

    public static void message(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
