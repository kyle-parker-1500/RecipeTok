package com.example.project02group7.viewHolders.recipesPage;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02group7.database.entities.UserSavedRecipes;

import java.util.List;

public class UserSavedRecipesAdapter extends RecyclerView.Adapter<UserSavedRecipesViewHolder> {
    private List<UserSavedRecipes> recipes;

    public UserSavedRecipesAdapter(List<UserSavedRecipes> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public UserSavedRecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return UserSavedRecipesViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull UserSavedRecipesViewHolder holder, int position) {
        UserSavedRecipes current = recipes.get(position);
        holder.bind(current, position);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(List<UserSavedRecipes> newRecipes) {
        this.recipes = newRecipes;
        notifyDataSetChanged();
    }
}
