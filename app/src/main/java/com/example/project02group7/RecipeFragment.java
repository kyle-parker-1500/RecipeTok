package com.example.project02group7;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project02group7.viewHolders.recipesPage.RecipesPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RecipeFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private RecipesPagerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate layout
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);

        // get tab layout & view pager
        tabLayout = view.findViewById(R.id.recipesPageTabLayout);
        viewPager = view.findViewById(R.id.recipesPageViewPager);

        viewPager.post(() -> {
            adapter = new RecipesPagerAdapter(requireActivity());
            viewPager.setAdapter(adapter);

            // connect tabs
            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                tab.setText(position == 0 ? "Liked" : "Saved");
            }).attach();
        });

        return view;
    }

    // preventing crashes after view has been destroyed
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewPager = null;
        adapter = null;
        tabLayout = null;
    }
}