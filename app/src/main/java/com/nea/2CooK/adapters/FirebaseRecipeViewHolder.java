package com.nea.2CooK.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.techspaceke.cookit.Constants;
import com.techspaceke.cookit.R;
import com.techspaceke.cookit.models.Recipes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirebaseRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public  @BindView(R.id.recipeImageView) ImageView mRecipeImageView;
    @BindView(R.id.recipeCategoryTextView) TextView mRecipeCategoryTextView;
    @BindView(R.id.recipeAreaTextView) TextView mRecipeAreaTextView;
    @BindView(R.id.recipeNameTextView) TextView mRecipeNameTextView;


    View mView;
    Context mContext;

    public FirebaseRecipeViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindRecipe(Recipes recipe) {
        ButterKnife.bind(this,mView);

        Picasso.get().load(recipe.getStrMealThumb()).into(mRecipeImageView);
        mRecipeNameTextView.setText(recipe.getStrMeal());
        mRecipeCategoryTextView.setText(recipe.getStrCategory());
        mRecipeAreaTextView.setText(recipe.getStrArea());
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Recipes> recipes = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RECIPES);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    recipes.add(snapshot.getValue(Recipes.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
