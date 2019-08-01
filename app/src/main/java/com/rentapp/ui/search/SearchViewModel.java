package com.rentapp.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rentapp.App;
import com.rentapp.model.Anouncement;
import com.rentapp.repository.remote.IRemoteStorage;

import java.util.List;

public class SearchViewModel extends ViewModel {
    MutableLiveData<List<Anouncement>> vehiclesLiveData = new MutableLiveData<>();
    MutableLiveData<String> messageLiveData = new MutableLiveData<>();

    private int adapterPosition;

    public void getAllVehicles(){

        App.remoteStorage.getAnouncements(new IRemoteStorage.GetFromRemoteCallback<List<Anouncement>>() {
            @Override
            public void onSucces(List<Anouncement> anouncements) {

                vehiclesLiveData.setValue(anouncements);

            }

            @Override
            public void onFailure(String message) {

                messageLiveData.setValue(message);

            }
        });

    }

    public void getFilteredAnouncements(String filter){

        App.remoteStorage.getFilteredAnouncements(filter, new IRemoteStorage.GetFromRemoteCallback<List<Anouncement>>() {
            @Override
            public void onSucces(List<Anouncement> anouncements) {

                vehiclesLiveData.setValue(anouncements);
            }

            @Override
            public void onFailure(String message) {

                messageLiveData.setValue(message);
            }
        });
    }

    public void saveAdapterPostition(int i){

        adapterPosition = i;

    }

    public int getAdapterPosition(){

        return adapterPosition;
    }

    //TODO isLoading
}
