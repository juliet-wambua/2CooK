package com.techspaceke.cookit.adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.techspaceke.cookit.models.Recipes;
import com.techspaceke.cookit.ui.RecipeDetailFragment;

import java.util.ArrayList;

public class RecipePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Recipes> mRecipes;

    public RecipePagerAdapter(FragmentManager fm, ArrayList<Recipes> recipes) {
        super(fm);
        mRecipes = recipes;
    }

    @Override
    public Fragment getItem(int position) {
        return RecipeDetailFragment.newInstance(mRecipes.get(position));
    }

    @Override
    public int getCount() {
        return mRecipes.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mRecipes.get(position).getStrMeal();
    }

}
