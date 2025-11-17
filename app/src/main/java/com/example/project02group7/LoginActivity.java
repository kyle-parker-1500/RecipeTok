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

        // binding.createAccountButton.setOnClickListener (when needed?)
    }

    /*
        Verify username + password using DB
        blank username -> toast
        unknown username -> toast
        wrong password -> toast
        all fine? Go to MainActivity with that UID
     */
    public void verifyUser(){
        final String username = binding.userNameLoginEditText.getText().toString().trim();

        if(username.isEmpty()){
            toastMaker("Username cannot be blank");
            return;
        }

        if(repository == null){
            toastMaker("Database not ready");
            return;
        }

        // observer watching db username
        LiveData<User> userObserver = repository.getUserByUsername(username);
        userObserver.observe(this, user -> {

            // username not found
            if(user == null){
                toastMaker(String.format("%s is not a valid username", username));
                binding.userNameLoginEditText.setSelection(0);
                return;
            }
            // Username exists, password check
            String password = binding.passwordLoginEditText.getText().toString();

            // now checking for username & password to be correct
            if(username.equals(user.getUsername()) && password.equals(user.getPassword())){

                // go to LandingPageActivity for this user
                Intent intent = LandingPageActivity.landingPageIntentFactory(getApplicationContext(), user.getUsername(), user.isAdmin());
                startActivity(intent);
                // close activity
                finish();
            }
            else{
                toastMaker("Invalid username or password");
                binding.passwordLoginEditText.setSelection(0);
            }
        });
    }

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }
}
