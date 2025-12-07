package com.example.project02group7.viewHolders;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02group7.R;
import com.example.project02group7.database.entities.Recipe;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    private final RecyclerView innerRecyclerViewItem;
    private RecipeViewHolder(@NonNull View view) {
        super(view);
        innerRecyclerViewItem = view.findViewById(R.id.innerRecyclerView);

        // adds side scrolling animations
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(innerRecyclerViewItem);
    }

    /**
     * Description: A method to set the text of a textviews in a recycler.
     * @param recipe a String
     */
    public void bind(Recipe recipe, Context context) {
        innerRecyclerViewItem.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        final InnerRecipeAdapter adapter = new InnerRecipeAdapter(recipe);
        innerRecyclerViewItem.setAdapter(adapter);

    }

    static RecipeViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_recycler_item, parent, false);
        return new RecipeViewHolder(view);
    }
}
