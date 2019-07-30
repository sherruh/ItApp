package com.rentapp.ui.anouncementdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rentapp.R;
import com.rentapp.model.Anouncement;

import java.util.ArrayList;
import java.util.List;


public class AnouncementsDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_ANOUNCEMENT = "Anouncement";

    public static void start(Context context, Anouncement anouncement){

        Intent intent = new Intent(context,AnouncementsDetailsActivity.class);
        intent.putExtra(AnouncementsDetailsActivity.EXTRA_ANOUNCEMENT,  anouncement);
        context.startActivity(intent);
    }

    private List<ImageView> imageViews = new ArrayList<>();
    private TextView textPrice;
    private Anouncement anouncement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anouncements_details);
        Intent intent = getIntent();
        anouncement = (Anouncement) intent.getSerializableExtra(AnouncementsDetailsActivity.EXTRA_ANOUNCEMENT);
        initView();
    }

    private void initView() {

        imageViews.add((ImageView) findViewById(R.id.anouncemets_details_image1));
        imageViews.add((ImageView) findViewById(R.id.anouncemets_details_image2));
        imageViews.add((ImageView) findViewById(R.id.anouncemets_details_image3));
        imageViews.add((ImageView) findViewById(R.id.anouncemets_details_image4));
        textPrice = findViewById(R.id.anouncemets_details_text_price);

        for (int i = 0; i < anouncement.getImagesUrl().size(); i++){

            Glide.with(this)
                    .load(anouncement.getImagesUrl().get(i))
                    .apply(new RequestOptions()
                            .centerCrop()
                    )
                    .into(imageViews.get(i));

        }
        String price = getString(R.string.price, anouncement.getPrice());
        textPrice.setText(price);

    }
}
