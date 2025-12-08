package com.example.project02group7.viewHolders.recipesPage;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02group7.database.entities.UserLikedRecipes;

import java.util.List;

public class UserLikedRecipesAdapter extends RecyclerView.Adapter<UserLikedRecipesViewHolder> {
    private List<UserLikedRecipes> recipes;

    public UserLikedRecipesAdapter(List<UserLikedRecipes> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public UserLikedRecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return UserLikedRecipesViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull UserLikedRecipesViewHolder holder, int position) {
        UserLikedRecipes current = recipes.get(position);
        holder.bind(current, position);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(List<UserLikedRecipes> newRecipes) {
        this.recipes = newRecipes;
        notifyDataSetChanged();
    }
}
