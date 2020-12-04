package com.fardeen.budget_planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static float Balance;
    public float getBalance(){ return Balance;}
    public void  setBalance(float newBalance){ MainActivity.Balance = newBalance;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Load Prefs
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        Balance = sharedPreferences.getFloat("BALANCE", 0.0f);

        Bundle bundle = new Bundle();
        bundle.putFloat("Balance", Balance);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fragment
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                homeFragment).commit();
        bottomNav.getMenu().getItem(1).setChecked(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = new Bundle();
        bundle.putFloat("Balance", Balance);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                homeFragment).commit();
    }

    @Override
    protected void onPause () {
        super.onPause();
        //Prefs
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("BALANCE", Balance);
        editor.apply();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            Bundle bundle = new Bundle();
                            bundle.putFloat("Balance", Balance);
                            HomeFragment homeFragment = new HomeFragment();
                            homeFragment.setArguments(bundle);
                            selectedFragment = homeFragment;
                            break;
                        case R.id.nav_stats:
                            selectedFragment = new StatsFragment();
                            break;
                        case R.id.nav_history:
                            selectedFragment = new HistoryFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                            selectedFragment).commit();
                    return true;
                }
            };
}