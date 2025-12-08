package com.example.project02group7.viewHolders.recipesPage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.project02group7.database.RecipeRepository;
import com.example.project02group7.database.entities.UserLikedRecipes;

import java.util.List;

public class UserLikedRecipesViewModel extends AndroidViewModel {
    private final RecipeRepository repository;

    // constructor
    public UserLikedRecipesViewModel(@NonNull Application application) {
        super(application);
        repository = RecipeRepository.getRepository(application);
    }

    /**
     * Description: Gets a list of all UserLikedRecipes
     * @return LiveData<List<UserLikedRecipes>> basically a list
     */
    public LiveData<List<UserLikedRecipes>> getLikedRecipesByUserId(int userId) {
        return repository.getLikedRecipesByUserId(userId);
    }

    /**
     * Description: A method to add a recipe to the repository. Should only be
     * called by Admin.
     * @param recipe a UserLikedRecipes object
     */
    public void insert(UserLikedRecipes recipe) {
        repository.insertUserLikedRecipes(recipe);
    }
}
