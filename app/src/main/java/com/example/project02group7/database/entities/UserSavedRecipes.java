package com.example.project02group7.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.project02group7.database.RecipeDatabase;

import java.util.Objects;

// 2 FKs
@Entity(
        tableName = RecipeDatabase.USER_SAVED_RECIPES_TABLE,
        foreignKeys = {
                @ForeignKey(
                        entity = User.class, // parent class
                        parentColumns = "id", // pk in parent
                        childColumns = "UserId", // fk in child (this class)
                        onDelete = ForeignKey.CASCADE // if parent column deleted child cols deleted too
                ),
                @ForeignKey(
                        entity = Recipe.class,
                        parentColumns = "id",
                        childColumns = "RecipeId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "UserId"),
                @Index(value = "RecipeId")
        }
)
public class UserSavedRecipes {
    // saved recipes will have their overall id (recipeId) and saved recipes unique id (savedId)
    // todo: determine if id should be refactored to SavedId -> if yes look at other entities
    @PrimaryKey(autoGenerate = true)
    private int id; // SavedId in ERD

    // @Index allows for fast lookup of ids
    private int UserId;
    private int RecipeId;
    private String instructions;
    private String ingredients;
    // todo: consider adding imageUrl / timestamp (for later)

    // todo: need to list instructions & ingredients somehow

    // todo: double check if other variables need to be initialized in constructor (think they're initialized elsewhere)
    public UserSavedRecipes() {
        // initialize only what may be null
        instructions = "";
        ingredients = "";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserSavedRecipes that = (UserSavedRecipes) o;
        return id == that.id && UserId == that.UserId && RecipeId == that.RecipeId && Objects.equals(instructions, that.instructions) && Objects.equals(ingredients, that.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, UserId, RecipeId, instructions, ingredients);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(int recipeId) {
        RecipeId = recipeId;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
