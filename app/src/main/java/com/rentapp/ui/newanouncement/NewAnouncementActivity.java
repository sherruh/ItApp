package com.rentapp.ui.newanouncement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.mikelau.croperino.Croperino;
import com.mikelau.croperino.CroperinoConfig;
import com.mikelau.croperino.CroperinoFileUtil;
import com.rentapp.App;
import com.rentapp.R;
import com.rentapp.model.Anouncement;
import com.rentapp.utils.Logger;
import com.rentapp.utils.Toaster;

import java.util.List;

import static android.R.layout.simple_spinner_item;

public class NewAnouncementActivity extends AppCompatActivity {

    public static void start(Context context){
        Intent intent = new Intent(context, NewAnouncementActivity.class);
        context.startActivity(intent);
    }

    private EditText editYear;
    private EditText editCity;
    private EditText editPrice;
    private Button buttonAdd;

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private int currentImageId;


    private ProgressBar progressBarIsUploading;
    private Spinner spinnerMarks;
    private NewAnouncementViewModel viewModel;

    private String imageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anouncement);

        initView();

        initViewModel();

        viewModel.getVehicleMarks();
    }

    private void initViewModel() {

        viewModel = ViewModelProviders.of(this)
                .get(NewAnouncementViewModel.class);

        viewModel.transactionResult.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toaster.message(NewAnouncementActivity.this,s);
            }
        });

        viewModel.isUploading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    progressBarIsUploading.setVisibility(View.VISIBLE);
                    buttonAdd.setVisibility(View.GONE);
                }else{
                    progressBarIsUploading.setVisibility(View.GONE);
                    buttonAdd.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.upLoadedEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish();
            }
        });

        viewModel.vehicleMarksLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> vehicleMarks) {

                ArrayAdapter<String> adapter = new ArrayAdapter(NewAnouncementActivity.this, simple_spinner_item,vehicleMarks);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMarks.setAdapter(adapter);
            }
        });

        viewModel.hasCameraEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {

                startCamera();
            }
        });

        viewModel.messageLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toaster.message(NewAnouncementActivity.this,s);
            }
        });
    }



    private void initView() {

        editYear = findViewById(R.id.anouncement_edit_year);
        editCity = findViewById(R.id.anouncement_edit_city);
        editPrice = findViewById(R.id.anouncement_edit_price);
        buttonAdd = findViewById(R.id.anouncement_buttona_save);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = new Integer( editPrice.getText().toString().trim()).intValue();
                viewModel.addAnouncement(new Anouncement(spinnerMarks.getSelectedItem().toString(),
                        editYear.getText().toString(),
                        editCity.getText().toString(),
                        price, App.getFirebaseUser().getUid(),null
                        ));
            }
        });
        image1 = findViewById(R.id.anouncement_image_1);
        image2 = findViewById(R.id.anouncement_image_2);
        image3 = findViewById(R.id.anouncement_image_3);
        image4 = findViewById(R.id.anouncement_image_4);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImageId = image1.getId();
                viewModel.onClickCamera(NewAnouncementActivity.this);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImageId = image2.getId();
                viewModel.onClickCamera(NewAnouncementActivity.this);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImageId = image3.getId();
                viewModel.onClickCamera(NewAnouncementActivity.this);
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImageId = image4.getId();
                viewModel.onClickCamera(NewAnouncementActivity.this);

            }
        });

        spinnerMarks = findViewById(R.id.anouncement_spinner_mark);

        progressBarIsUploading = findViewById(R.id.anouncement_progress);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case CroperinoConfig.REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(),
                            NewAnouncementActivity.this, true, 16, 12,
                            R.color.gray, R.color.gray_variant);
                    Logger.message("URI " + Uri.fromFile(CroperinoFileUtil.getTempFile()));
                }
                break;
            case CroperinoConfig.REQUEST_PICK_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    CroperinoFileUtil.newGalleryFile(data, NewAnouncementActivity.this);
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), NewAnouncementActivity.this,
                            true, 16, 12, R.color.gray, R.color.gray_variant);
                }
                break;
            case CroperinoConfig.REQUEST_CROP_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Uri outputImageUri = Uri.fromFile(CroperinoFileUtil.getTempFile());
                    Glide.with(findViewById(currentImageId)).load(outputImageUri).into((ImageView) findViewById(currentImageId));
                    viewModel.sendImage(imageName,outputImageUri);
                }
                break;
            default:
                break;
        }

    }

    private void startCamera() {

        imageName = "IMG_" + System.currentTimeMillis() + ".jpg";
        new CroperinoConfig(imageName, "/Pictures/Camera/", "/sdcard/Pictures/Camera/");
        CroperinoFileUtil.setupDirectory(NewAnouncementActivity.this);
        prepareChooser();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CroperinoFileUtil.REQUEST_CAMERA) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.CAMERA)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        prepareCamera();
                    }
                }
            }
        } else if (requestCode == CroperinoFileUtil.REQUEST_EXTERNAL_STORAGE) {
            boolean wasReadGranted = false;
            boolean wasWriteGranted = false;

            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        wasReadGranted = true;
                    }
                }
                if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        wasWriteGranted = true;
                    }
                }
            }

            if (wasReadGranted && wasWriteGranted) {
                prepareChooser();
            }
        }
    }

    private void prepareChooser() {
        Croperino.prepareChooser(NewAnouncementActivity.this, "Capture photo...",
                ContextCompat.getColor(NewAnouncementActivity.this, android.R.color.background_dark));
    }

    private void prepareCamera() {
        Croperino.prepareCamera(NewAnouncementActivity.this);
    }
}
