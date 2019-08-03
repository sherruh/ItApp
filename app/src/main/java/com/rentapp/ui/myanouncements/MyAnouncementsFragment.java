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
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rentapp.R;
import com.rentapp.model.Anouncement;
import com.rentapp.ui.newanouncement.NewAnouncementActivity;
import com.rentapp.ui.search.recycler.AnouncementAdapter;
import com.rentapp.ui.search.recycler.AnouncementViewHolder;

import java.util.List;


public class MyAnouncementsFragment extends Fragment implements AnouncementViewHolder.OnAnouncementClick {

    public static MyAnouncementsFragment newInstance() {
        MyAnouncementsFragment fragment = new MyAnouncementsFragment();
        return fragment;
    }

    private FloatingActionButton buttonAddAnouncement;
    private RecyclerView recyclerView;
    private AnouncementAdapter adapter;
    private ProgressBar progressBarIsLoading;

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
        viewModel.anouncementsOfUserLiveData.observe(this, new Observer<List<Anouncement>>() {
            @Override
            public void onChanged(List<Anouncement> anouncements) {
                adapter.setAnouncements(anouncements);
            }
        });
        viewModel.isLoading.observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) progressBarIsLoading.setVisibility(View.VISIBLE);
                else progressBarIsLoading.setVisibility(View.GONE);
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
                NewAnouncementActivity.start(getContext());
            }
        });

        progressBarIsLoading = getActivity().findViewById(R.id.fragment_my_anouncements_progress_is_loading);

        recyclerView = getActivity().findViewById(R.id.my_anouncements_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AnouncementAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(int i) {

    }
}
