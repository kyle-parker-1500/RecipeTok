package com.example.project02group7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

public class IntentFactoryTests {
    @Test
    public void mainActivityIntentFactoryTest(){
        Context context = ApplicationProvider.getApplicationContext();
        int userId = 42;

        Intent intent = MainActivity.mainActivityIntentFactory(context, userId);

        // Component must be point to MainActivity
        assertNotNull(intent.getComponent());
        assertEquals(MainActivity.class.getName(), intent.getComponent().getClassName());

        // Check that the userId extra was inserted correctly
        assertEquals(userId,
                intent.getIntExtra("com.example.project02group7.MAIN_ACTIVITY_USER_ID", -1)
        );
    }

    @Test
    public void landingPageActivityIntentFactoryTest(){
        Context context = ApplicationProvider.getApplicationContext();

        String username = "testUser";
        boolean isAdmin = true;

        // Create Intent to LandingPageActivity
        Intent intent = LandingPageActivity.landingPageIntentFactory(context, username, isAdmin);

        // Ensures that intent is pointing to LandingPageActivity correctly
        assertNotNull(intent.getComponent());
        assertEquals(LandingPageActivity.class.getName(), intent.getComponent().getClassName());

        // Verify Extras
        assertEquals(username, intent.getStringExtra("USERNAME"));
        assertEquals(isAdmin, intent.getBooleanExtra("IS_ADMIN", false));
    }

    @Test
    public void loginPageActivityIntentFactoryTest(){
        Context context = ApplicationProvider.getApplicationContext();

        Intent intent = LoginActivity.loginIntentFactory(context);

        // Checks target activity
        assertNotNull(intent.getComponent());
        assertEquals(LoginActivity.class.getName(), intent.getComponent().getClassName());
    }
}
