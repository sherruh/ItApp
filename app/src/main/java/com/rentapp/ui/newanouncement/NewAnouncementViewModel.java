package com.rentapp.ui.newanouncement;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rentapp.App;
import com.rentapp.core.SingleLiveEvent;
import com.rentapp.model.Anouncement;
import com.rentapp.repository.remote.IRemoteStorage;
import com.rentapp.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class NewAnouncementViewModel extends ViewModel {

    MutableLiveData<String> transactionResult = new MutableLiveData<>();
    MutableLiveData<List<String>> vehicleMarksLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> isUploading = new MutableLiveData<>();
    SingleLiveEvent<Void> upLoadedEvent = new SingleLiveEvent<>();
    SingleLiveEvent<Void> hasCameraEvent = new SingleLiveEvent<>();
    MutableLiveData<String> messageLiveData = new MutableLiveData<>();

    private List<String> imageUrls = new ArrayList<>();

    public void addAnouncement(Anouncement anouncement){
        isUploading.setValue(true);
        anouncement.setImagesUrl(imageUrls);
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


    public void sendImage(String imageName, Uri outputImageUri) {

        App.remoteStorage.uploadImage(imageName, outputImageUri, new IRemoteStorage.GetFromRemoteCallback() {
            @Override
            public void onSucces(Object data) {
                Logger.message((String) data);
                imageUrls.add((String)data);
                //TODO check uploadings
            }

            @Override
            public void onFailure(String message) {

                Logger.message(message);
            }
        });
    }
}
