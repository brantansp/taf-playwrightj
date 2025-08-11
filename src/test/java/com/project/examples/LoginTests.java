package com.project.examples;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@UsePlaywright(ChromiumCustomOptions.class)
public class LoginTests {

    @Test
    @DisplayName("Validating the test user login for positive case")
    void testUserLogin(Page page) {
        // Register new user using API
        User user = User.random_user();
        UserAPI userAPI = new UserAPI(page);
        userAPI.registerUser(user);

        // Login to the user in UI
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLoginPage();
        loginPage.login(user);

        // Assert that the user is logged in
        assertThat(loginPage.title())
            .as("Check that the user is logged in")
            .isEqualTo("My account");
    }
}
