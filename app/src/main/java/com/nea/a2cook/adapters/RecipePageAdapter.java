package com.nea.a2cook.adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nea.a2cook.models.Recipes;
import com.nea.a2cook.ui.RecipeDetailFragment;

import java.util.ArrayList;

public class RecipePageAdapter extends FragmentPagerAdapter {
    private ArrayList<Recipes> mRecipes;

    public RecipePageAdapter(FragmentManager fm, ArrayList<Recipes> recipes) {
        super(fm);
        mRecipes = recipes;
    }

    public RecipePageAdapter(FragmentManager fm) {
        super ( fm );
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


