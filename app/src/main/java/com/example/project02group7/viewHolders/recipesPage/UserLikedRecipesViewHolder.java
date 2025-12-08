package com.example.project02group7.viewHolders.recipesPage;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02group7.R;
import com.example.project02group7.database.RecipeRepository;
import com.example.project02group7.database.entities.UserLikedRecipes;

public class UserLikedRecipesViewHolder extends RecyclerView.ViewHolder {
    private final TextView title;
    private final TextView ingredients;
    private final TextView instructions;
    private final Button deleteRecipe;
    private final RecipeRepository repository;

    private UserLikedRecipesViewHolder(@NonNull View recipeView) {
        super(recipeView);
        title = recipeView.findViewById(R.id.recipeLikedTitleTextView);
        ingredients = recipeView.findViewById(R.id.recipeLikedIngredientsTextView);
        instructions = recipeView.findViewById(R.id.recipeLikedInstructionsTextView);
        deleteRecipe = recipeView.findViewById(R.id.deleteLikedRecipeButton);
    }
}
