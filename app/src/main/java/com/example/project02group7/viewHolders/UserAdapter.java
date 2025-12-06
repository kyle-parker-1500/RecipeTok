package com.example.project02group7.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02group7.database.RecipeRepository;
import com.example.project02group7.database.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<User> users = new ArrayList<>();
    private final RecipeRepository repository;

    public UserAdapter(RecipeRepository repository){
        this.repository = repository;
    }

    // Updates list of users from LiveData
    public void setUsers(List<User> users){
        this.users = users;
        notifyDataSetChanged(); // Tells RecyclerView to redraw everything
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return UserViewHolder.create(parent, repository);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount(){
        return users.size();
    }

}
