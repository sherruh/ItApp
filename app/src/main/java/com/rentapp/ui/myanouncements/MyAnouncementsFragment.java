package com.rentapp.ui.myanouncements;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rentapp.R;
import com.rentapp.model.Vehicle;
import com.rentapp.ui.anouncement.AnouncementActivity;
import com.rentapp.ui.search.recycler.AnouncementAdapter;

import java.util.List;


public class MyAnouncementsFragment extends Fragment {

    public static MyAnouncementsFragment newInstance() {
        MyAnouncementsFragment fragment = new MyAnouncementsFragment();
        return fragment;
    }

    private FloatingActionButton buttonAddAnouncement;
    private RecyclerView recyclerView;
    private AnouncementAdapter adapter;

    private MyAnouncementsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_anouncements, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initViewModel();
    }

    private void initViewModel() {

        viewModel = ViewModelProviders.of(this).get(MyAnouncementsViewModel.class);
        viewModel.anouncementsOfUserLiveData.observe(this, new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(List<Vehicle> vehicles) {
                adapter.setVehicles(vehicles);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getUserAnouncements();
    }

    private void initView() {

        buttonAddAnouncement = getActivity().findViewById(R.id.my_anouncements_button_add);
        buttonAddAnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnouncementActivity.start(getContext());
            }
        });

        recyclerView = getActivity().findViewById(R.id.my_anouncements_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AnouncementAdapter();
        recyclerView.setAdapter(adapter);
    }
}
