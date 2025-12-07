package com.example.project02group7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AccountFragment extends Fragment {

    private String username;
    private boolean isAdmin;

    public static AccountFragment newInstance(String username, boolean isAdmin){
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();

        args.putString("username", username);
        args.putBoolean("isAdmin", isAdmin);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            username = getArguments().getString("username");
            isAdmin = getArguments().getBoolean("isAdmin");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        TextView usernameTextView = view.findViewById(R.id.UsernameTextView);
        TextView isAdminTextView = view.findViewById(R.id.IsAdminTextView);
        Button logoutButton = view.findViewById(R.id.LogoutButton);
        Button adminButton = view.findViewById(R.id.AdminButton);

        usernameTextView.setText(username);
        isAdminTextView.setText(isAdmin ? "Yes" : "No");
        adminButton.setVisibility(isAdmin ? View.VISIBLE : View.INVISIBLE);

        logoutButton.setOnClickListener(v ->{
            clearUserFromSharedPreferences();
            Intent backToMain = MainActivity.mainActivityIntentFactory(
                    requireContext().getApplicationContext(), -1
            );
            backToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(backToMain);
            requireActivity().finish();
        });
        /*
        *   !requireActivity() gives us the Activity that is hosting the Fragment!
        *   Get the activity that hosting the fragment, casting it into a LandingPageActivity
        *   That lets us call openAdminFragment, because the fragment is only placed
        *   inside of LandingPageActivity
        */
        adminButton.setOnClickListener(v ->{
            if(getActivity() instanceof LandingPageActivity){
                LandingPageActivity activity = (LandingPageActivity) getActivity();
                activity.openAdminFragment();
            }
        });

        // inflate layout
        return view;
    }

    private void clearUserFromSharedPreferences(){
        SharedPreferences sharedPreferences = requireContext()
                .getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getString(R.string.preference_userId_key));
        editor.apply();
    }
}