package com.rentapp.ui.search;

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
import android.widget.ProgressBar;

import com.google.android.material.appbar.AppBarLayout;
import com.rentapp.R;
import com.rentapp.model.Anouncement;
import com.rentapp.ui.anouncementdetails.AnouncementsDetailsActivity;
import com.rentapp.ui.search.recycler.AnouncementAdapter;
import com.rentapp.ui.search.recycler.AnouncementViewHolder;
import com.rentapp.utils.Logger;

import java.util.List;


public class SearchFragment extends Fragment implements AnouncementViewHolder.OnAnouncementClick {

    private SearchViewModel viewModel;
    private RecyclerView recyclerView;
    private AnouncementAdapter adapter;
    private EditText editSearch;
    private AppBarLayout appBarLayout;
    private ProgressBar progressBarIsLoading;

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
        adapter = new AnouncementAdapter( this);
        recyclerView.setAdapter(adapter);

        appBarLayout = getActivity().findViewById(R.id.appbar);
        progressBarIsLoading = getActivity().findViewById(R.id.fragment_search_progress_is_loading);
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
            viewModel.vehiclesLiveData.observe(getActivity(), new Observer<List<Anouncement>>() {
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
        }else {
            adapter.setAnouncements(viewModel.vehiclesLiveData.getValue());
            recyclerView.scrollToPosition(viewModel.getAdapterPosition());
        }

    }

    @Override
    public void onPause() {
        appBarLayout.setExpanded(true);
        super.onPause();
    }

    @Override
    public void onClick(int i) {
        AnouncementsDetailsActivity.start(getContext(),
                viewModel.vehiclesLiveData.getValue().get(i));
    }

}
