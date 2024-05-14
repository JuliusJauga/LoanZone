package com.example.loanzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loanzone.ui.dashboard.DashboardFragment;
import com.example.loanzone.ui.home.HomeFragment;
import com.example.loanzone.ui.home.OnDataPassListener;
import com.example.loanzone.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.loanzone.databinding.ActivityMainBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDataPassListener {
    DashboardFragment fragment;
    List<Month> monthList;
    @Override
    public void onDataPass(List<Month> monthList) {
        this.monthList = monthList;
    }

    private boolean flag;
    private ActivityMainBinding binding;
    private static Fields input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monthList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        //replaceFragment(new HomeFragment());
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        binding.navView.setOnItemSelectedListener(item -> {
            NavController navController1 = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            Bundle args = new Bundle();
            if (item.getItemId() == R.id.navigation_dashboard) {
                args.putSerializable("monthList", (Serializable)monthList);
                navController1.navigate(R.id.navigation_dashboard, args);
            }
            else if (item.getItemId() == R.id.navigation_home) {
                navController1.navigate(R.id.navigation_home);
            }
            else if (item.getItemId() == R.id.navigation_notifications) {
                navController1.navigate(R.id.navigation_notifications);
            }
            return true;
        });

    }
    public static Fields getInput() {
        return input;
    }

    public static void setInput(Fields input) {
        MainActivity.input = input;
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Find if there's any fragment already added to the container
        Fragment existingFragment = fragmentManager.findFragmentById(R.id.navigation_dashboard);

        // Remove the existing fragment if it exists
        if (existingFragment != null) {
            fragmentTransaction.remove(existingFragment);
        }

        // Replace the fragment
        fragmentTransaction.replace(R.id.navigation_dashboard, fragment);
        fragmentTransaction.addToBackStack(null); // Add this line to enable back navigation
        fragmentTransaction.commit();
    }
    /*private void loadInitialFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.navigation_dashboard, )
    }*/
}