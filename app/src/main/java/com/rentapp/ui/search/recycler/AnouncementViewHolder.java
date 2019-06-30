package com.rentapp.ui.search.recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rentapp.R;
import com.rentapp.model.Vehicle;

public class AnouncementViewHolder extends RecyclerView.ViewHolder {

    private TextView textTitle;
    private TextView textPrice;

    public AnouncementViewHolder(@NonNull View itemView) {
        super(itemView);

        textPrice = itemView.findViewById(R.id.item_anouncement_price);
        textTitle = itemView.findViewById(R.id.item_anouncement_title);
    }

    public void onBind(Vehicle vehicle){

        textTitle.setText( "Title: " + vehicle.getTitle());
        textPrice.setText( "Price: " + String.valueOf(vehicle.getPrice()));

    }




}
