package com.rentapp.ui.search;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.rentapp.R;
import com.rentapp.model.Vehicle;
import com.rentapp.ui.search.recycler.AnouncementAdapter;
import com.rentapp.utils.Logger;

import java.util.List;


public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;
    private RecyclerView recyclerView;
    private AnouncementAdapter adapter;
    private EditText editSearch;
    private AppBarLayout appBarLayout;
    private int recyclerPostiion;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();
        viewModel.getAllVehicles();

    }

    private void initView() {

        recyclerView = getActivity().findViewById(R.id.search_recycler);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AnouncementAdapter();
        recyclerView.setAdapter(adapter);

        appBarLayout = getActivity().findViewById(R.id.appbar);

        editSearch = getActivity().findViewById(R.id.search_edit_search);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                appBarLayout.setExpanded(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getFilteredAnouncements(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initViewModel() {

        if (viewModel == null) {
            viewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
            viewModel.vehiclesLiveData.observe(getActivity(), new Observer<List<Vehicle>>() {
                @Override
                public void onChanged(List<Vehicle> vehicles) {
                    adapter.setVehicles(vehicles);
                }
            });
        }else {
            adapter.setVehicles(viewModel.vehiclesLiveData.getValue());
            recyclerView.scrollToPosition(viewModel.getAdapterPosition());
        }
    }

    @Override
    public void onPause() {
        viewModel.saveAdapterPostition(recyclerPostiion);
        appBarLayout.setExpanded(true);
        super.onPause();
    }

    //TODO find adapter Position
}
