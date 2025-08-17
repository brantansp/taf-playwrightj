package com.project.examples;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.RequestOptions;
import com.project.domain.User;

public class UserAPI {
    private final String BASE_URL = "https://api.practicesoftwaretesting.com/users/register";
    private final Page page;

    public UserAPI(Page page) {
        this.page = page;
    }
    public void registerUser(User user) {
        var response = page.request().post(
                BASE_URL, RequestOptions.create()
                        .setData(user)
                        .setIgnoreHTTPSErrors(true)
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Accept", "application/json")
        );

        if (response.status() != 201) {
            throw new RuntimeException("Failed to register user: " + response.statusText());
        }
    }

}
