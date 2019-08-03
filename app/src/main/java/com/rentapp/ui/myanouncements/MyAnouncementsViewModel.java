package com.rentapp.ui.myanouncements;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rentapp.App;
import com.rentapp.model.Anouncement;
import com.rentapp.repository.remote.IRemoteStorage;

import java.util.List;

public class MyAnouncementsViewModel extends ViewModel {

    MutableLiveData<List<Anouncement>> anouncementsOfUserLiveData = new MutableLiveData<>();
    MutableLiveData<String> messageLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public void getUserAnouncements(){

        isLoading.setValue(true);

        App.remoteStorage.getUserAnouncements(App.getFirebaseUser().getUid(), new IRemoteStorage.GetFromRemoteCallback<List<Anouncement>>() {
            @Override
            public void onSucces(List<Anouncement> anouncements) {

                anouncementsOfUserLiveData.setValue(anouncements);
            }

            @Override
            public void onFailure(String message) {

                messageLiveData.setValue(message);
            }
        });

        isLoading.setValue(false);
    }
}
