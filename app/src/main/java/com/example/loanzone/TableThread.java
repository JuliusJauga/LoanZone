package com.example.loanzone;

import android.os.Handler;

import java.util.List;

public class TableThread extends Thread {
    private Handler mHandler;
    List<Month> monthList;
    public TableThread(Handler handler, List<Month> monthList) {
        mHandler = handler;
        this.monthList = monthList;
    }
    @Override
    public void run() {










        mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }}
