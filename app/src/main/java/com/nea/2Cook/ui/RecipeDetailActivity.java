package com.techspaceke.cookit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.techspaceke.cookit.R;
import com.techspaceke.cookit.adapters.RecipePagerAdapter;
import com.techspaceke.cookit.models.Recipes;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class RecipeDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager) ViewPager mViewpager;
    private RecipePagerAdapter mAdapterViewPager;
    ArrayList<Recipes> mRecipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        mRecipes = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        int startingPosition = getIntent().getIntExtra("position", 0);
        mAdapterViewPager = new RecipePagerAdapter(getSupportFragmentManager(), mRecipes);
        mViewpager.setAdapter(mAdapterViewPager);
        mViewpager.setCurrentItem(startingPosition);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/sans_pro.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}
