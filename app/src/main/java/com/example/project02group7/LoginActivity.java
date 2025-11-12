package com.example.project02group7;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project02group7.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*
        // repository = DATABASEREPO.getRepository(getApplication());
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view){
               verifyUser();
           }
        });
        */
    }


    public void verifyUser(){
        /*
        String username = binding.BUTTON_NAME.getText().toString();

        if(username.isEmpty()){
            toastMaker("Username cannot be blank");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            String password = binding.NAME.getText().toString();
            if(password.equals(user.getPassword())){
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
            }
            else{
                toastMaker("Invalid password");
                binding.Name.setSelection(0);
            }
        });
        */
        return;
    }

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }
}
