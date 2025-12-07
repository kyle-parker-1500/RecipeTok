package com.example.project02group7.viewHolders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.project02group7.database.RecipeRepository;
import com.example.project02group7.database.entities.Recipe;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    private final RecipeRepository repository;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        repository = RecipeRepository.getRepository(application);
    }

    public LiveData<List<Recipe>> getListOfAllRecipes() {
        //todo: This might be where we want to randomize the selection of recipes to display to recycler view
        return repository.getListOfAllRecipes();
    }

    /**
     * Description: A method to add a recipe to the repository. Should be called only by Admin
     * (with the current version of the app 12/5).
     * @param recipe a Recipe object
     */
    public void insert(Recipe recipe) {
        repository.insertRecipe(recipe);
    }
}
