package com.catfee.shareader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.catfee.shareader.R;

import java.util.Map;

public class ShelfAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;

    private InnerItemOnclickListener mListener;

    int[ ] size = new int[ 5 ];

    public ShelfAdapter (Context context) {
        mContext = context;
    }

    @ Override
    public int getCount () {

        if ( size.length > 3 ) {
            return size.length;
        } else {
            return 3;
        }

    }

    @ Override
    public Object getItem ( int position ) {

        return size[ position ];
    }

    @ Override
    public long getItemId ( int position ) {

        return position;
    }

    @ Override
    public View getView (int position , View convertView , ViewGroup parent ) {
        final ViewHolder viewHolder;
        if(null == convertView){
            viewHolder = new ViewHolder();
            LayoutInflater layout_inflater = LayoutInflater.from(mContext);
            convertView = layout_inflater.inflate ( R.layout.bookshelf_list_item , null );

            viewHolder.fl1 = convertView.findViewById(R.id.FrameLayout1);
            viewHolder.fl2 = convertView.findViewById(R.id.FrameLayout2);
            viewHolder.fl3 = convertView.findViewById(R.id.FrameLayout3);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.fl1.findViewById(R.id.imageView_1).setOnClickListener(this);
        viewHolder.fl2.findViewById(R.id.imageView_2).setOnClickListener(this);
        viewHolder.fl3.findViewById(R.id.imageView_3).setOnClickListener(this);

        return convertView;
    }

    //回调接口，点击书本封面的事件
    interface InnerItemOnclickListener{
        void bookClick(View v);
    }

    public void setOnInnerItemOnclickListener(InnerItemOnclickListener listener){
        this.mListener = listener;
    }

    //点击事件监听
    @Override
    public void onClick(View v) {
        mListener.bookClick(v);
    }

    public final static class ViewHolder{
        FrameLayout fl1, fl2, fl3;
    }

}
