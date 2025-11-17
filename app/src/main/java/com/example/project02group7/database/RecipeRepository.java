package com.example.project02group7.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project02group7.MainActivity;
import com.example.project02group7.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RecipeRepository {
    private final UserDAO userDAO;
//    private ArrayList<String> allUsers;

    private static RecipeRepository repository;

    // constructor
    private RecipeRepository(Application application) {
        RecipeDatabase db = RecipeDatabase.getDatabase(application);
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

    public void insertUser(User... user) {
        RecipeDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

    // may not want this public
    public LiveData<List<User>> getListOfAllUsers() {
        return userDAO.getAllUsers();
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }
}
