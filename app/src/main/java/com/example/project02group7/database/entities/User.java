package com.example.project02group7.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project02group7.database.RecipeDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(tableName = RecipeDatabase.USER_TABLE)
public class User {
    @PrimaryKey
    private int id;
    private String username;
    private String password;
    private List<Integer> postIds;
    private boolean isAdmin;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.postIds = new ArrayList<>();

        // don't want every user to be admin
        isAdmin = false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isAdmin == user.isAdmin && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(postIds, user.postIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, postIds, isAdmin);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getPostIds() {
        return postIds;
    }

    public void setPostIds(List<Integer> postIds) {
        this.postIds = postIds;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
