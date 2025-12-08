package com.example.project02group7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.example.project02group7.database.entities.User;

import org.junit.Test;

public class UserTest {

    @Test
    public void constructorTest(){
        User user = new User("Thompson", "FunnyName");

        assertEquals("Thompson", user.getUsername());
        assertEquals("FunnyName", user.getPassword());

        // Should be false by default
        assertFalse(user.isAdmin());
    }

    @Test
    public void setterMethodsTest(){
        User user = new User("Batista", "LaPasion");

        user.setUsername("Dexter");
        user.setPassword("BayHarbor");
        user.setAdmin(true);
        user.setId(144);

        assertNotEquals("Doakes", user.getUsername());
        assertNotEquals("Surprise", user.getPassword());
        assertTrue(user.isAdmin());
        assertEquals(144, user.getId());
    }

    @Test
    public void differentUserTest(){
        User user1 = new User("Thanos", "fifty");
        User user2 = new User("Tony Stark", "Alive");

        assertNotEquals(user1, user2);
    }
}
