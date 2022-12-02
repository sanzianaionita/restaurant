package com.example.restaurant18.ui.favorite_products;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoriteProductsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FavoriteProductsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}