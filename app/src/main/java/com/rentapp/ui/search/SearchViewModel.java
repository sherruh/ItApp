package com.rentapp.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rentapp.App;
import com.rentapp.model.Vehicle;
import com.rentapp.repository.remote.IRemoteStorage;

import java.util.List;

public class SearchViewModel extends ViewModel {
    MutableLiveData<List<Vehicle>> vehiclesLiveData = new MutableLiveData<>();
    MutableLiveData<String> messageLiveData = new MutableLiveData<>();
    private int adapterPosition;

    public void getAllVehicles(){

        App.remoteStorage.getAnouncements(new IRemoteStorage.GetFromRemoteCallback() {
            @Override
            public void onSucces(List<Vehicle> vehicles) {

                vehiclesLiveData.setValue(vehicles);

            }

            @Override
            public void onFailure(String message) {

                messageLiveData.setValue(message);

            }
        });

    }

    public void getFilteredAnouncements(String filter){

        App.remoteStorage.getFilteredAnouncements(filter, new IRemoteStorage.GetFromRemoteCallback() {
            @Override
            public void onSucces(List<Vehicle> vehicles) {

                vehiclesLiveData.setValue(vehicles);
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
}
