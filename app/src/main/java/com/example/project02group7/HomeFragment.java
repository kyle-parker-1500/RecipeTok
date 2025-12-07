package com.example.project02group7;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.project02group7.database.RecipeRepository;
import com.example.project02group7.database.entities.Recipe;
import com.example.project02group7.databinding.ActivityMainBinding;
import com.example.project02group7.viewHolders.RecipeAdapter;
import com.example.project02group7.viewHolders.RecipeViewModel;

public class HomeFragment extends Fragment {
    RecipeRepository repository;
    public HomeFragment() {
        repository = RecipeRepository.getRepository();
    }
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
            LinearLayoutManager layoutManager = (LinearLayoutManager) outerRecyclerView.getLayoutManager();
            if (layoutManager != null) {
                int position = layoutManager.findFirstVisibleItemPosition();
                Recipe current = adapter.getCurrentList().get(position);

                // add current recipe to liked recipes table

            }

        });
        saveButton.setOnClickListener(save -> {
        });

        return view;
    }
}