package com.example.project02group7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.project02group7.database.RecipeRepository;
import com.example.project02group7.database.entities.User;
import com.example.project02group7.databinding.ActivityLandingPageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LandingPageActivity extends AppCompatActivity {
    private static final String LANDING_PAGE_ACTIVITY_USER_ID =
            "com.example.project02group7.LANDING_PAGE_ACTIVITY_USER_ID";
    private ActivityLandingPageBinding binding;
    private RecipeRepository repository;
    private String username;
    boolean isAdmin;
    private User user;

    // Fragments for bottom navigation
    private Fragment homeFragment;
    private Fragment recipeFragment;
    private Fragment accountFragment;
    private Fragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = RecipeRepository.getRepository(getApplication());

        if (repository == null) {
            Toast.makeText(this, "Error initializing database", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get login info from Intent
        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");
        isAdmin = intent.getBooleanExtra("IS_ADMIN", false);

        // Showing username (header)
        TextView usernameTextView = binding.UsernameTextView;
        LiveData<User> usernameObserver = repository.getUserByUsername(username);
        usernameObserver.observe(this, user -> {
            if (user != null) {
                usernameTextView.setText(user.getUsername());
            }
        });

        // Show admin status (header)
        TextView isAdminTextView = binding.IsAdminTextView;
        Button isAdminButton = binding.AdminButton;
        if (isAdmin) {
            isAdminTextView.setText("Yes");
            isAdminButton.setVisibility(View.VISIBLE);
        } else {
            isAdminTextView.setText("No");
            isAdminButton.setVisibility(View.INVISIBLE);
        }

        // implement functionality for logout button
        Button logoutButton = binding.LogoutButton;
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearUserFromSharedPreferences();

                Intent backToMain = MainActivity.mainActivityIntentFactory(
                        getApplicationContext(), user.getId()
                );
                backToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(backToMain);
                // close current activity
                finish();
            }
        });

        Button adminButton = binding.AdminButton;
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: open your admin screen / fragment? here
            }
        });

        // Bottom navigation + fragments
        BottomNavigationView bottomNavigationItemView = binding.bottomNavigationView;

        // Create fragment instances
        homeFragment = new HomeFragment();
        recipeFragment = new RecipeFragment();
        accountFragment = new AccountFragment();
        settingsFragment = new SettingsFragment();

        // Initial Fragment
        setCurrentFragment(homeFragment);
        bottomNavigationItemView.setSelectedItemId(R.id.home);

        // Handle bottom nav selection
        bottomNavigationItemView.setOnItemSelectedListener(item ->{
           int id = item.getItemId();

           if(id == R.id.home){
               setCurrentFragment(homeFragment);
           }
           else if(id == R.id.recipe){
               setCurrentFragment(recipeFragment);
           }
           else if(id == R.id.profile){
               setCurrentFragment(accountFragment);
           }
           else if(id == R.id.setting){
               setCurrentFragment(settingsFragment);
           }
           return true;
        });
    }

    /**
     *  Replace fragment in the LandingPageActivity container
     */
    private void setCurrentFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragment, fragment)
                .commit();
    }

    /**
     * Clears the user from SharedPreferences on logout
     */
    private void clearUserFromSharedPreferences(){
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getString(R.string.preference_userId_key));
        editor.apply();
    }

    static Intent landingPageIntentFactory(Context applicationContext, String username, boolean admin) {
        Intent intent = new Intent(applicationContext, LandingPageActivity.class);
        // may want to change "USER_ID" to symbolic constant
        intent.putExtra("USERNAME", username);
        intent.putExtra("IS_ADMIN", admin);
        return intent;
    }
}