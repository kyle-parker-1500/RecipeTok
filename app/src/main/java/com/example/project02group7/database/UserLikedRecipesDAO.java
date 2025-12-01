package com.example.project02group7.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project02group7.database.entities.UserLikedRecipes;

import java.util.List;

@Dao
public interface UserLikedRecipesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserLikedRecipes... userLikedRecipes);

    @Delete
    void delete(UserLikedRecipes userLikedRecipes);

    @Query("SELECT * FROM " + RecipeDatabase.USER_LIKED_RECIPES_TABLE + " ORDER BY id")
    LiveData<List<UserLikedRecipes>> getAllUserLikedRecipes();

    @Query("DELETE from " + RecipeDatabase.USER_LIKED_RECIPES_TABLE)
    void deleteAll();

    @Query("SELECT * from " + RecipeDatabase.USER_LIKED_RECIPES_TABLE + " WHERE userId == :searchUserId")
    LiveData<UserLikedRecipes> getUserLikedRecipesByUserId(int searchUserId);

    // may want to add finding methods for specific lists of ingredients
}
