package com.rentapp.repository.remote;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rentapp.model.Anouncement;
import com.rentapp.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class RemoteStorage implements IRemoteStorage {

    private final String TABLE_ANOUNCEMENTS = "anouncements";
    private final String TABLE_VEHICLES = "vehicles";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();;

    @Override
    public void addAnouncement(Anouncement anouncement, final WriteToRemoteCallback callback) {

        db.collection(TABLE_ANOUNCEMENTS)
                .add(anouncement)
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

        db.collection(TABLE_ANOUNCEMENTS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Anouncement> anouncements = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                anouncements.add(document.toObject(Anouncement.class));
                            }
                            callback.onSucces(anouncements);
                        } else {
                            Logger.message(task.getException().getMessage());
                            callback.onFailure(task.getException().getMessage());
                        }
                    }
                });

    }

    @Override
    public void getFilteredAnouncements(String filter, final GetFromRemoteCallback callback) {

        db.collection(TABLE_ANOUNCEMENTS)
                .whereGreaterThanOrEqualTo("title",filter)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Anouncement> anouncements = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                anouncements.add(document.toObject(Anouncement.class));
                            }
                            callback.onSucces(anouncements);
                        } else {
                            Logger.message(task.getException().getMessage());
                            callback.onFailure(task.getException().getMessage());
                        }
                    }
                });

    }

    @Override
    public void getUserAnouncements(String userId, final GetFromRemoteCallback callback) {

        db.collection(TABLE_ANOUNCEMENTS)
                .whereGreaterThanOrEqualTo("userID",userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Anouncement> anouncements = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                anouncements.add(document.toObject(Anouncement.class));
                            }
                            callback.onSucces(anouncements);
                        } else {
                            Logger.message(task.getException().getMessage());
                            callback.onFailure(task.getException().getMessage());
                        }
                    }
                });

    }

    @Override
    public void getVehicleBrands(final GetFromRemoteCallback callback) {

        db.collection(TABLE_VEHICLES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> vehicleMarks = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                vehicleMarks.add(document.getString("Name"));
                            }
                            callback.onSucces(vehicleMarks);
                        } else {
                            Logger.message(task.getException().getMessage());
                            callback.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void uploadImage(String imageName, Uri outputImageUri, final GetFromRemoteCallback callback) {

        final StorageReference imagesRef = storageReference.child("images/" + imageName);
        imagesRef.putFile(outputImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                callback.onSucces(uri.toString());
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                        callback.onFailure(exception.getMessage());
                    }
                });
    }
}
