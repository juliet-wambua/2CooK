package com.techspaceke.cookit.ui;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techspaceke.cookit.Constants;
import com.techspaceke.cookit.R;
import com.techspaceke.cookit.models.Categories;
import com.techspaceke.cookit.services.CategoriesService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName() ;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    public List<Categories> categoriesList = new ArrayList<>();
    private DatabaseReference mSearchedRecipeRef;
    private ValueEventListener mSearchedRecipeRefListener;
    FirebaseUser mUser;


    @BindView(R.id.beef) ImageButton mBeefImageButton;
    @BindView(R.id.chickenImageButton) ImageButton mChickenImageButton;
    @BindView(R.id.porkImageButton) ImageButton mPorkImageButton;
    @BindView(R.id.fishImageButton) ImageButton mFishImageButton;
    @BindView(R.id.favouritesImageViewBtn) ImageView mFavouritesImageViewButton;
    @BindView(R.id.httpProgressBar) ProgressBar mProgressBar;
    @BindView(R.id.my_toolbar) Toolbar mToolBar;
    @BindView(R.id.desc) TextView mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSearchedRecipeRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_RECIPES);

        mSearchedRecipeRefListener = mSearchedRecipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()){
                    String recipe = recipeSnapshot.getValue().toString();
                    Log.e("Recipe Updated firebase", recipe);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        mToolBar.getOverflowIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mUser = FirebaseAuth.getInstance().getCurrentUser();
//        mDesc.setText("Welcome"+mUser.getDisplayName().split(" "));

        hideProgressDialog();
        //OnClick listeners
        mFavouritesImageViewButton.setOnClickListener(this);

        ViewPump.init(ViewPump.builder()
        .addInterceptor(new CalligraphyInterceptor(
                new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/poppins.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()))
        .build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        ViewPump.init(ViewPump.builder()
        .addInterceptor(new CalligraphyInterceptor(
                new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/poppins.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()))
        .build());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search recipes");
        searchView.setElevation(4);
        searchView.setPadding(10,0,10,0);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
                intent.putExtra("meal", s);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.login:
                login();
                return true;

            case R.id.logOut:
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void showProgressDialog(){
        mProgressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgressDialog(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }


    private void listCategories(){
        showProgressDialog();
        CategoriesService.listCategories(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressDialog();
                    }
                });

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {//
                ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
                String jsonData = responseBody.string();
                Log.e(TAG, jsonData);

                categoriesList = CategoriesService.processResults(response);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        hideProgressDialog();
//
                    }
                });
            }
        });
    }

    public void saveRecipeToFirebase(String recipe) {

        mSearchedRecipeRef.push().setValue(recipe);
    }

    private void addToSharedPreferences(String recipe){
        mEditor.putString(Constants.PREFERENCES_RECIPE_KEY, recipe).apply();
    }

    @Override
    public void onClick(View view) {
        ButterKnife.bind(this);
        if (view == mFavouritesImageViewButton){
            Intent intent = new Intent(this, SavedRecipeListActivity.class);
            startActivity(intent);
        }

    }

    public void login(){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Logging out...",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void beef(View view) {
        Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
        intent.putExtra("meal", "beef");

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(mBeefImageButton, "mealTransition");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);

        startActivity(intent);
    }
    public void chicken(View view) {
        Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
        intent.putExtra("meal", "chicken");
        startActivity(intent);
    }
    public void fish(View view) {
        Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
        intent.putExtra("meal", "fish");
        startActivity(intent);
    }
    public void pork(View view) {
        Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
        intent.putExtra("meal", "pork");
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedRecipeRef.removeEventListener(mSearchedRecipeRefListener);
    }
}
