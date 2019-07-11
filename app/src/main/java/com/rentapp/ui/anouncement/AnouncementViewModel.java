package com.rentapp.ui.anouncement;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.api.Context;
import com.rentapp.App;
import com.rentapp.core.SingleLiveEvent;
import com.rentapp.model.Anouncement;
import com.rentapp.repository.remote.IRemoteStorage;
import com.rentapp.utils.Logger;

import java.util.List;

public class AnouncementViewModel extends ViewModel {

    MutableLiveData<String> transactionResult = new MutableLiveData<>();
    MutableLiveData<List<String>> vehicleMarksLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> isUploading = new MutableLiveData<>();
    SingleLiveEvent<Void> upLoadedEvent = new SingleLiveEvent<>();
    SingleLiveEvent<Void> hasCameraEvent = new SingleLiveEvent<>();
    MutableLiveData<String> messageLiveData = new MutableLiveData<>();

    public void addAnouncement(Anouncement anouncement){
        isUploading.setValue(true);
        App.remoteStorage.addAnouncement(anouncement, new IRemoteStorage.WriteToRemoteCallback() {
            @Override
            public void onSucces() {
                transactionResult.setValue("Success");
                isUploading.setValue(false);
                upLoadedEvent.call();
            }

            @Override
            public void onFailure(String message) {
                transactionResult.setValue(message);
                isUploading.setValue(false);
            }
        });
    }

    public void getVehicleMarks(){

        App.remoteStorage.getVehicleMarks(new IRemoteStorage.GetFromRemoteCallback<List<String>>() {

            @Override
            public void onSucces(List<String> data) {

                vehicleMarksLiveData.setValue(data);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    public void onClickCamera(Activity activity){

        if (activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){

            hasCameraEvent.call();
        }else {

            messageLiveData.setValue("You have not camera");
        }
    }

}
