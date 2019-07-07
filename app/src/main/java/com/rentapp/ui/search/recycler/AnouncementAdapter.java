package com.rentapp.ui.search.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rentapp.R;
import com.rentapp.model.Anouncement;

import java.util.ArrayList;
import java.util.List;

public class AnouncementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Anouncement> anouncements = new ArrayList<>();



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_anouncement,parent,false);
        return new AnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof AnouncementViewHolder){
            ((AnouncementViewHolder)holder).onBind(anouncements.get(position));
        }

    }



    @Override
    public int getItemCount() {
        return anouncements.size();
    }

    public void setAnouncements(List<Anouncement> anouncements){

        this.anouncements.clear();
        this.anouncements.addAll(anouncements);
        notifyDataSetChanged();
    }

}
