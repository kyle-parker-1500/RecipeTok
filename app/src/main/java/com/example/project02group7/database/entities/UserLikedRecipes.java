package com.example.project02group7.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.project02group7.database.RecipeDatabase;

import java.util.Objects;

@Entity(
        tableName = RecipeDatabase.USER_LIKED_RECIPES_TABLE,
        foreignKeys = {
                @ForeignKey(
                        entity = User.class, // parent class
                        parentColumns = "id", // pk in parent
                        childColumns = "userId", // fk in child (this class)
                        onDelete = ForeignKey.CASCADE // if parent col deleted, child cols deleted too
                ),
                @ForeignKey(
                        entity = Recipe.class,
                        parentColumns = "id",
                        childColumns = "recipeId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "userId"),
                @Index(value = "recipeId"),
                @Index(value = {"userId", "recipeId"}, unique = true) // prevents duplicates from being added to table
        }
)
public class UserLikedRecipes {
    @PrimaryKey(autoGenerate = true)
    private int id; // LikedId in ERD
    private int userId;
    private int recipeId;
    private String title;
    private String ingredients;
    private String instructions;

    public UserLikedRecipes(int userId, int recipeId, String title, String ingredients, String instructions) {
        this.userId = userId;
        this.recipeId = recipeId;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserLikedRecipes that = (UserLikedRecipes) o;
        return id == that.id && userId == that.userId && recipeId == that.recipeId && Objects.equals(title, that.title) && Objects.equals(ingredients, that.ingredients) && Objects.equals(instructions, that.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, recipeId, title, ingredients, instructions);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
