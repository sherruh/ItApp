package com.rentapp;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rentapp.repository.remote.IRemoteStorage;
import com.rentapp.repository.remote.RemoteStorage;


public class App extends Application {

    public static IRemoteStorage remoteStorage;
    public static boolean isLoggedIn;


    @Override
    public void onCreate() {
        super.onCreate();
        remoteStorage = new RemoteStorage();
        if(getFirebaseUser() == null) {
            isLoggedIn = false;
        }
        else {
            isLoggedIn = true;
        }
    }

    public static FirebaseUser getFirebaseUser(){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser;
    }
}
