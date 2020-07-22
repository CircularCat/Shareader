package com.catfee.shareader.activity.ui.bookstore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookStoreViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BookStoreViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is bookstore fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}