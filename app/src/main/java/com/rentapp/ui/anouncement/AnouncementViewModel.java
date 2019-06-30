package com.rentapp.ui.anouncement;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rentapp.App;
import com.rentapp.core.SingleLiveEvent;
import com.rentapp.model.Vehicle;
import com.rentapp.repository.remote.IRemoteStorage;
import com.rentapp.utils.Logger;

public class AnouncementViewModel extends ViewModel {

    MutableLiveData<String> transactionResult = new MutableLiveData<>();
    MutableLiveData<Boolean> isUploading = new MutableLiveData<>();
    SingleLiveEvent<Void> upLoadedEvent = new SingleLiveEvent<>();

    public void addAnouncement(Vehicle vehicle){
        isUploading.setValue(true);
        App.remoteStorage.addAnouncement(vehicle, new IRemoteStorage.WriteToRemoteCallback() {
            @Override
            public void onSucces() {
                transactionResult.setValue("Success");
                Logger.message("succes");
                isUploading.setValue(false);
                upLoadedEvent.call();
            }

            @Override
            public void onFailure(String message) {
                transactionResult.setValue(message);
                Logger.message("filed");
                isUploading.setValue(false);
            }
        });
    }

}
