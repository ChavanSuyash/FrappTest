package com.frapp.test.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.frapp.test.R;
import com.frapp.test.favourite.FavouriteFragment;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    final String TAG_HOME_FRAGMENT = "HOME_FRAGMENT";
    final String TAG_FAVOURITE_FRAGMENT = "FAVOURITE_FRAGMENT";

    boolean shouldShowHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        shouldShowHomeFragment = true;

        initFragment(HomeFragment.newInstance(),FavouriteFragment.newInstance());

        //Setup BottomNavigationView
        BottomNavigationView bottomNavigation = findViewById(R.id.navigationView);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initFragment(Fragment homeFragment, Fragment favoriteFragment) {
        // Add the NotesFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fl_fragment, homeFragment, TAG_HOME_FRAGMENT);
        transaction.add(R.id.fl_fragment, favoriteFragment, TAG_FAVOURITE_FRAGMENT);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(shouldShowHomeFragment){
            fragmentSwitch(TAG_HOME_FRAGMENT, TAG_FAVOURITE_FRAGMENT);
        }else {
            fragmentSwitch(TAG_FAVOURITE_FRAGMENT,TAG_HOME_FRAGMENT);
            shouldShowHomeFragment = false;
        }
    }

    private void fragmentSwitch(String toShow, String toDetach){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.detach(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag(toDetach)));
        fragmentTransaction.attach(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag(toShow)));

        fragmentTransaction.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();

    }

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.navigation_home :
                    fragmentSwitch(TAG_HOME_FRAGMENT,TAG_FAVOURITE_FRAGMENT);
                    shouldShowHomeFragment = true;
                    return true;
                case R.id.navigation_favourite :
                    fragmentSwitch(TAG_FAVOURITE_FRAGMENT,TAG_HOME_FRAGMENT);
                    shouldShowHomeFragment = false;
                    return true;

            }
            return false;
        }
    };
}
