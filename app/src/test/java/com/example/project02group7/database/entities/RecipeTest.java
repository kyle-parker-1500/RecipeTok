package com.example.project02group7.database.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RecipeTest {
    private Recipe recipe;

    @Before
    public void setUp() {
        recipe = new Recipe("Pasta water", "Noodles and such", "Mix things until it works");
    }

    @Test
    public void testRecipeCreation() {
        assertNotNull(recipe);
        assertEquals("Pasta water", recipe.getTitle());
        assertNotEquals("Mix things until pasta is formed!", recipe.getInstructions());
        assertEquals("Noodles and such", recipe.getIngredients());
    }

    @Test
    public void testRecipeSetters() {
        recipe.setTitle("Psych pasta");
        assertEquals("Psych pasta", recipe.getTitle());
        assertNotEquals("Normal boring pasta", recipe.getTitle());
        recipe.setIngredients("Ravioli");
        assertEquals("Ravioli", recipe.getIngredients());
        recipe.setInstructions("Just do stuff, it'll work");
        assertEquals("Just do stuff, it'll work", recipe.getInstructions());
    }

    @After
    public void tearDown() {
        recipe = null;
    }
}
