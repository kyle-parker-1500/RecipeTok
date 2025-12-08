package com.example.project02group7.viewHolders.recipesPage;

import android.util.Log;
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
        Log.d("LikedAdapter", "onCreateViewHolder called");
        return UserLikedRecipesViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull UserLikedRecipesViewHolder holder, int position) {
        Log.d("LikedAdapter", "onBindViewHolder called with " + position);
        UserLikedRecipes current = recipes.get(position);
        Log.d("LikedAdapter", "Binding recipe " + current.getTitle());
        holder.bind(current, position);
    }

    @Override
    public int getItemCount() {
        Log.d("LikedAdapter", "getItemCount called with " + recipes.size() + " recipes");
        return recipes.size();
    }

    public void setRecipes(List<UserLikedRecipes> newRecipes) {
        Log.d("LikedAdapter", "setRecipes called with " + newRecipes.size() + " recipes");
        this.recipes = newRecipes;
        notifyDataSetChanged();
    }
}
