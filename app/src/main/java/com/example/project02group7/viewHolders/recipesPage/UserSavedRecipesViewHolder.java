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
import com.example.project02group7.database.entities.UserSavedRecipes;

import org.w3c.dom.Text;

public class UserSavedRecipesViewHolder extends RecyclerView.ViewHolder {
    private final TextView title;
    private final TextView ingredients;
    private final TextView instructions;
    private final Button deleteRecipeButton;
    private final RecipeRepository repository;
    private UserSavedRecipes recipe;

    private UserSavedRecipesViewHolder(@NonNull View recipeView) {
        super(recipeView);
        title = recipeView.findViewById(R.id.recipeSavedTitleTextView);
        ingredients = recipeView.findViewById(R.id.recipeSavedIngredientsTextView);
        instructions = recipeView.findViewById(R.id.recipeSavedInstructionsTextView);
        deleteRecipeButton = recipeView.findViewById(R.id.deleteSavedRecipesButton);
        repository = RecipeRepository.getRepository((Application)recipeView.getContext().getApplicationContext());

        deleteRecipeButton.setOnClickListener(v -> {
            new AlertDialog.Builder(recipeView.getContext())
                    .setTitle("Delete Recipe")
                    .setMessage("Are you sure you want to delete: " + recipe.getTitle() + "?")
                    .setPositiveButton("Delete", (((dialog, which) -> {
                        repository.deleteSavedRecipe(recipe);
                        Toast.makeText(recipeView.getContext(), "Deleted " + recipe.getTitle(), Toast.LENGTH_SHORT).show();
                    })))
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    public void bind(UserSavedRecipes recipe, int position) {
        this.recipe = recipe;
        title.setText(recipe.getTitle());
        ingredients.setText(recipe.getIngredients());
        instructions.setText(recipe.getInstructions());
    }

    static UserSavedRecipesViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_recipe, parent, false);
        return new UserSavedRecipesViewHolder(view);
    }
}
