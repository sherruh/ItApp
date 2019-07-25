package com.rentapp.ui.search.recycler;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rentapp.R;
import com.rentapp.model.Anouncement;

import java.util.ArrayList;
import java.util.List;

public class AnouncementViewHolder extends RecyclerView.ViewHolder {

    private TextView textTitle;
    private TextView textPrice;
    private List<ImageView> images;

    public AnouncementViewHolder(@NonNull View itemView) {
        super(itemView);

        textPrice = itemView.findViewById(R.id.item_anouncement_price);
        textTitle = itemView.findViewById(R.id.item_anouncement_title);
        images = new ArrayList<>();
        images.add((ImageView) itemView.findViewById(R.id.item_anouncement_image1));
        images.add((ImageView) itemView.findViewById(R.id.item_anouncement_image2));
        images.add((ImageView) itemView.findViewById(R.id.item_anouncement_image3));
        images.add((ImageView) itemView.findViewById(R.id.item_anouncement_image4));
    }

    public void onBind(Anouncement anouncement){

        textTitle.setText( "Title: " + anouncement.getTitle());
        textPrice.setText( "Price: " + String.valueOf(anouncement.getPrice()));

        if (anouncement.getImagesUrl() == null) {
            for (ImageView imageView : images) {
                imageView.setImageDrawable(null);
            }
        }else{
            for(int i = 0; i < anouncement.getImagesUrl().size(); i++){
                Glide.with(images.get(i)).load(anouncement.getImagesUrl().get(i)).into(images.get(i));
            }
        }


    }




}
