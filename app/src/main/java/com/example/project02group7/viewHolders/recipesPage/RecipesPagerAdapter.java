package com.example.project02group7.viewHolders.recipesPage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.project02group7.LikedRecipesFragment;
import com.example.project02group7.SavedRecipesFragment;

public class RecipesPagerAdapter extends FragmentStateAdapter {
    public RecipesPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public int getItemCount() {
        return 2; // 2 pages
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new LikedRecipesFragment();
        } else {
            return new SavedRecipesFragment();
        }
    }
}
