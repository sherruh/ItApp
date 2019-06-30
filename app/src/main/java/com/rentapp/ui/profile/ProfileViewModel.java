package com.rentapp.ui.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rentapp.App;

public class ProfileViewModel extends ViewModel {

    MutableLiveData<Boolean> isLogged = new MutableLiveData<>();

    public void checkLogginStatus(){

        if (App.getFirebaseUser() == null){
            isLogged.setValue(false);
        }else isLogged.setValue(true);
    }

    public void logOut(){

        isLogged.setValue(false);
    }
}
