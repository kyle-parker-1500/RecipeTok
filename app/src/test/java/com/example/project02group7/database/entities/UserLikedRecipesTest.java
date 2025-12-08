package com.example.project02group7.database.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserLikedRecipesTest {
    private UserLikedRecipes userLikedRecipes;

    @Before
    public void setUp() {
        userLikedRecipes = new UserLikedRecipes(
                1,
                2,
                "Title",
                "Ingredients",
                "Instructions");
        userLikedRecipes.setId(10);
    }

    @Test
    public void testCreation() {
        assertEquals(10, userLikedRecipes.getId());
        assertEquals(1, userLikedRecipes.getUserId());
        assertEquals(2, userLikedRecipes.getRecipeId());
        assertEquals("Title", userLikedRecipes.getTitle());
        assertEquals("Ingredients", userLikedRecipes.getIngredients());
        assertEquals("Instructions", userLikedRecipes.getInstructions());
    }

    @Test
    public void testSetters() {
        userLikedRecipes.setId(3);
        assertEquals(3, userLikedRecipes.getId());

        userLikedRecipes.setUserId(4);
        assertEquals(4, userLikedRecipes.getUserId());

        userLikedRecipes.setRecipeId(5);
        assertEquals(5, userLikedRecipes.getRecipeId());

        userLikedRecipes.setTitle("New Title");
        assertEquals("New Title", userLikedRecipes.getTitle());

        userLikedRecipes.setIngredients("New Ingredients");
        assertEquals("New Ingredients", userLikedRecipes.getIngredients());

        userLikedRecipes.setInstructions("New Instructions");
        assertEquals("New Instructions", userLikedRecipes.getInstructions());
    }

    @Test
    public void testEquals() {
        UserLikedRecipes userLikedRecipes2 = new UserLikedRecipes(
                1,
                2,
                "Title",
                "Ingredients",
                "Instructions");
        userLikedRecipes2.setId(10);
        assertEquals(userLikedRecipes, userLikedRecipes2);
    }

    @Test
    public void testNotEquals() {
        UserLikedRecipes userLikedRecipes2 = new UserLikedRecipes(
                2,
                2,
                "Title",
                "Ingredients",
                "Instructions");
        userLikedRecipes2.setId(10);
        assertNotEquals(userLikedRecipes, userLikedRecipes2);
    }

    @After
    public void tearDown() {
        userLikedRecipes = null;
    }

}