package com.example.restaurant18.ui.profile_settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileSettingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ProfileSettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}