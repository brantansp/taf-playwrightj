package com.project.toolshop.pageobjects;

import com.microsoft.playwright.Page;

public class NavBar {
    private final Page page;

    public NavBar(Page page) {
        this.page = page;
    }

    public void clickOnCart() {
        page.getByTestId("nav-cart").click();
    }

    public void navigateToHomePage() {
        page.getByTestId("nav-home").click();
    }
}
