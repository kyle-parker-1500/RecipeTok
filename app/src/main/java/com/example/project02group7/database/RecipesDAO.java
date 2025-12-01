package com.example.project02group7.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project02group7.database.entities.Recipes;

import java.util.List;

@Dao
public interface RecipesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipes... recipe);

    @Delete
    void delete(Recipes recipe);

    // may be too many recipes to get all of (may want to filter if data > 5k recipes)
    @Query("SELECT * FROM " + RecipeDatabase.RECIPE_TABLE + " ORDER BY id")
    LiveData<List<Recipes>> getAllRecipes();

    @Query("DELETE from " + RecipeDatabase.RECIPE_TABLE)
    void deleteAll();

    @Query("SELECT * from " + RecipeDatabase.RECIPE_TABLE + " WHERE id == :recipeId")
    LiveData<Recipes> getRecipeByRecipeId(int recipeId);

    // may want to add finding methods for specific lists of ingredients
}
