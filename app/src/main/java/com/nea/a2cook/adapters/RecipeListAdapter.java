package com.nea.a2cook.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nea.a2cook.R;
import com.nea.a2cook.models.Recipes;
import com.nea.a2cook.ui.RecipeDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    String TAG = RecipeListAdapter.class.getSimpleName();


    private List<Recipes> mRecipes = new ArrayList<> ();
    private Context mContext;

    public RecipeListAdapter(Context context, List<Recipes> recipes){
        mContext = context;
        mRecipes = recipes;
    }

    @Override
    public RecipeListAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.recipe_list_item, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.RecipeViewHolder holder, int position) {
        holder.bindRecipe(mRecipes.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        @BindView(R.id.recipeImageView)
        ImageView mRecipeImageView;
        @BindView(R.id.recipeCategoryTextView)
        TextView mRecipeCategoryTextView;
        @BindView(R.id.recipeAreaTextView) TextView mRecipeAreaTextView;
        @BindView(R.id.recipeNameTextView) TextView mRecipeNameTextView;


        private Context mContext;

        public RecipeViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindRecipe(Recipes recipe) {

            Picasso.get().load(recipe.getStrMealThumb()).into(mRecipeImageView);
            mRecipeNameTextView.setText(recipe.getStrMeal());
            mRecipeCategoryTextView.setText(recipe.getStrCategory());
            mRecipeAreaTextView.setText(recipe.getStrArea());


        }


        @Override
        public void onClick(View view) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, RecipeDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("recipe", Parcels.wrap(mRecipes));
            mContext.startActivity(intent);
        }
    }
}
