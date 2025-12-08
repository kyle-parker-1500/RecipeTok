package com.example.project02group7.viewHolders.recipesPage;

import android.app.AlertDialog;
import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02group7.R;
import com.example.project02group7.database.RecipeRepository;
import com.example.project02group7.database.entities.UserLikedRecipes;

public class UserLikedRecipesViewHolder extends RecyclerView.ViewHolder {
    private final TextView title;
    private final TextView ingredients;
    private final TextView instructions;
    private final Button deleteRecipeButton;
    private final RecipeRepository repository;
    private UserLikedRecipes recipes;

    private UserLikedRecipesViewHolder(@NonNull View recipeView) {
        super(recipeView);
        title = recipeView.findViewById(R.id.recipeLikedTitleTextView);
        ingredients = recipeView.findViewById(R.id.recipeLikedIngredientsTextView);
        instructions = recipeView.findViewById(R.id.recipeLikedInstructionsTextView);
        deleteRecipeButton = recipeView.findViewById(R.id.deleteLikedRecipeButton);
        repository = RecipeRepository.getRepository((Application)recipeView.getContext().getApplicationContext());

        deleteRecipeButton.setOnClickListener(v -> {
            new AlertDialog.Builder(recipeView.getContext())
                    .setTitle("Delete Recipe")
                    .setMessage("Are you sure you want to delete: " + recipes.getTitle() + "?")
                    .setPositiveButton("Delete", (((dialog, which) -> {
                        repository.deleteLikedRecipe(recipes);
                        Toast.makeText(recipeView.getContext(), "Deleted " + recipes.getTitle(), Toast.LENGTH_SHORT).show();
                    })))
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    public void bind(UserLikedRecipes recipes, int position) {
        this.recipes = recipes;
        title.setText(recipes.getTitle());
        ingredients.setText(recipes.getIngredients());
        instructions.setText(recipes.getInstructions());
    }

    static UserLikedRecipesViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_liked_recipe, parent, false);
        return new UserLikedRecipesViewHolder(view);
    }
}
