package com.rentapp.repository.remote;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rentapp.model.Vehicle;
import com.rentapp.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class RemoteStorage implements IRemoteStorage {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void addAnouncement(Vehicle vehicle, final WriteToRemoteCallback callback) {
        Logger.message(db.toString());
        db.collection("vehicles")
                .add(vehicle)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Logger.message("DocumentSnapshot added with ID: " + documentReference.getId());
                        callback.onSucces();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Logger.message( "Error adding document" + e);
                        callback.onFailure("failed");
                    }
                });

    }

    @Override
    public void getAnouncements(final GetFromRemoteCallback callback) {

        db.collection("vehicles")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Vehicle> vehicles = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                vehicles.add(document.toObject(Vehicle.class));
                            }
                            callback.onSucces(vehicles);
                        } else {
                            Logger.message(task.getException().getMessage());
                            callback.onFailure(task.getException().getMessage());
                        }
                    }
                });

    }

    @Override
    public void getFilteredAnouncements(String filter, final GetFromRemoteCallback callback) {

        db.collection("vehicles")
                .whereGreaterThanOrEqualTo("title",filter)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Vehicle> vehicles = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                vehicles.add(document.toObject(Vehicle.class));
                            }
                            callback.onSucces(vehicles);
                        } else {
                            Logger.message(task.getException().getMessage());
                            callback.onFailure(task.getException().getMessage());
                        }
                    }
                });

    }

    @Override
    public void getUserAnouncements(String userId, final GetFromRemoteCallback callback) {

        db.collection("vehicles")
                .whereGreaterThanOrEqualTo("userID",userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Vehicle> vehicles = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                vehicles.add(document.toObject(Vehicle.class));
                            }
                            callback.onSucces(vehicles);
                        } else {
                            Logger.message(task.getException().getMessage());
                            callback.onFailure(task.getException().getMessage());
                        }
                    }
                });

    }
}
