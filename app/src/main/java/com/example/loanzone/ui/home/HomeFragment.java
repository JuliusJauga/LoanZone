package com.example.loanzone.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
    private Drawable defaultDrawable;
    private Drawable redDrawable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        defaultDrawable = ContextCompat.getDrawable(getContext(), R.drawable.default_text_background);
        redDrawable = ContextCompat.getDrawable(getContext(), R.drawable.text_background_red);
        binding.loanAmount.setBackground(defaultDrawable);
        binding.termYears.setBackground(defaultDrawable);
        binding.termMonths.setBackground(defaultDrawable);
        defaultDrawable = ContextCompat.getDrawable(getContext(), R.drawable.default_text_background);
        binding.downPayment.setBackground(defaultDrawable);
        defaultDrawable = ContextCompat.getDrawable(getContext(), R.drawable.default_text_background);
        binding.percent.setBackground(defaultDrawable);
        binding.startYear.setBackground(defaultDrawable);
        binding.endMonths.setBackground(defaultDrawable);
        binding.endYear.setBackground(defaultDrawable);
        binding.startMonth.setBackground(defaultDrawable);
        binding.loanAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                defaultDrawable = ContextCompat.getDrawable(getContext(), R.drawable.default_text_background);
                binding.loanAmount.setBackground(defaultDrawable);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    defaultDrawable = ContextCompat.getDrawable(getContext(), R.drawable.default_text_background);
                    binding.loanAmount.setBackground(defaultDrawable);
                }
            }
        });
        binding.percent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    defaultDrawable = ContextCompat.getDrawable(getContext(), R.drawable.default_text_background);
                    binding.percent.setBackground(defaultDrawable);
                }
            }
        });
        binding.termYears.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    defaultDrawable = ContextCompat.getDrawable(getContext(), R.drawable.default_text_background);
                    binding.termYears.setBackground(defaultDrawable);
                    binding.termMonths.setBackground(defaultDrawable);
                }
            }
        });
        binding.termMonths.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    defaultDrawable = ContextCompat.getDrawable(getContext(), R.drawable.default_text_background);
                    binding.termYears.setBackground(defaultDrawable);
                    binding.termMonths.setBackground(defaultDrawable);
                }
            }
        });
        binding.calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean badFlag = false;
                double loan_amount = 30000;
                double annual_percent = 6;
                double down_payment = 0;
                try {
                    loan_amount = Double.parseDouble(binding.loanAmount.getText().toString());
                } catch(Exception e) {
                    redDrawable = ContextCompat.getDrawable(getContext(), R.drawable.text_background_red);
                    binding.loanAmount.setBackground(redDrawable);
                    scrollTo(binding.loanAmount);
                    badFlag = true;
                }
                try {
                    annual_percent = Double.parseDouble(binding.percent.getText().toString());
                } catch(Exception e) {
                    redDrawable = ContextCompat.getDrawable(getContext(), R.drawable.text_background_red);
                    binding.percent.setBackground(redDrawable);
                    scrollTo(binding.percent);
                    badFlag = true;
                }
                try {
                    down_payment = Double.parseDouble(binding.downPayment.getText().toString());
                } catch(Exception e) {
                    down_payment = 0;
                }
                int duration = 0;
                try {
                    duration = Integer.parseInt(binding.termYears.getText().toString()) * 12;
                } catch(Exception e) {

                }
                try {
                    duration += Integer.parseInt(binding.termMonths.getText().toString());
                } catch(Exception e) {
                    if (duration == 0) {
                        redDrawable = ContextCompat.getDrawable(getContext(), R.drawable.text_background_red);
                        binding.termMonths.setBackground(redDrawable);
                        binding.termYears.setBackground(redDrawable);
                        scrollTo(binding.termYears);
                        badFlag = true;
                    }
                }
                int postPoneStart = -1;
                int postPoneEnd = -1;
                try {
                    postPoneStart = Integer.parseInt(binding.startYear.getText().toString()) * 12;
                } catch(Exception e) {
                }
                try {
                    if (postPoneStart == -1) postPoneStart = Integer.parseInt(binding.startMonth.getText().toString());
                    else postPoneStart += Integer.parseInt(binding.startMonth.getText().toString());
                } catch(Exception e) {

                }
                try {
                    postPoneEnd = Integer.parseInt(binding.endYear.getText().toString()) * 12;
                } catch(Exception e) {

                }
                try {
                    if (postPoneEnd == -1) postPoneStart = Integer.parseInt(binding.endMonths.getText().toString());
                    else postPoneEnd += Integer.parseInt(binding.endMonths.getText().toString());
                } catch(Exception e) {

                }
                int postPoneIntStart = 0;
                int postPoneIntEnd = 0;
                if (postPoneEnd == -1) {
                    postPoneIntEnd = 0;
                }
                else postPoneIntEnd = postPoneEnd;
                if (postPoneStart == -1) {
                    postPoneIntStart = 0;
                }
                else postPoneIntStart = postPoneStart;
                if (!binding.isAnnuit.isChecked() && !binding.isLinear.isChecked()) {
                    binding.isAnnuit.setTextColor(Color.RED);
                    binding.isLinear.setTextColor(Color.RED);
                    scrollTo(binding.downPayment);
                    badFlag = true;
                }
                if (badFlag) {
                    return;
                }
                MainActivity mainActivity = (MainActivity)getActivity();
                if (mainActivity != null) {
                    MonthListGenerator.setFields(new Fields(loan_amount, down_payment, annual_percent, duration, postPoneIntStart,postPoneIntEnd, binding.isAnnuit.isChecked(), binding.isLinear.isChecked()));
                    mainActivity.onDataPass(Objects.requireNonNull(MonthListGenerator.generateList()));
                }
            }
        });
        binding.isAnnuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.isAnnuit.isChecked() || binding.isLinear.isChecked()) {
                    binding.isLinear.setChecked(false);
                    binding.isAnnuit.setChecked(true);
                    binding.isAnnuit.setTextColor(Color.BLACK);
                    binding.isLinear.setTextColor(Color.BLACK);
                }
            }
        });
        binding.isLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.isLinear.isChecked() || binding.isAnnuit.isChecked()) {
                    binding.isLinear.setChecked(true);
                    binding.isAnnuit.setChecked(false);
                    binding.isAnnuit.setTextColor(Color.BLACK);
                    binding.isLinear.setTextColor(Color.BLACK);
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
    public void scrollTo(EditText text) {
        ScrollView scrollView = binding.scrollView;
        int[] location = new int[2];
        text.getLocationInWindow(location);
        int yCoord = location[1];
        scrollView.getLocationInWindow(location);
        int scrollViewY = location[1];
        scrollView.smoothScrollTo(0, yCoord - scrollViewY);
    }
}