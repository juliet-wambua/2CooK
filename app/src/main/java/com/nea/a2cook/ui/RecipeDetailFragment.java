package com.nea.a2cook.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nea.a2cook.Constants;
import com.nea.a2cook.R;
import com.nea.a2cook.models.Recipes;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.titleTextView)
    TextView mTitleTextView;
    @BindView(R.id.ingredientsListView)
    ListView mIngredientsListView;
    @BindView(R.id.instructionsTextView) TextView mInstructionsTextView;
    @BindView(R.id.recipeImageView)
    ImageView mRecipeImageView;
    @BindView(R.id.favourites) ImageView mFavouritesBtn;

    private static final String TAG = RecipeDetailFragment.class.getSimpleName();
    private Recipes mRecipes;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailFragment newInstance (Recipes recipes) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("recipe", Parcels.wrap(recipes));
        recipeDetailFragment.setArguments(args);
        return recipeDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this,view);
        mRecipes = Parcels.unwrap(getArguments().getParcelable("recipe"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recipe_detail_fragment, container, false);
        ButterKnife.bind(this,view);

        Picasso.get().load(mRecipes.getStrMealThumb()).into(mRecipeImageView);
        mTitleTextView.setText(mRecipes.getStrMeal());
        String instructions = mRecipes.getStrInstructions();
        String[] in = TextUtils.split(instructions,".\n");
        String inst = TextUtils.join(", \n", in);
        mInstructionsTextView.setText(inst);


        Map<String, String> ingMap = new HashMap<> ();
        ingMap.put("getIng1",mRecipes.getStrIngredient1() +" - " + mRecipes.getStrMeasure1());
        ingMap.put("getIng2",mRecipes.getStrIngredient2() +" - " + mRecipes.getStrMeasure2());
        ingMap.put("getIng3",mRecipes.getStrIngredient3() +" - " + mRecipes.getStrMeasure3());
        ingMap.put("getIng4",mRecipes.getStrIngredient4() +" - " + mRecipes.getStrMeasure4());
        ingMap.put("getIng5",mRecipes.getStrIngredient5() +" - " + mRecipes.getStrMeasure5());
        ingMap.put("getIng6",mRecipes.getStrIngredient6() +" - " + mRecipes.getStrMeasure6());
        ingMap.put("getIng7",mRecipes.getStrIngredient7() +" - " + mRecipes.getStrMeasure7());
        ingMap.put("getIng8",mRecipes.getStrIngredient8() +" - " + mRecipes.getStrMeasure8());
        ingMap.put("getIng9",mRecipes.getStrIngredient9() +" - " + mRecipes.getStrMeasure9());
        ingMap.put("getIng10",mRecipes.getStrIngredient10() +" - " + mRecipes.getStrMeasure10());
        ingMap.put("getIng11",mRecipes.getStrIngredient11() +" - " + mRecipes.getStrMeasure11());
        ingMap.put("getIng12",mRecipes.getStrIngredient12() +" - " + mRecipes.getStrMeasure12());
        ingMap.put("getIng13",mRecipes.getStrIngredient13() +" - " + mRecipes.getStrMeasure13());
        ingMap.put("getIng14",mRecipes.getStrIngredient14() +" - " + mRecipes.getStrMeasure14());
        ingMap.put("getIng15",mRecipes.getStrIngredient15() +" - " + mRecipes.getStrMeasure15());
        ingMap.put("getIng16",mRecipes.getStrIngredient16() +" - " + mRecipes.getStrMeasure16());
        ingMap.put("getIng17",mRecipes.getStrIngredient17() +" - " + mRecipes.getStrMeasure17());
        ingMap.put("getIng18",mRecipes.getStrIngredient18() +" - " + mRecipes.getStrMeasure18());
        ingMap.put("getIng19",mRecipes.getStrIngredient19() +" - " + mRecipes.getStrMeasure19());
        ingMap.put("getIng20",mRecipes.getStrIngredient20() +" - " + mRecipes.getStrMeasure20());


        String gIng = "getIng";
        List<String> ingredientList = new ArrayList<> ();
        for (int i = 1; i <= 20; i++) {
            ingredientList.add(gIng+i);
            Log.e(TAG, ingredientList.get(0));
        }

        List<String> availableIngredient = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String ingredientItem = ingMap.get(ingredientList.get(i));
            if (ingredientItem != null && ingredientItem.length() > 5 ){
                availableIngredient.add(ingredientItem);
//                Log.e(TAG, ingMap.get(ingredientList.get(i)));
                Log.e(TAG, ingMap.get(ingredientList.get(i)));
            }
        }

        ArrayAdapter<String> ingredientAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,availableIngredient);
        mIngredientsListView.setAdapter(ingredientAdapter);

        Integer itemHeight = mIngredientsListView.getHeight();
        mIngredientsListView.setLayoutParams(new LinearLayout.LayoutParams(getActivity().getWindow().getWindowManager().getDefaultDisplay().getWidth(),100*availableIngredient.size()));

        mFavouritesBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == mFavouritesBtn){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference recipeRef = FirebaseDatabase
                    .getInstance()
                    .getReference( Constants.FIREBASE_CHILD_RECIPES)
                    .child(uid);
            DatabaseReference pushRef = recipeRef.push();
            String pushId = pushRef.getKey();
            mRecipes.setPushId(pushId);
            pushRef.setValue(mRecipes);
            Toast.makeText(getContext(), "Saved",Toast.LENGTH_SHORT).show();
        }
    }
}


