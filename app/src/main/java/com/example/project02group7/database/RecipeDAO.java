package com.example.project02group7.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project02group7.database.entities.Recipe;

import java.util.List;

@Dao
public interface RecipeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipe... recipe);

    @Delete
    void delete(Recipe recipe);

    // may be too many recipes to get all of (may want to filter if data > 5k recipes)
    @Query("SELECT * FROM " + RecipeDatabase.RECIPE_TABLE + " ORDER BY id")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("DELETE from " + RecipeDatabase.RECIPE_TABLE)
    void deleteAll();

    @Query("SELECT * from " + RecipeDatabase.RECIPE_TABLE + " WHERE id == :recipeId")
    LiveData<Recipe> getRecipeByRecipeId(int recipeId);

    // get actual liked recipe data
    @Query("SELECT title, description, instructions, ingredients FROM recipeTable INNER JOIN userLikedRecipesTable on userLikedRecipesTable.RecipeId = recipeTable.id")
    LiveData<List<Recipe>> getLikedRecipesForUser(int userId);

    // get saved recipe data
    @Query("SELECT title, description, instructions, ingredients FROM recipeTable INNER JOIN userSavedRecipesTable on userSavedRecipesTable.RecipeId = recipeTable.id")
    LiveData<List<Recipe>> getSavedRecipesForUser(int userId);

    // may want to add finding methods for specific lists of ingredients
}
