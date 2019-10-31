package com.nea.a2cook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IngredientsArrayAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mIngredients;
    private int mTextView;
    private int mLayout;

    public IngredientsArrayAdapter(Context mContext, String[] ingredients, int listItem, int layout) {
        this.mContext = mContext;
        this.mIngredients = ingredients;
        this.mTextView = listItem;
        this.mLayout = layout;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return mIngredients.length;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listView;
        if (convertView == null) {
            listView = inflater.inflate(mLayout, null);

            TextView listItemView = listView.findViewById(mTextView);

            listItemView.setText(String.valueOf(mIngredients[position]));

        } else {
            listView = convertView;
        }
        return listView;
    }
}
