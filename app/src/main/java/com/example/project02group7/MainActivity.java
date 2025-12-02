package com.example.project02group7;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.project02group7.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Find bottom nav bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Create instances of existing fragments
        Fragment home = new HomeFragment();
        Fragment recipes = new RecipeFragment();
        Fragment account = new AccountFragment();
        Fragment settings = new SettingsFragment();

        // set initial fragment
        setCurrentFragment(home);
        bottomNavigationView.setSelectedItemId(R.id.home);

        // handle bottom nav user selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                setCurrentFragment(home);
            } else if (id == R.id.recipe) {
                setCurrentFragment(recipes);
            } else if (id == R.id.profile) {
                setCurrentFragment(account);
            } else if (id == R.id.setting) {
                setCurrentFragment(settings);
            }
            return true;
        });
    }

    /**
     * Description: Method to help switch between fragments.
     * @param fragment a Fragment object
     */
    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragment, fragment)
                .commit();
    }

}