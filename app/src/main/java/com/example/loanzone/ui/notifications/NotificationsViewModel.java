package com.example.loanzone.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hello! This is a loan calculator app, use the home fragment for your data input, after pressing the calculate button, you can go to dashboard fragment to see information about your loan. You can also save the data to a CSV file.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}