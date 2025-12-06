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

import com.example.project02group7.databinding.ActivityMainBinding;
import com.example.project02group7.viewHolders.RecipeAdapter;
import com.example.project02group7.viewHolders.RecipeViewModel;

public class HomeFragment extends Fragment {
    private ActivityMainBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout for fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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

        return view;
    }
}