package com.example.project02group7;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project02group7.viewHolders.recipesPage.UserLikedRecipesAdapter;
import com.example.project02group7.viewHolders.recipesPage.UserLikedRecipesViewModel;

import java.util.ArrayList;

public class LikedRecipesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("LikedRecipes", "onCreateView called");
        // Inflate layout for fragment
        View view = inflater.inflate(R.layout.fragment_liked_recipes, container, false);

        // get userId
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt(getString(R.string.preference_userId_key), -1);

        Log.d("LikedRecipes", "UserId from sharedPreferences " + userId);

        // Instantiate VM
        UserLikedRecipesViewModel recipeViewModel = new ViewModelProvider(this).get(UserLikedRecipesViewModel.class);

        // Find recycler view
        RecyclerView recyclerView = view.findViewById(R.id.likedRecipesRecyclerView);

        if (recyclerView == null) {
            Log.e("LikedRecipes", "Recycler view is NULL!");
            return view;
        }

        // layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        // adapter
        final UserLikedRecipesAdapter adapter = new UserLikedRecipesAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        recyclerView.post(() -> {
            Log.d("LikedRecipes", "RecyclerView width: " + recyclerView.getWidth() + ", height " + recyclerView.getHeight());
        });

        // observe VM
        recipeViewModel.getLikedRecipesByUserId(userId).observe(getViewLifecycleOwner(), recipes -> {
            Log.d("LikedRecipes", "Observer triggered - Recieved " + recipes.size() + " recipes for user " + userId);
            adapter.setRecipes(recipes);
        });

        return view;
    }
}