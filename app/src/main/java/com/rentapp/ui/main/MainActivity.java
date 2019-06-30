package com.rentapp.ui.main;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rentapp.R;
import com.rentapp.ui.myanouncements.MyAnouncementsFragment;
import com.rentapp.ui.profile.ProfileFragment;
import com.rentapp.ui.search.SearchFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private SearchFragment searchFragment;
    private MyAnouncementsFragment myAnouncementsFragment;
    private ProfileFragment profileFragment;
    private Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_search:
                    fragment = searchFragment;
                    break;
                case R.id.navigation_my_anouncements:
                    fragment = myAnouncementsFragment;
                    break;
                case R.id.navigation_profile:
                    fragment = profileFragment;
                    break;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.bottom_frame,fragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        fragment = null;
        searchFragment = SearchFragment.newInstance();
        myAnouncementsFragment = MyAnouncementsFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_search);
    }

}
