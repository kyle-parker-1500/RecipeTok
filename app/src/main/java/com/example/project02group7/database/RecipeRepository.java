package com.example.project02group7.database;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.project02group7.MainActivity;
import com.example.project02group7.database.entities.Recipe;
import com.example.project02group7.database.entities.User;
import com.example.project02group7.database.entities.UserLikedRecipes;
import com.example.project02group7.database.entities.UserSavedRecipes;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RecipeRepository {
    private RecipeDAO recipeDAO;
    private UserLikedRecipesDAO userLikedRecipesDAO;
    private UserSavedRecipesDAO userSavedRecipesDAO;
    private final UserDAO userDAO;
//    private ArrayList<String> allUsers;

    private static RecipeRepository repository;

    // constructor
    private RecipeRepository(Application application) {
        RecipeDatabase db = RecipeDatabase.getDatabase(application);
        this.recipeDAO = db.recipeDAO();
        this.userLikedRecipesDAO = db.userLikedRecipesDAO();
        this.userSavedRecipesDAO = db.userSavedRecipesDAO();
        this.userDAO = db.userDAO();
    }

    // singleton RecipeRepository
    public static RecipeRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        // submitting task to future
        Future<RecipeRepository> future = RecipeDatabase.databaseWriteExecutor.submit(
                new Callable<RecipeRepository>() {
                    @Override
                    public RecipeRepository call() throws Exception {
                        return new RecipeRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when getting RecipeRepository, thread error.");
        }
        // once safe return null
        return null;
    }

    /**
     * Description: A void method that inserts any number of users passed into the database.
     * Each new user added will generate a unique UserId.
     * @param user a User object
     */
    public void insertUser(User... user) {
        RecipeDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

    /**
     * Description: A void method that deletes any number of users passed into the database.
     * @param user a User object
     */
    public void deleteUser(User user) {
        RecipeDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.delete(user);
        });
    }

    // may not want this public

    /**
     * Description: Gets a list of all users in the database.
     * @return LiveData<List<User>>
     */
    public LiveData<List<User>> getListOfAllUsers() {
        return userDAO.getAllUsers();
    }

    /**
     * Description: A method that takes a String username and returns a user of type
     * LiveData<User> from the Database.
     * @param username a String
     * @return LiveData<User>
     */
    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    /**
     * Description: A method that takes an int userId and returns a LiveData<User> user.
     * @param userId an int
     * @return LiveData<User>
     */
    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    // recipes section of repository

    /**
     * Description: A method that takes any number of recipes and inserts them into the Recipe table of the
     * database.
     * @param recipe a Recipe
     */
    public void insertRecipe(Recipe... recipe) {
        RecipeDatabase.databaseWriteExecutor.execute(() -> {
            recipeDAO.insert(recipe);
        });
    }

    /**
     * Description: A method that returns a list of type LiveData that returns a list of all recipes.
     * @return LiveData<List<Recipe>>
     */
    public LiveData<List<Recipe>> getListOfAllRecipes() {
        return recipeDAO.getAllRecipes();
    }

    /**
     * Description: Returns a recipe of type Recipe that is at the specified recipeId
     * @param recipeId an int
     * @return LiveData<Recipe>
     */
    public LiveData<Recipe> getRecipeByRecipeId(int recipeId) {
        return recipeDAO.getRecipeByRecipeId(recipeId);
    }

    // user liked recipes section

    /**
     * Description: A method that takes any number of recipes liked by a user and inserts them into the userLiked recipe
     * table of the database.
     * @param userLikedRecipes a Recipe
     */
    public void insertUserLikedRecipes(UserLikedRecipes... userLikedRecipes) {
        RecipeDatabase.databaseWriteExecutor.execute(() -> {
            userLikedRecipesDAO.insert(userLikedRecipes);
        });
    }

    /**
     * Description: A method that takes any number of recipes saved by a user and inserts them into the userSaved recipe
     * table of the database.
     * @param userSavedRecipes a Recipe
     */
    public void insertUserSavedRecipes(UserSavedRecipes... userSavedRecipes) {
        RecipeDatabase.databaseWriteExecutor.execute(() -> {
            userSavedRecipesDAO.insert(userSavedRecipes);
        });
    }

    /**
     * Description: Returns a recipe of type UserLikedRecipes that is at the specified recipeId
     * @param recipeId an int
     * @return LiveData<UserLikedRecipes>
     */
    public LiveData<UserLikedRecipes> getLikedRecipeByRecipeId(int recipeId) {
        return userLikedRecipesDAO.getLikedRecipesById(recipeId);
    }

    /**
     * Description: Gets all recipes liked by userId
     * @param userId an Int
     * @return LiveData<List<UserLikedRecipes>>
     */
    public LiveData<List<UserLikedRecipes>> getLikedRecipesByUserId(int userId) {
        return userLikedRecipesDAO.getLikedRecipesByUserId(userId);
    }

    /**
     * Description: Gets all recipes liked by userId
     * @param userId an Int
     * @return LiveData<List<UserSavedRecipes>>
     */
    public LiveData<List<UserSavedRecipes>> getSavedRecipesByUserId(int userId) {
        return userSavedRecipesDAO.getSavedRecipesByUserId(userId);
    }

    /**
     * Description: Returns a recipe of type UserSavedRecipes that is at the specified recipeId
     * @param recipeId an int
     * @return LiveData<UserSavedRecipes>
     */
    public LiveData<UserSavedRecipes> getSavedRecipeByRecipeId(int recipeId) {
        return userSavedRecipesDAO.getSavedRecipeByRecipeId(recipeId);
    }

    /**
     * Description: Method to check if liked recipe already exists in table.
     * @param recipe a Recipe object
     * @return true/false - true if duplicate found, false if not
     */
    public boolean checkIfDuplicateLikedRecipe(Recipe recipe) {
        int recipeId = recipe.getId();
        return getLikedRecipeByRecipeId(recipeId) == null;
    }

    /**
     * Description: Method to check if liked recipe already exists in table.
     * @param recipe a Recipe object
     * @return true/false - true if duplicate found, false if not
     */
    public boolean checkIfDuplicateSavedRecipe(Recipe recipe) {
        int recipeId = recipe.getId();
        return getSavedRecipeByRecipeId(recipeId) == null;
    }
    // todo: write unlike method for user & delete methods for admin

    /**
     * Description: A method that returns a list of     type LiveData that returns a list of all userLikedRecipes.
     * @return LiveData<List<UserLikedRecipes>>
     */
    public LiveData<List<UserLikedRecipes>> getAllUserLikedRecipes() {
        return userLikedRecipesDAO.getAllUserLikedRecipes();
    }

    /**
     * Description: A method that returns a list of type LiveData that returns a list of all userSavedRecipes.
     * @return LiveData<List<UserSavedRecipes>>
     */
    public LiveData<List<UserSavedRecipes>> getAllUserSavedRecipes() {
        return userSavedRecipesDAO.getAllUserSavedRecipes();
    }
    // gets recipe data by user id
    /**
     * Description: Returns a recipe of type Recipe that is at the specified userId
     * @param userId an int
     * @return LiveData<List<Recipe>>
     */
    public LiveData<List<Recipe>> getLikedRecipesForUser(int userId) {
        return recipeDAO.getLikedRecipesForUser(userId);
    }

    /**
     * Description: Delete user liked recipe.
     */
    public void deleteLikedRecipe(UserLikedRecipes likedRecipe) {
        RecipeDatabase.databaseWriteExecutor.execute(() -> {
            userLikedRecipesDAO.delete(likedRecipe);
        });
    }

    /**
     * Description: Delete user saved recipe.
     */
    public void deleteSavedRecipe(UserSavedRecipes savedRecipe) {
        RecipeDatabase.databaseWriteExecutor.execute(() -> {
            userSavedRecipesDAO.delete(savedRecipe);
        });
    }
}
