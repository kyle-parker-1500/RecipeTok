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

        repository = RecipeRepository.getRepository(getApplication());
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view){
               verifyUser();
           }
        });
    }

    public void verifyUser(){
        String username = binding.userNameLoginEditText.getText().toString();

        if(username.isEmpty()){
            toastMaker("Username cannot be blank");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUsername(username);
        userObserver.observe(this, user -> {

            if(user == null){
                toastMaker(String.format("%s is not a valid username", username));
                binding.userNameLoginEditText.setSelection(0);
                return;
            }

            String password = binding.passwordLoginEditText.getText().toString();
            if(password.equals(user.getPassword())){

                SharedPreferences sharedPreferences = getApplication()
                        .getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                sharedPreferences.edit()
                                .putInt(getString(R.string.preference_userId_key), user.getId())
                                        .apply();

                startActivity(MainActivity.mainActivityIntentFactory(
                        getApplicationContext(),
                        user.getId()));
            }
            else{
                toastMaker("Invalid password");
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
