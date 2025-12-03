package com.example.project02group7.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project02group7.database.RecipeDatabase;

import java.util.Objects;

@Entity(tableName = RecipeDatabase.RECIPE_TABLE)
public class Recipe {
    // Api will have:
    // 1: Instructions
    // 2: Ingredients
    // (maybe) Images
    // Need (maybe if we have time):
    // Prices of ingredients

    @PrimaryKey(autoGenerate = true) // generates unique ids when something is added
    private int id;
    private String title;
    private String description;
    //todo: implement this later (don't forget to update erd) -> private String imageFile;
    private String instructions;
    // may want ingredients to be an List or ArrayList not String
    private String ingredients;

    public Recipe() {
        // don't want to be able to change these outside of the db
        title = "";
        description = "";
        instructions = "";
        ingredients = "";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id && Objects.equals(title, recipe.title) && Objects.equals(description, recipe.description) && Objects.equals(instructions, recipe.instructions) && Objects.equals(ingredients, recipe.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, instructions, ingredients);
    }

    // todo: determine if setters are needed here

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
