package com.rentapp.ui.profile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.rentapp.App;
import com.rentapp.R;
import com.rentapp.ui.login.LoginActivity;
import com.rentapp.utils.Logger;


public class ProfileFragment extends Fragment {

    private ImageView imageProfile;
    private TextView textProfileName;
    private TextView textProfileMail;
    private Button buttonExit;

    private ProfileViewModel viewModel;

    public static ProfileFragment newInstance(){
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageProfile = getActivity().findViewById(R.id.profile_image);
        textProfileMail = getActivity().findViewById(R.id.profile_mail);
        textProfileName = getActivity().findViewById(R.id.profile_name);
        buttonExit= getActivity().findViewById(R.id.profile_button_exit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.logOut();
            }
        });

        initViewModel();

    }




    @Override
    public void onResume() {
        super.onResume();
        viewModel.checkLogginStatus();
    }

    private void initViewModel() {

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        viewModel.isLogged.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) showUserInfo();
                else {
                    Logger.message("Login activity is not logged");
                    LoginActivity.start(getContext());}
            }
        });
    }

    private void showUserInfo() {
        FirebaseUser user = App.getFirebaseUser();
        String imageUrl = user.getPhotoUrl().toString();
        if(FacebookAuthProvider.PROVIDER_ID.equals(user.getProviderId())) {
            imageUrl = "https://graph.facebook.com/" + user.getUid() + "/picture?height=1000";
        }
        Glide.with(imageProfile).load(imageUrl).into(imageProfile);
        textProfileName.setText(App.getFirebaseUser().getDisplayName());
        textProfileMail.setText(App.getFirebaseUser().getEmail());
    }


}
