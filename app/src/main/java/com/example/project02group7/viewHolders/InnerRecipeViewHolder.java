package com.example.project02group7.viewHolders;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02group7.R;
import com.example.project02group7.database.entities.Recipe;

public class InnerRecipeViewHolder extends RecyclerView.ViewHolder {
    private final TextView titleViewItem;
    private final TextView ingredientViewItem;
    private final TextView instructionsViewItem;
    private InnerRecipeViewHolder(@NonNull View recipeView) {
        super(recipeView);
        titleViewItem = recipeView.findViewById(R.id.innerRecipeTitle);
        ingredientViewItem = recipeView.findViewById(R.id.innerRecipeIngredients);
        instructionsViewItem = recipeView.findViewById(R.id.innerRecipeInstructions);
    }

    /**
     * Description: A method to set the text of a textviews in a recycler.
     * @param recipe a String
     */
    public void bind(Recipe recipe, int position) {
        if (position == 0) {
            // inside card 1:
            // display title and ingredients
            titleViewItem.setText(recipe.getTitle());
            ingredientViewItem.setText(recipe.getIngredients());
            ingredientViewItem.setMovementMethod(new ScrollingMovementMethod());
        } else {
            // inside card 2:
            // display only instructions
            instructionsViewItem.setText(recipe.getInstructions());
            instructionsViewItem.setMovementMethod(new ScrollingMovementMethod());
        }
    }

    static InnerRecipeViewHolder create(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            // card 1
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_recipe_card_1, parent, false);
        } else {
            // card 2
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_recipe_card_2, parent, false);
        }
        return new InnerRecipeViewHolder(view);
    }
}
