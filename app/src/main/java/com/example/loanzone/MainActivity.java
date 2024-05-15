package com.example.loanzone;

import android.os.Bundle;

import com.example.loanzone.ui.dashboard.DashboardFragment;
import com.example.loanzone.ui.home.HomeFragment;
import com.example.loanzone.ui.notifications.NotificationsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.loanzone.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    boolean dataPassed = false;
    List<Month> monthList;
    final HomeFragment fragmentHome = new HomeFragment();
    final DashboardFragment fragmentDash = new DashboardFragment();
    final NotificationsFragment fragmentNot = new NotificationsFragment();
    Fragment active = fragmentHome;
    FragmentManager fragmentManager = getSupportFragmentManager();

    public void onDataPass(List<Month> monthList) {
        dataPassed = true;
        this.monthList = monthList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monthList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        com.example.loanzone.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        Fragment home = fragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main);
        assert home != null;
        fragmentManager.beginTransaction().detach(home).commit();
        fragmentManager.beginTransaction().add(R.id.container, fragmentDash, "dash").hide(fragmentDash).commit();
        fragmentManager.beginTransaction().add(R.id.container, fragmentHome, "home").commit();
        fragmentManager.beginTransaction().add(R.id.container, fragmentNot, "not").hide(fragmentNot).commit();
        active = fragmentHome;
        binding.navView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_dashboard) {
                if (dataPassed) {
                    fragmentDash.redraw(monthList);
                    dataPassed = false;
                }
                fragmentManager.beginTransaction().hide(active).show(fragmentDash).commit();
                active = fragmentDash;
            }
            else if (item.getItemId() == R.id.navigation_home) {
                fragmentManager.beginTransaction().hide(active).show(fragmentHome).commit();
                active = fragmentHome;
            }
            else if (item.getItemId() == R.id.navigation_notifications) {
                fragmentManager.beginTransaction().hide(active).show(fragmentNot).commit();
                active = fragmentNot;
            }
            return true;
        });
    }
}