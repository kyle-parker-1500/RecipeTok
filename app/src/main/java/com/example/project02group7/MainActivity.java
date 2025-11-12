package com.example.project02group7;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private MainActivity binding;
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
}