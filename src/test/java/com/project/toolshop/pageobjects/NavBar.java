package com.project.toolshop.pageobjects;

import com.microsoft.playwright.Page;
import com.project.toolshop.utilities.ScreenshotManager;
import io.qameta.allure.Step;

public class NavBar {
    private final Page page;

    public NavBar(Page page) {
        this.page = page;
    }

    @Step("Click on the cart icon")
    public void clickOnCart() {
        page.getByTestId("nav-cart").click();
    }

    @Step("Navigating to the home page")
    public void navigateToHomePage() {
        page.getByTestId("nav-home").click();
    }
}
