package com.example.project02group7;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project02group7.database.RecipeRepository;
import com.example.project02group7.viewHolders.UserAdapter;

public class UserFragment extends Fragment {

    private RecipeRepository repository;
    private UserAdapter adapter;
    private TextView emptyTextView;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout with RecyclerView
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        // Find Views
        RecyclerView recyclerView = view.findViewById(R.id.usersRecyclerView);
        emptyTextView = view.findViewById(R.id.emptyUsersTextView);

        // Get repository instance
        repository = RecipeRepository.getRepository(requireActivity().getApplication());
        assert repository != null; // Make sure it's not null (thx Kyle)

        // Create adapter, pass repository so ViewHolder can delete it directly
        adapter = new UserAdapter(repository);

        // Set up Recycler View
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        // Observe all users in db
        repository.getListOfAllUsers()
                .observe(getViewLifecycleOwner(), users -> {
                    adapter.setUsers(users);

                    emptyTextView.setVisibility(
                            (users == null || users.isEmpty()) ? View.VISIBLE : View.INVISIBLE
                    );
                });

        return view;
    }
}