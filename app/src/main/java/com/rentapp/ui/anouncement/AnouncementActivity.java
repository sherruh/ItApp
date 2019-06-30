package com.rentapp.ui.anouncement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rentapp.App;
import com.rentapp.R;
import com.rentapp.model.Vehicle;
import com.rentapp.utils.Toaster;

public class AnouncementActivity extends AppCompatActivity {

    public static void start(Context context){
        Intent intent = new Intent(context,AnouncementActivity.class);
        context.startActivity(intent);
    }

    private EditText editTite;
    private EditText editYear;
    private EditText editCity;
    private EditText editPrice;
    private Button buttonAdd;
    private ProgressBar progressBarIsUploading;
    private AnouncementViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anouncement);

        initView();

        initViewModel();
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
    }

    private void initView() {

        editTite = findViewById(R.id.anouncement_edit_title);
        editYear = findViewById(R.id.anouncement_edit_year);
        editCity = findViewById(R.id.anouncement_edit_city);
        editPrice = findViewById(R.id.anouncement_edit_price);
        buttonAdd = findViewById(R.id.anouncement_buttona_save);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = 20;
                viewModel.addAnouncement(new Vehicle(editTite.getText().toString(),
                        editYear.getText().toString(),
                        editCity.getText().toString(),
                        price, App.getFirebaseUser().getUid()
                        ));
            }
        });

        progressBarIsUploading = findViewById(R.id.anouncement_progress);
    }
}
