package com.catfee.shareader.activity.ui.bookshelf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.catfee.shareader.R;
import com.catfee.shareader.adapter.ShelfAdapter;


public class BookShelfFragment extends Fragment {

    //private BookShelfViewModel mBookShelfViewModel;

    private ShelfAdapter mAdapter;
    private ListView shelf_list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //mBookShelfViewModel =
                //new ViewModelProvider(this).get(BookShelfViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bookshelf, container, false);



        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        init();
        mAdapter = new ShelfAdapter(getActivity());
        shelf_list.setAdapter(mAdapter);
    }

    private void init () {
        shelf_list = ( ListView )getActivity().findViewById( R.id.shelf_list );
    }


}
