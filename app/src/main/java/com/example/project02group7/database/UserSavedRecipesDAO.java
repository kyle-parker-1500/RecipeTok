package com.example.project02group7.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project02group7.database.entities.UserSavedRecipes;

import java.util.List;

@Dao
public interface UserSavedRecipesDAO {
    // todo: revisit conflict strategy & determine if it works
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserSavedRecipes... savedRecipes);

    @Delete
    void delete(UserSavedRecipes savedRecipes);

    // saved in ascending order by default
    @Query("SELECT * FROM " + RecipeDatabase.USER_SAVED_RECIPES_TABLE + " ORDER BY id")
    LiveData<List<UserSavedRecipes>> getAllUserSavedRecipes();

    @Query("DELETE from " + RecipeDatabase.USER_SAVED_RECIPES_TABLE)
    void deleteAll();

    /**
     * Use this when you want the full db object (I think that's how it's sent anyways).
     * @param userId int
     * @return LiveData<List<UserLikedRecipes>>
     */
    @Query("SELECT * FROM " + RecipeDatabase.USER_SAVED_RECIPES_TABLE + " WHERE userId = :userId")
    LiveData<List<UserSavedRecipes>> getSavedRecipesByUserId(int userId);

    @Query("SELECT * from " + RecipeDatabase.USER_SAVED_RECIPES_TABLE + " WHERE recipeId == :recipeId")
    LiveData<UserSavedRecipes> getSavedRecipeByRecipeId(int recipeId);

    @Query("DELETE FROM " + RecipeDatabase.USER_SAVED_RECIPES_TABLE + " WHERE userId == :userId AND recipeId == :recipeId")
    void unsave(int userId, int recipeId);
}
