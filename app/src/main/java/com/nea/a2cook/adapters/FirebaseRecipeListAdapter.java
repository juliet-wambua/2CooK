package com.nea.a2cook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.nea.a2cook.R;
import com.nea.a2cook.models.Recipes;

public class FirebaseRecipeListAdapter extends FirebaseRecyclerAdapter<Recipes, FirebaseRecipeViewHolder> implements com.nea.a2cook.util.ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private com.nea.a2cook.util.OnStartDragListener mOnStartDragListener;
    private Context mContext;


    public FirebaseRecipeListAdapter(FirebaseRecyclerOptions<Recipes> options,
                                     DatabaseReference ref,
                                     com.nea.a2cook.util.OnStartDragListener onStartDragListener,
                                     Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull FirebaseRecipeViewHolder holder, int position, Recipes recipes) {
        holder.bindRecipe(recipes);
        holder.mRecipeImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @NonNull
    @Override
    public FirebaseRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.recipe_list_item_drag, parent,false);
        return new FirebaseRecipeViewHolder(view);
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}
