package com.example.project02group7.viewHolders;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02group7.database.entities.Recipe;

public class InnerRecipeAdapter extends RecyclerView.Adapter<InnerRecipeViewHolder> {
    private Recipe recipe;
    public final int VIEW_TYPE_CARD_1 = 0;
    public final int VIEW_TYPE_CARD_2 = 1;

    public InnerRecipeAdapter(Recipe recipe) {
        this.recipe = recipe;
    }

    @NonNull
    @Override
    public InnerRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_CARD_1) {
            return InnerRecipeViewHolder.create(parent, getItemViewType(viewType));
        } else {
            return InnerRecipeViewHolder.create(parent, getItemViewType(viewType));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull InnerRecipeViewHolder holder, int position) {
        holder.bind(recipe, position);
    }

    // define # of cards
    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_CARD_1;
        } else {
            return VIEW_TYPE_CARD_2;
        }
    }
}