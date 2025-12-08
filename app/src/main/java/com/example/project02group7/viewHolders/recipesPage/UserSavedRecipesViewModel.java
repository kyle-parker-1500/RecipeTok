package com.example.project02group7.viewHolders.recipesPage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.project02group7.database.RecipeRepository;
import com.example.project02group7.database.entities.UserSavedRecipes;

import java.util.List;

public class UserSavedRecipesViewModel extends AndroidViewModel {
    private final RecipeRepository repository;

    public UserSavedRecipesViewModel(@NonNull Application application) {
        super(application);
        repository = RecipeRepository.getRepository(application);
    }

    public LiveData<List<UserSavedRecipes>> getSavedRecipesByUserId(int userId) {
        return repository.getSavedRecipesByUserId(userId);
    }
}
