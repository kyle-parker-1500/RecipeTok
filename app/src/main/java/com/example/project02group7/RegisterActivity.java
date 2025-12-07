package com.example.project02group7;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.project02group7.database.RecipeRepository;
import com.example.project02group7.database.entities.User;
import com.example.project02group7.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private RecipeRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = RecipeRepository.getRepository(getApplication());
        if(repository == null){
            Toast.makeText(this, "Error initializing database", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reads what the user typed
        EditText usernameEditText = binding.editTextUsername;
        EditText passwordEditText = binding.editTextPassword;
        EditText confirmPasswordEditText = binding.editTextConfirmPassword;
        CheckBox adminCheckBox = binding.checkBoxIsAdmin;
        Button createAccountButton = binding.buttonCreateAccount;
        Button cancelButton = binding.buttonCancel;

        // Create account
        createAccountButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
            boolean isAdmin = adminCheckBox.isChecked();

            // Validates user input
            if(username.isEmpty()){
                usernameEditText.setError("Username required");
                return;
            }
            if(password.isEmpty()){
                passwordEditText.setError("Password required");
                return;
            }
            if(!password.equals(confirmPassword)){
                confirmPasswordEditText.setError("Passwords do not match");
                return;
            }

            // Checks if user is already in DB
            LiveData<User> existingUser = repository.getUserByUsername(username);
            existingUser.observe(this, userInDb ->{

                if(userInDb != null){
                    // User with that username is already in the DB
                    Toast.makeText(this, "Username already taken", Toast.LENGTH_SHORT).show();
                }
                else{
                    // Creates a new user
                    User newUser = new User(username, password);
                    newUser.setAdmin(isAdmin);

                    // Insert user into our DB
                    repository.insertUser(newUser);

                    // Successfully created the account and sends user to LoginActivity
                    Toast.makeText(this, "Account created! Please log in", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        });

        cancelButton.setOnClickListener(v -> {
            // If user cancels account creation, goes back to MainActivity
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}