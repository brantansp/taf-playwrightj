package com.project.toolshop.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

public class ProductPage {
    private final Page page;

    public ProductPage(Page page) {
        this.page = page;
    }

    @Step("Click on the Add to Cart")
    public void clickOnAddToCart() {
        //page.getByRole(AriaRole.ALERT, new Page.GetByRoleOptions().setName(" Product added to shopping cart.")).waitFor();
        page.waitForResponse(
                response -> response.url().contains("/carts") &&
                        response.request().method().equals("POST"),
                () -> {
                    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).click();
                });
    }

    @Step("Increase the product quantity by {quantity}")
    public void increaseTheProductQuantity(int quantity) {
        for (int i = 1; i < quantity; i++) {
            page.locator("#btn-increase-quantity").click();
        }
    }
}
