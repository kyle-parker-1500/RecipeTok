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
    private String ingredients;
    private String instructions;
    // todo: consider adding imageUrl / timestamp (for later)

    // todo: find some way to list instructions & ingredients like SavedRecipes

    public UserLikedRecipes(int userId, int recipeId, String ingredients, String instructions) {
        this.userId = userId;
        this.recipeId = recipeId;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserLikedRecipes that = (UserLikedRecipes) o;
        return id == that.id && userId == that.userId && recipeId == that.recipeId && Objects.equals(ingredients, that.ingredients) && Objects.equals(instructions, that.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, recipeId, ingredients, instructions);
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
