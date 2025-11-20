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
import androidx.lifecycle.LiveData;

import com.example.project02group7.database.RecipeRepository;
import com.example.project02group7.database.entities.User;
import com.example.project02group7.databinding.ActivityLandingPageBinding;

import org.w3c.dom.Text;

public class LandingPageActivity extends AppCompatActivity {
    private static final String LANDING_PAGE_ACTIVITY_USER_ID = "com.example.project02group7.LANDING_PAGE_ACTIVITY_USER_ID";
    private ActivityLandingPageBinding binding;
    private RecipeRepository repository;
    private String username;
    boolean isAdmin;
    private User user;
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

        updateSharedPreference(); // check where this is being called -> check where things are saved for persistence

        // show logged in username
        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");
        isAdmin = intent.getBooleanExtra("IS_ADMIN", false);

        TextView usernameTextView = binding.UsernameTextView;
        LiveData<User> usernameObserver = repository.getUserByUsername(username);
        usernameObserver.observe(this, user -> {
            if (user != null) {
                usernameTextView.setText(user.getUsername());
            }
        });

        // show is admin
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
                updateSharedPreference();
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                // close current activity
                finish();
            }
        });

        Button adminButton = binding.AdminButton;
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    static Intent landingPageIntentFactory(Context applicationContext, String username, boolean admin) {
        Intent intent = new Intent(applicationContext, LandingPageActivity.class);
        // may want to change "USER_ID" to symbolic constant
        intent.putExtra("USERNAME", username);
        intent.putExtra("IS_ADMIN", admin);
        return intent;
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.remove(getString(R.string.preference_file_key));
        sharedPrefEditor.apply();
    }
}
