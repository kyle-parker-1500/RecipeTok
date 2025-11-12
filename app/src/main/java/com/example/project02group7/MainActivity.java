package com.example.project02group7;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project02group7.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "GROUP07_RECIPE";

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project02group7.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.project02group7.SHARED_PREFERENCE_USERID_KEY";
    private ActivityMainBinding binding;
    private static final int LOGGED_OUT = -1;
    // private RECIPETOKRepository repository;
    // private RECIPETOKViewModel gymLogViewModel;
    public static final String TAG = "VB_RECIPETOK";
    private int loggedInUserId = -1;
    // private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /*
    private void loginUser(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        if(loggedInUserId == LOGGED_OUT
                && savedInstanceState != null
                && savedInstanceState.containsKey(SHARED_PREFERENCE_USERID_KEY)){
            loggedInUserId = savedInstanceState.getInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        }

        if(loggedInUserId == LOGGED_OUT){
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT){
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if(user != null) invalidateOptionsMenu();
        });
    }
    */
}