package com.example.project02group7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project02group7.database.RecipeRepository;
import com.example.project02group7.database.entities.User;
import com.example.project02group7.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private RecipeRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the repository
        repository = RecipeRepository.getRepository(getApplication());
        if(repository == null){
            toastMaker("Error initializing database");
            return;
        }

        // Login button
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                verifyUser();
            }
        });
    }

    /*
        Verify username + password using DB
        blank username -> toast
        unknown username -> toast
        wrong password -> toast
        all fine? Go to MainActivity with that UID
     */
    public void verifyUser(){
        final String usernameInput = binding.userNameLoginEditText.getText().toString().trim();
        final String passwordInput = binding.passwordLoginEditText.getText().toString();

        if(usernameInput.isEmpty()){
            toastMaker("Username cannot be blank");
            return;
        }

        if(passwordInput.isEmpty()){
            toastMaker("Password cannot be blank");
            return;
        }

        if(repository == null){
            toastMaker("Database not ready");
            return;
        }

        LiveData<User> userLiveData = repository.getUserByUsername(usernameInput);
        userLiveData.observe(this, user -> {
            if(user == null){
                //todo: make this not display when the user is found (just a couple seconds late to finding it because database isn't created)
                toastMaker("User not found");
                return;
            }

            if(!user.getPassword().equals(passwordInput)){
                toastMaker("Incorrect Password");
                return;
            }

            boolean isAdmin = user.isAdmin();

            Intent intent = LandingPageActivity.landingPageIntentFactory(
                    getApplicationContext(),
                    user.getUsername(),
                    isAdmin
            );
            startActivity(intent);
            updateSharedPreference(user.getId());
            finish();
        });
    }

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }

    // need to update shared preferences where they are being changed for persistence
    // Updated parameter from String to int
    private void updateSharedPreference(int userId) {
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        // need a way to access username
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), userId);
        sharedPrefEditor.apply();
    }
}