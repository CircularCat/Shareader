package com.catfee.shareader.activity.ui.topic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TopicViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public TopicViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is topic fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
