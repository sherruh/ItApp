package com.rentapp.repository.remote;

import com.rentapp.model.Vehicle;

import java.util.List;

public interface IRemoteStorage {

    void addAnouncement(Vehicle vehicle, WriteToRemoteCallback callback);

    void getAnouncements(GetFromRemoteCallback callback);

    void getFilteredAnouncements(String filter, GetFromRemoteCallback callback);

    void getUserAnouncements(String userId, GetFromRemoteCallback callback);

    public interface WriteToRemoteCallback{

        public void onSucces();

        public void onFailure(String message);

    }

    public interface GetFromRemoteCallback{

         void onSucces(List<Vehicle> vehicles);

         void onFailure(String message);


    }

}
