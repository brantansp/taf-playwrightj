package com.project;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {
    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void navigateToLoginPage() {
        page.navigate("https://practicesoftwaretesting.com/auth/login");
    }

    public void login(User users) {
        page.locator("#email").fill(users.email());
        page.locator("#password").fill(users.password());
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"))
            .click();
    }

    public String title() {
        return page.getByTestId("page-title")
            .textContent()
            .trim();
    }
}
