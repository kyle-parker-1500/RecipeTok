package com.example.project02group7;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AdminFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        Button viewUsersButton = view.findViewById(R.id.viewUsersButton);
        Button backToAccountButton = view.findViewById(R.id.goBackButton);


        viewUsersButton.setOnClickListener(v ->{
            LandingPageActivity activity = (LandingPageActivity) requireActivity();
            activity.openUserFragment();
        });

        backToAccountButton.setOnClickListener(v ->{
            LandingPageActivity activity = (LandingPageActivity) requireActivity();
            activity.openAccountFragment();
        });

        return view;
    }
}