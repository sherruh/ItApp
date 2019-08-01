package com.rentapp.repository.remote;

import android.net.Uri;

import com.rentapp.model.Anouncement;

import java.util.List;

public interface IRemoteStorage {

    void addAnouncement(Anouncement anouncement, WriteToRemoteCallback callback);

    void getAnouncements(GetFromRemoteCallback callback);

    void getFilteredAnouncements(String filter, GetFromRemoteCallback callback);

    void getUserAnouncements(String userId, GetFromRemoteCallback callback);

    void getVehicleMarks(GetFromRemoteCallback callback);

    void uploadImage(String imageName, Uri outputImageUri,GetFromRemoteCallback callback);

    public interface WriteToRemoteCallback{

        public void onSucces();

        public void onFailure(String message);

    }

    public interface GetFromRemoteCallback<T>{

         void onSucces(T data);

         void onFailure(String message);


    }

}
