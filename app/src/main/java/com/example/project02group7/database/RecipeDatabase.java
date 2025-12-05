package com.example.project02group7.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project02group7.MainActivity;
import com.example.project02group7.database.entities.Recipe;
import com.example.project02group7.database.entities.User;
import com.example.project02group7.database.entities.UserLikedRecipes;
import com.example.project02group7.database.entities.UserSavedRecipes;
import com.example.project02group7.database.typeConverters.LocalDateTypeConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {
        User.class,
        Recipe.class,
        UserLikedRecipes.class,
        UserSavedRecipes.class
        },
        version = 4, // updated from v1
        exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {
    public static final String USER_TABLE = "userTable";
    // todo: implement next three tables
    public static final String RECIPE_TABLE = "recipeTable";
    public static final String USER_SAVED_RECIPES_TABLE = "userSavedRecipesTable";
    public static final String USER_LIKED_RECIPES_TABLE = "userLikedRecipesTable";
    private static final String DATABASE_NAME = "RecipeDatabase";
    // names of tables go here:

    // volatile -> only lives in ram
    private static volatile RecipeDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4; // multithreading!!!

    // says: create service to supply threads
    // create @ startup and put in pool -> DB will have max of 4 threads
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // singleton: only one instance of UserDB exists at any one time
    static RecipeDatabase getDatabase(final Context context) {
        // INSTANCE not obj -> don't need .equals()
        if (INSTANCE == null) {

            // locks stuff into single thread
            // makes sure nothing is referencing our class (only want one instance of db)
            synchronized (RecipeDatabase.class) {
                // .fallbackToDestructiveMigration() -> basically helps us if version changes... I think (eg. oh.. I've made a mistake & app doesn't crash)
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    RecipeDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // .addCallback() does:
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO userDao = INSTANCE.userDAO();
                RecipeDAO recipeDao = INSTANCE.recipeDAO();
                UserLikedRecipesDAO likedDao = INSTANCE.userLikedRecipesDAO();
                UserSavedRecipesDAO savedDao = INSTANCE.userSavedRecipesDAO();

                // clear user db when recreating db
                userDao.deleteAll();

                // define default users
                User admin = new User("admin2", "admin2");
                admin.setAdmin(true);
                userDao.insert(admin);

                User testUser1 = new User("testuser1", "testuser1");
                userDao.insert(testUser1);

                // api call objects: OkHttp
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://10.0.2.2:8000/recipes").build();

                // add recipes to database using okhttp
                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) {
                        // define recipe columns
                        String title, ingredients, instructions;
                        // define recipe object
                        Recipe recipe;
                        List<Recipe> recipes = new ArrayList<>();

                        // using new thread to run on
                        try {
                            // getting initial json from api & parsing out recipes dict
                            String jsonResponse = response.body().string();
                            JSONObject jsonObject = new JSONObject(jsonResponse);
                            JSONArray recipesArray = jsonObject.getJSONArray("recipes");

                            for (int i = 0; i < recipesArray.length(); i++) {
                                JSONObject recipeJson = recipesArray.getJSONObject(i);

                                // parse data from object
                                title = recipeJson.getString("Title");
                                ingredients = recipeJson.getString("Ingredients");
                                instructions = recipeJson.getString("Instructions");

                                // create recipe object from current data
                                recipe = new Recipe(title, ingredients, instructions);

                                // add recipe to array
                                recipes.add(recipe);
                            }

                            // write to db on executor thread (switch back to it)
                            databaseWriteExecutor.execute(() -> {
                                recipeDao.insert(recipes.toArray(new Recipe[0]));
                            });
                        } catch(IOException | JSONException e) {
                            Log.e(MainActivity.TAG, "Error parsing JSON Recipes");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.e(MainActivity.TAG, "Failed to fetch recipes from recipes API", e);
                    }
                });
            });
        }
    };

    // daos -> for room to handle
    public abstract UserDAO userDAO();

    public abstract RecipeDAO recipeDAO();

    public abstract UserLikedRecipesDAO userLikedRecipesDAO();

    public abstract UserSavedRecipesDAO userSavedRecipesDAO();
}
