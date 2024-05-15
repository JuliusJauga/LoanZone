package com.example.loanzone;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriterCSV {
    private List<Month> data;
    public WriterCSV(List<Month> other) {
        data = new ArrayList<>(other);
    }
    public void write(String filePath, String[] headers) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            writer.writeNext(headers);
            for (int i = 0; i < data.size(); i++) {
                String[] row = {
                        Integer.toString(i+1),
                        String.format("%.2f", data.get(i).getMonthly_payment()),
                        String.format("%.2f", data.get(i).getMonthly_interest()),
                        String.format("%.2f", data.get(i).getRemaining_balance())
                };
                writer.writeNext(row);
            }
        } catch (IOException e) {
            System.out.println("Failed to write");
        }
    }
    public void writeAndroid(String filePath, String[] headers) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            writer.writeNext(headers);
            for (int i = 0; i < data.size(); i++) {
                String[] row = {
                        Integer.toString(i+1),
                        String.format("%.2f", data.get(i).getMonthly_payment()),
                        String.format("%.2f", data.get(i).getMonthly_interest()),
                        String.format("%.2f", data.get(i).getRemaining_balance())
                };
                writer.writeNext(row);
            }
        } catch (IOException e) {
            System.out.println("Failed to write");
        }
    }
    public List<Month> getData() {
        return data;
    }

    public void setData(List<Month> data) {
        this.data = data;
    }
}
