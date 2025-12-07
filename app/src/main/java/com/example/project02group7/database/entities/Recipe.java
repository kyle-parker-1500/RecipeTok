package com.example.project02group7.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project02group7.database.RecipeDatabase;

import java.util.ArrayList;
import java.util.List;
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
    //todo: implement this later (don't forget to update erd) -> private String imageFile;
    private String ingredients;
    private String instructions;
    // may want ingredients to be an List or ArrayList not String

    public Recipe(String title, String ingredients, String instructions) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id && Objects.equals(title, recipe.title) && Objects.equals(ingredients, recipe.ingredients) && Objects.equals(instructions, recipe.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, ingredients, instructions);
    }

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