package com.example.project02group7;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.project02group7.database.RecipeRepository;
import com.example.project02group7.database.entities.Recipe;
import com.example.project02group7.database.entities.UserLikedRecipes;
import com.example.project02group7.database.entities.UserSavedRecipes;
import com.example.project02group7.databinding.ActivityMainBinding;
import com.example.project02group7.viewHolders.RecipeAdapter;
import com.example.project02group7.viewHolders.RecipeViewModel;

import java.lang.reflect.Array;
import java.util.Objects;

public class HomeFragment extends Fragment {
    RecipeRepository repository;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout for fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Like & Save Buttons
        final ImageButton likeButton = view.findViewById(R.id.likeButton);
        final ImageButton saveButton = view.findViewById(R.id.saveButton);

        // instantiate view model
        RecipeViewModel recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        // find recycler view
        RecyclerView outerRecyclerView = view.findViewById(R.id.parentRecyclerView);
        final RecipeAdapter adapter = new RecipeAdapter(new RecipeAdapter.RecipeDiff());

        // set adapter & layout manager
        outerRecyclerView.setAdapter(adapter);
        outerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // adds vertical scrolling animations
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(outerRecyclerView);

        // observe view model
        // replaced this -> getViewLifecycleOwner()
        recipeViewModel.getListOfAllRecipes().observe(getViewLifecycleOwner(), recipes -> {
            adapter.submitList(recipes);
        });


        likeButton.setOnClickListener(like -> {
            // get the repository
            repository = RecipeRepository.getRepository(requireActivity().getApplication());

            // get shared preferences userId
            SharedPreferences sharedPreferences = requireActivity().getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

            LinearLayoutManager layoutManager = (LinearLayoutManager) outerRecyclerView.getLayoutManager();
            if (layoutManager != null) {
                int position = layoutManager.findFirstVisibleItemPosition();
                Recipe current = adapter.getCurrentList().get(position);

                int recipeId = current.getId();
                int userId = sharedPreferences.getInt(getString(R.string.preference_userId_key), -1);
                String ingredients = current.getIngredients();
                String instructions = current.getInstructions();

                // add current recipe to liked recipes table
                repository.insertUserLikedRecipes(new UserLikedRecipes(userId, recipeId, ingredients, instructions));
            }
        });
        saveButton.setOnClickListener(save -> {
            // get the repository
            repository = RecipeRepository.getRepository(requireActivity().getApplication());

            // getting currently logged in userId
            SharedPreferences sharedPreferences = requireActivity().getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

            LinearLayoutManager layoutManager = (LinearLayoutManager) outerRecyclerView.getLayoutManager();
            if (layoutManager != null) {
                int position = layoutManager.findFirstVisibleItemPosition();
                Recipe current = adapter.getCurrentList().get(position);

                int recipeId = current.getId();
                int userId = sharedPreferences.getInt(getString(R.string.preference_userId_key), -1);
                String ingredients = current.getIngredients();
                String instructions = current.getInstructions();

                // add current recipe to liked recipes table
                repository.insertUserSavedRecipes(new UserSavedRecipes(userId, recipeId, ingredients, instructions));
            }
        });

        return view;
    }
}