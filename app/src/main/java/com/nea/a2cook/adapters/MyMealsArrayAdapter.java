package com.nea.a2cook.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MyMealsArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mMeals;
    private String[] mPrice;

    public MyMealsArrayAdapter(Context mContext, int resource, String[] mMeals, String[] mPrice){
        super(mContext, resource);
        this.mContext = mContext;
        this.mMeals = mMeals;
        this.mPrice = mPrice;
    }

    @Override
    public Object getItem(int position) {
        String emojis = mMeals[position];
        String meaning = mPrice[position];
        return String.format("%s \nServes great: %s", emojis, meaning);
    }

    @Override
    public int getCount() {
        return mPrice.length;
    }

}
