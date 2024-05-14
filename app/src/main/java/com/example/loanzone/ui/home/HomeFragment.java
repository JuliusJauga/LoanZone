package com.example.loanzone.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.loanzone.Fields;
import com.example.loanzone.MainActivity;
import com.example.loanzone.MonthListGenerator;
import com.example.loanzone.R;
import com.example.loanzone.databinding.FragmentHomeBinding;
import com.example.loanzone.ui.dashboard.DashboardFragment;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Fields fields;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        final EditText loan = binding.loanAmount;
        binding.downPayment.setText(loan.getText());
        binding.calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final EditText loan = binding.loanAmount;
                //final double loan_amount = Double.parseDouble(binding.loanAmount.getText().toString());
                //final double annual_percent = Double.parseDouble(binding.percent.getText().toString());
                //final double down_payment = Double.parseDouble(binding.loanAmount.getText().toString());
                //final int duration = Integer.parseInt(binding.termYears.getText().toString()) * 12 + Integer.parseInt(binding.termMonths.getText().toString());
                //MainActivity.setInput(new Fields(loan_amount, down_payment, annual_percent, duration, 0,0, binding.isAnnuit.isChecked(), binding.isLinear.isChecked()));
                //binding.downPayment.setText(loan.getText());
                /*DashboardFragment dashboardFragment =    (DashboardFragment) getActivity()
                                                                            .getSupportFragmentManager()
                                                                            .findFragmentById(R.id.fragment_container);
                */
                MainActivity mainActivity = (MainActivity)getActivity();
                if (mainActivity != null) {
                    System.out.println("Happens?");
                    // You have the reference to the DashboardFragment
                    // Now you can call its methods or access its members
                    MonthListGenerator.setFields(new Fields(30000, 600, 6, 30*12, 0,0,true, false));
                    mainActivity.onDataPass(Objects.requireNonNull(MonthListGenerator.generateList()));
                } else {
                    System.out.println("Happens?NOT");
                    binding.downPayment.setText("666");
                    // DashboardFragment not found
                }
            }
        });
        binding.isAnnuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.isAnnuit.isChecked() || binding.isLinear.isChecked()) {
                    binding.isLinear.setChecked(false);
                    binding.isAnnuit.setChecked(true);
                }
            }
        });
        binding.isLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.isLinear.isChecked() || binding.isAnnuit.isChecked()) {
                    binding.isLinear.setChecked(true);
                    binding.isAnnuit.setChecked(false);
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}