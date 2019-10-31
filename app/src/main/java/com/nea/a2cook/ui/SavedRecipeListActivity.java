package com.nea.a2cook.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nea.a2cook.Constants;
import com.nea.a2cook.R;
import com.nea.a2cook.adapters.FirebaseRecipeListAdapter;
import com.nea.a2cook.adapters.FirebaseRecipeViewHolder;
import com.nea.a2cook.models.Recipes;
import com.nea.a2cook.util.ItemTouchHelperAdapter;
import com.nea.a2cook.util.SimpleItemTouchHelperCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SavedRecipeListActivity extends AppCompatActivity {
    private DatabaseReference mRecipeReference;
    private FirebaseRecyclerAdapter<Recipes, FirebaseRecipeViewHolder> mFirebaseSavedRecipeListAdapter;
    private FirebaseRecipeListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.savedRecipeToolbar)
    Toolbar mToolBar;
    @BindView(R.id.savedRecipeListProgressBar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipe_list);
        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.getOverflowIcon().setColorFilter( Color.BLACK, PorterDuff.Mode.SRC_ATOP);

        setUpFirebaseAdapter();
        //import font
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor (
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/sans_pro.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext( ViewPumpContextWrapper.wrap(newBase));
    }
    private void setUpFirebaseAdapter(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        mRecipeReference = FirebaseDatabase
                .getInstance()
                .getReference( Constants.FIREBASE_CHILD_RECIPES)
                .child(uid);
        FirebaseRecyclerOptions<Recipes> options = new FirebaseRecyclerOptions.Builder<Recipes>()
                .setQuery(mRecipeReference, Recipes.class)
                .build();
        mFirebaseAdapter = new FirebaseRecipeListAdapter (options,mRecipeReference,this::onStartDrag,this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager (this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        mRecyclerView.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback ( (ItemTouchHelperAdapter) mFirebaseAdapter );
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        hideProgressDialog();

    }



    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mFirebaseAdapter != null){
            mFirebaseAdapter.stopListening();
        }
    }

    public void showProgressDialog(){
        mProgressBar.setVisibility( View.VISIBLE);
    }
    public void hideProgressDialog(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
