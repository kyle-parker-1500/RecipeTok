package com.example.project02group7.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project02group7.database.entities.User;
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

    /**
     * Use this when you want the full db object (I think that's how it's sent anyways).
     * @param userId int
     * @return LiveData<List<UserLikedRecipes>>
     */
    @Query("SELECT * from " + RecipeDatabase.USER_LIKED_RECIPES_TABLE + " WHERE userId = :userId")
    LiveData<List<UserLikedRecipes>> getLikedRecipesByUserId(int userId);

    @Query("SELECT * from " + RecipeDatabase.USER_LIKED_RECIPES_TABLE + " WHERE id == :recipeId")
    LiveData<UserLikedRecipes> getLikedRecipesById(int recipeId);

    // removes item from database if unliked
    @Query("DELETE FROM " + RecipeDatabase.USER_LIKED_RECIPES_TABLE + " WHERE userId == :userId AND recipeId == :recipeId")
    void unlike(int userId, int recipeId);

    // may want to add finding methods for specific lists of ingredients
}
