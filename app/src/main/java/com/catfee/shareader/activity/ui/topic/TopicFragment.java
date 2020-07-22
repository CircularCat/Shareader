package com.catfee.shareader.activity.ui.topic;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.catfee.shareader.R;
import com.catfee.shareader.activity.ui.mine.MineViewModel;

public class TopicFragment extends Fragment {

    private TopicViewModel mTopicViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mTopicViewModel =
                new ViewModelProvider(this).get(TopicViewModel.class);
        View root = inflater.inflate(R.layout.fragment_friends, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        mTopicViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}
