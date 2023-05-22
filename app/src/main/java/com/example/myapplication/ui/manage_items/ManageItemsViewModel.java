package com.example.myapplication.ui.manage_items;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageItemsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ManageItemsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}