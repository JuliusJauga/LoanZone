package com.example.loanzone.ui.dashboard;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.loanzone.Month;
import com.example.loanzone.R;
import com.example.loanzone.TableThread;
import com.example.loanzone.databinding.FragmentDashboardBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
public class DashboardFragment extends Fragment {

    private Handler mHandler;
    static boolean firstLoad = true;
    private FragmentDashboardBinding binding;

    private List<Month> monthList = new ArrayList<>();

    boolean firstTime = true;

    LineChart lineChart;
    LineDataSet lineDataSet;
    LineData lineData;
    ArrayList lineEntries;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            monthList.addAll((List<Month>)getArguments().getSerializable("monthList"));
        }


        mHandler = new Handler(Looper.getMainLooper());

        // Create a Runnable to update the UI
        Runnable updateUIRunnable = new Runnable() {
            @Override
            public void run() {
                // Update UI here
                // You can put your UI update code here
                TableLayout layout = binding.payTable;
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                params.setMargins(2, 2, 2, 2);
                for (int i = 0; i < monthList.size(); i++) {
                    final int index = i;
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Month month = monthList.get(index);
                            TableRow tableRow = new TableRow(requireContext());

                            TextView textView1 = new TextView(requireContext());
                            textView1.setText(month.getIndexS());
                            textView1.setPadding(8, 8, 8, 8);
                            textView1.setBackgroundResource(R.drawable.border); // Set the background drawable for the border
                            textView1.setLayoutParams(params); // Apply layout parameters
                            tableRow.addView(textView1);
                            TextView textView2 = new TextView(requireContext());
                            textView2.setText(month.getMonthly_paymentS());
                            textView2.setPadding(8, 8, 8, 8);
                            textView2.setBackgroundResource(R.drawable.border); // Set the background drawable for the border
                            textView2.setLayoutParams(params);
                            tableRow.addView(textView2);

                            TextView textView3 = new TextView(requireContext());
                            textView3.setText(month.getMonthly_interestS());
                            textView3.setPadding(8, 8, 8, 8);
                            textView3.setBackgroundResource(R.drawable.border); // Set the background drawable for the border
                            textView3.setLayoutParams(params);
                            tableRow.addView(textView3);

                            TextView textView4 = new TextView(requireContext());
                            textView4.setText(month.getRemaining_balanceS());
                            textView4.setPadding(8, 8, 8, 8);
                            textView4.setBackgroundResource(R.drawable.border); // Set the background drawable for the border
                            textView4.setLayoutParams(params);
                            tableRow.addView(textView4);

                            layout.addView(tableRow);
                        }
                    }, i); // Delay each row addition by 20 milliseconds
                }
            }
        };

        // Create a background thread
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Perform background tasks here
                // For example, processing your monthList
                mHandler.post(updateUIRunnable);
            }
        });

        // Start the background thread
        backgroundThread.start();



        binding.fragmentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableLayout tableLayout = binding.payTable;

                if (tableLayout.getChildCount() > 1) {
                    if (tableLayout.getChildAt(1).getVisibility() == View.GONE) {
                        for (int i = 1; i < tableLayout.getChildCount(); i++) {
                            tableLayout.getChildAt(i).setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        for (int i = 1; i < tableLayout.getChildCount(); i++) {
                            tableLayout.getChildAt(i).setVisibility(View.GONE);
                        }
                    }
                }
            }
        });


        lineChart = binding.lineChart;
        getData();
        lineDataSet = new LineDataSet(lineEntries, "Monthly payments");
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setValueTextColor(Color.RED);
        lineDataSet.setLineWidth(0.5f);
        lineDataSet.setCircleRadius(0.2f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setDrawCircles(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setTextSize(12f);

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setDrawGridLines(false);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridColor(Color.LTGRAY);

        Legend legend = lineChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextSize(10f);
        legend.setTextColor(Color.BLACK);


        lineChart.setDrawBorders(true);
        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.setPadding(8,8,8,8);
        lineChart.getDescription().setEnabled(false);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setBorderColor(Color.LTGRAY);
        lineChart.setExtraBottomOffset(10f);
        //Description description = new Description();
        //description.setText("Month");
        //lineChart.setDescription(description);


        lineChart.invalidate();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void FillUp() {
        System.out.println("Happens?");
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 2, 2, 2);
        TableLayout layout = binding.payTable;
        TableRow row = (TableRow) layout.getChildAt(0);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
        shapeDrawable.getPaint().setColor(Color.BLACK); // Border color
        shapeDrawable.getPaint().setStyle(Paint.Style.STROKE); // Border style
        shapeDrawable.getPaint().setStrokeWidth(10); // Border width
        row.setBackground(shapeDrawable);
        row.getChildAt(0).setBackgroundResource(R.drawable.border);
        row.getChildAt(0).setLayoutParams(params);

        row.getChildAt(1).setBackgroundResource(R.drawable.border);
        row.getChildAt(1).setLayoutParams(params);

        row.getChildAt(2).setBackgroundResource(R.drawable.border);
        row.getChildAt(2).setLayoutParams(params);

        row.getChildAt(3).setBackgroundResource(R.drawable.border);
        row.getChildAt(3).setLayoutParams(params);

        for (Month month : monthList) {
            TableRow tableRow = new TableRow(requireContext());

            TextView textView1 = new TextView(requireContext());
            textView1.setText(month.getIndexS());
            textView1.setPadding(8, 8, 8, 8);
            textView1.setBackgroundResource(R.drawable.border); // Set the background drawable for the border
            textView1.setLayoutParams(params); // Apply layout parameters
            tableRow.addView(textView1);
            TextView textView2 = new TextView(requireContext());
            textView2.setText(month.getMonthly_paymentS());
            textView2.setPadding(8, 8, 8, 8);
            textView2.setBackgroundResource(R.drawable.border); // Set the background drawable for the border
            textView2.setLayoutParams(params);
            tableRow.addView(textView2);

            TextView textView3 = new TextView(requireContext());
            textView3.setText(month.getMonthly_interestS());
            textView3.setPadding(8, 8, 8, 8);
            textView3.setBackgroundResource(R.drawable.border); // Set the background drawable for the border
            textView3.setLayoutParams(params);
            tableRow.addView(textView3);

            TextView textView4 = new TextView(requireContext());
            textView4.setText(month.getRemaining_balanceS());
            textView4.setPadding(8, 8, 8, 8);
            textView4.setBackgroundResource(R.drawable.border); // Set the background drawable for the border
            textView4.setLayoutParams(params);
            tableRow.addView(textView4);
            layout.addView(tableRow);
        }
    }
    private void getData() {
        lineEntries = new ArrayList<>();
        for (Month month : monthList) {
            lineEntries.add(new Entry((float) month.getIndex(), (float) month.getRemaining_balance()));
        }
    }
}