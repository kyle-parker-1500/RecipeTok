package com.example.project02group7;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project02group7.database.RecipeRepository;
import com.example.project02group7.database.entities.User;
import com.example.project02group7.databinding.ActivityLandingPageBinding;
import com.example.project02group7.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "GROUP07_RECIPE";
    private static final String MAIN_ACTIVITY_USER_ID =
            "com.example.project02group7.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY =
            "com.example.project02group7.SHARED_PREFERENCE_USERID_KEY";
    //todo: figure out where saved_instance_state_userid_key is pulling from -kyle
    // Kyle, it literally says "com.example.project02group7" before they key itself
    static final String SAVED_INSTANCE_STATE_USERID_KEY =
            "com.example.project02group7.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private ActivityMainBinding binding;
    private RecipeRepository repository;
    private int loggedInUserId = LOGGED_OUT;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get repository
        repository = RecipeRepository.getRepository(getApplication());

        if(repository == null){
            Toast.makeText(this, "Error initializing database", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sees if someone is already logged in
        loginUser(savedInstanceState);

        /*
        * If loginUser has a logged-in user, it will go to LandingPageActivity
        * Otherwise, it will run the code below
        */
        TextView isLoggedIn = binding.CurrentlyLoggedInTextView;
        isLoggedIn.setText("Not currently logged in");

        // login button
        Button loginButton = binding.MainActivityLoginButton;
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send to login page
                Intent intent = LoginActivity.loginIntentFactory(MainActivity.this);
                startActivity(intent);
            }
        });

        // create account button
        Button createAccountButton = binding.MainActivityCreateAccountButton;
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
        @Nullable means the parameter can be null
        Decides who is logged in:
        SharedPreferences
        savedInstanceState (rotation, or recreating app)
        Intent extra from LoginActivity

        If still LOGGED_OUT after all, send user to LoginActivity
     */
    private void loginUser(@Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
        );

        // 1 -> Try SharedPreferences
        loggedInUserId = sharedPreferences.getInt(
                getString(R.string.preference_userId_key),
                LOGGED_OUT
        );

        // 2 -> Try savedInstanceState (e.g. after rotation)
        if(loggedInUserId == LOGGED_OUT
                && savedInstanceState != null
                && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){

            loggedInUserId = savedInstanceState.getInt(
                    SAVED_INSTANCE_STATE_USERID_KEY,
                    LOGGED_OUT
            );
        }

        // 3 -> Try Intent extra from LoginActivity (first time launch)
        if (loggedInUserId == LOGGED_OUT){
            loggedInUserId = getIntent().getIntExtra(
                    "MAIN_ACTIVITY_USER_ID",
                    LOGGED_OUT
            );

            if(loggedInUserId != LOGGED_OUT){
                sharedPreferences.edit()
                        .putInt(getString(R.string.preference_userId_key), loggedInUserId)
                        .apply();
            }
        }

        // 4 -> Still logged out? return and show MainActivity
        if (loggedInUserId == LOGGED_OUT){
            return;
        }

        // 5 -> We have a valid userId = observe the user and go to Landing Page
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user ->{
            if(user == null){
                sharedPreferences.edit()
                        .remove(getString(R.string.preference_userId_key))
                        .apply();
                startActivity(LoginActivity.loginIntentFactory(this));
                finish();
                return;
            }

            this.user = user;
            invalidateOptionsMenu();

            boolean isAdmin = user.isAdmin();
            Intent intent = LandingPageActivity.landingPageIntentFactory(
                    MainActivity.this,
                    user.getUsername(),
                    isAdmin
            );
            startActivity(intent);
            finish();
        });
    }

    /*
        Saves the current loggedInUserId into SharedPreferences so it persists
        between app launches
    */
    private void updateSharedPreference(){
        SharedPreferences sharedPreferences = getApplication()
                .getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        editor.apply();
    }

    /*
        Used by LoginActivity to start MainActivity with the specific userId
     */
    static Intent mainActivityIntentFactory(Context applicationContext, int userId) {
        Intent intent = new Intent(applicationContext, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);

        // If user was not loaded, don't show username
        if(user == null){
            return false;
        }

        // Display username as menu title
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(menuItem ->{
            showLogoutDialog();
            return true;
        });
        return true;
    }

    private void showLogoutDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);

        alertBuilder.setMessage("Logout");
        alertBuilder.setPositiveButton("Logout", (DialogInterface dialog, int which) -> logout());
        alertBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        alertBuilder.create().show();
    }

    private void logout(){
        // Mark user as logged out
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();

        // Clear intent extra
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);

        // Go back to LoginActivity
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(SHARED_PREFERENCE_USERID_KEY, loggedInUserId);
        updateSharedPreference();
    }
}