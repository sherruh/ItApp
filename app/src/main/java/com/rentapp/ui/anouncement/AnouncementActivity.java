package com.rentapp.ui.anouncement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.rentapp.App;
import com.rentapp.R;
import com.rentapp.model.Anouncement;
import com.rentapp.utils.Logger;
import com.rentapp.utils.Toaster;

import java.util.List;

import static android.R.layout.simple_spinner_item;

public class AnouncementActivity extends AppCompatActivity {

    public static void start(Context context){
        Intent intent = new Intent(context,AnouncementActivity.class);
        context.startActivity(intent);
    }

    private EditText editYear;
    private EditText editCity;
    private EditText editPrice;
    private Button buttonAdd;
    private ProgressBar progressBarIsUploading;
    private Spinner spinnerMarks;
    private AnouncementViewModel viewModel;


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
                .get(AnouncementViewModel.class);

        viewModel.transactionResult.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toaster.message(AnouncementActivity.this,s);
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

                ArrayAdapter<String> adapter = new ArrayAdapter(AnouncementActivity.this, simple_spinner_item,vehicleMarks);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMarks.setAdapter(adapter);
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
                int price = 20;
                viewModel.addAnouncement(new Anouncement(spinnerMarks.getSelectedItem().toString(),
                        editYear.getText().toString(),
                        editCity.getText().toString(),
                        price, App.getFirebaseUser().getUid()
                        ));
            }
        });
        spinnerMarks = findViewById(R.id.anouncement_spinner_mark);

        progressBarIsUploading = findViewById(R.id.anouncement_progress);
    }
}
