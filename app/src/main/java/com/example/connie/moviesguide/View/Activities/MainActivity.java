package com.example.connie.moviesguide.View.Activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.connie.moviesguide.R;
import com.example.connie.moviesguide.View.fragments.DiscoverFragments;
import com.example.connie.moviesguide.View.fragments.FavoritesFragment;
import com.example.connie.moviesguide.View.fragments.MoviesFragment;
import com.example.connie.moviesguide.View.fragments.SeriesFragment;

public class MainActivity extends AppCompatActivity   {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private DiscoverFragments discoverFragments;
    private MoviesFragment moviesFragment;
    private SeriesFragment seriesFragment;
    private FavoritesFragment favoritesFragment;
    private Context context;
    private boolean isConnected;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         bottomNavigationView = findViewById(R.id.main_bottom_nav);
         frameLayout = findViewById(R.id.main_frame);


         discoverFragments = new DiscoverFragments();
         moviesFragment =  new MoviesFragment();
         seriesFragment = new SeriesFragment();
         favoritesFragment =  new FavoritesFragment();
        setFragment(discoverFragments);





        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
                switch(menuItem.getItemId()){
                    case R.id.action_discover:
                        setFragment(discoverFragments);
                        return  true;

                    case R.id.action_movies:
                        setFragment(moviesFragment);
                        return true;

                    case R.id.action_series:
                        setFragment(seriesFragment);
                        return true;

                    case R.id.action_favorites:
                        setFragment(favoritesFragment);
                        return true;

                        default:
                            return false;
                }
                }

    });
    }
    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    public  boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInternet = connectivityManager.getActiveNetworkInfo();
        isConnected = activeInternet!= null && activeInternet.isConnectedOrConnecting();
        return isConnected;
    }

    public void onStart() {
        super.onStart();



    }


}