package com.techspaceke.cookit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.techspaceke.cookit.R;

public class TrendingAdapter  extends BaseAdapter {

    private Context mContext;
    private String[] mDescription;
    public TrendingAdapter(Context context, String[] description){
        this.mContext = context;
        this.mDescription = description;
    }

    @Override
    public int getCount() {
        return mDescription.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;

        if(convertView == null) {
            //getlayout from xml
            gridView = inflater.inflate(R.layout.trending_item, null);

            //pull up views
            TextView trendView = gridView.findViewById(R.id.trendDescTextView);
            //set value into views
            trendView.setText(String.valueOf(mDescription[position]));
        }
        else {
            gridView = convertView;
        }
        return gridView;
    }


}
