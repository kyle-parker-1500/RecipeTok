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
    void insert(UserSavedRecipes... userSavedRecipes);

    @Delete
    void delete(UserSavedRecipes userSavedRecipes);

    // saved in ascending order by default
    @Query("SELECT * FROM " + RecipeDatabase.USER_SAVED_RECIPES_TABLE + " ORDER BY id")
    LiveData<List<UserSavedRecipes>> getAllUserSavedRecipes();

    @Query("DELETE from " + RecipeDatabase.USER_SAVED_RECIPES_TABLE)
    void deleteAll();

    @Query("SELECT * from " + RecipeDatabase.USER_SAVED_RECIPES_TABLE + " WHERE userId == :searchUserId")
    LiveData<UserSavedRecipes> getUserSavedRecipesByUserId(int searchUserId);

}
