package com.example.project02group7.viewHolders;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02group7.LandingPageActivity;
import com.example.project02group7.R;
import com.example.project02group7.database.RecipeRepository;
import com.example.project02group7.database.entities.User;

public class UserViewHolder extends RecyclerView.ViewHolder {
    private final TextView usernameTextView;
    private final TextView isAdminTextView;
    private final Button deleteUserButton;

    private final RecipeRepository repository;
    private User currentUser;

    public UserViewHolder(@NonNull View itemView, RecipeRepository repository){
        super(itemView);
        this.repository = repository;

        usernameTextView = itemView.findViewById(R.id.usernameRowTextView);
        isAdminTextView = itemView.findViewById(R.id.isAdminRowTextView);
        deleteUserButton = itemView.findViewById(R.id.deleteUserButton);

        deleteUserButton.setOnClickListener(v ->{
            if (currentUser == null){
                return;
            }

            new AlertDialog.Builder(itemView.getContext())
                    .setTitle("Delete user")
                    .setMessage("Are you sure you want to delete: " + currentUser.getUsername() + "?")
                    .setPositiveButton("Delete", ((dialog, which) -> {
                        repository.deleteUser(currentUser);
                        Toast.makeText(itemView.getContext(),
                                "Deleted " + currentUser.getUsername(), Toast.LENGTH_SHORT).show();
                    }))
                    .setNegativeButton("cancel", null)
                    .show();
        });
    }

    // inflates item_user.xml and returns a viewHolder
    public static UserViewHolder create(ViewGroup parent, RecipeRepository repository){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view, repository);
    }

    // Binds the User data to the row views
    public void bind(User user){
        currentUser = user;
        usernameTextView.setText(user.getUsername());
        isAdminTextView.setText(user.isAdmin() ? "Admin: Yes" : "Admin: No");
    }
}
