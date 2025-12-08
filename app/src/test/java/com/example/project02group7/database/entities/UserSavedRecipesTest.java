package com.example.project02group7.database.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserSavedRecipesTest {
    private UserSavedRecipes recipe;

    @Before
    public void setUp() {
        recipe = new UserSavedRecipes(0, 20, "I am a title", "I am ingredient", "Instructions...");
    }

    @Test
    public void testRecipeCreation() {
        assertNotNull(recipe);
        assertEquals("I am a title", recipe.getTitle());
        assertNotEquals("Mix things until pasta is formed!", recipe.getInstructions());
        assertEquals(0, recipe.getId());
        assertNotEquals(12, recipe.getId());
    }

    @Test
    public void testRecipeSetters() {
        assertNotNull(recipe);
        recipe.setTitle("Psych pasta");
        assertEquals("Psych pasta", recipe.getTitle());
        assertNotEquals("Normal boring pasta", recipe.getTitle());
        recipe.setRecipeId(30);
        assertEquals(30, recipe.getRecipeId());
    }

    @After
    public void tearDown() {
        recipe = null;
    }
}