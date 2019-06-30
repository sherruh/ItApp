package com.rentapp.ui.myanouncements;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rentapp.App;
import com.rentapp.model.Vehicle;
import com.rentapp.repository.remote.IRemoteStorage;

import java.util.List;

public class MyAnouncementsViewModel extends ViewModel {

    MutableLiveData<List<Vehicle>> anouncementsOfUserLiveData = new MutableLiveData<>();
    MutableLiveData<String> messageLiveData = new MutableLiveData<>();

    public void getUserAnouncements(){
        App.remoteStorage.getUserAnouncements(App.getFirebaseUser().getUid(), new IRemoteStorage.GetFromRemoteCallback() {
            @Override
            public void onSucces(List<Vehicle> vehicles) {

                anouncementsOfUserLiveData.setValue(vehicles);
            }

            @Override
            public void onFailure(String message) {

                messageLiveData.setValue(message);
            }
        });
    }
}
