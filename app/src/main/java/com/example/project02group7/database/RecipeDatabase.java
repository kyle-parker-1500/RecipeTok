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
import com.example.project02group7.database.entities.User;
import com.example.project02group7.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {
    public static final String USER_TABLE = "userTable";
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
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();

                User admin = new User("admin2", "admin2");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
            });
        }
    };
    // daos -> for room to handle
    public abstract UserDAO userDAO();
}
