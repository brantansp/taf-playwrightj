package com.project.toolshop.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.project.toolshop.utilities.ScreenshotManager;
import io.qameta.allure.Step;

import java.util.List;

public class ProductList {
    private final Page page;

    public ProductList(Page page) {
        this.page = page;
    }

    @Step("Getting the matching product names")
    public List<String> getMatchingProductNames() {
        return page.getByTestId("product-name").allInnerTexts();
    }

    @Step("Selecting the product with name {productName}")
    public void selectTheProduct(String productName) {
        page.locator(".card", new Page.LocatorOptions().setHasText(productName)).click();
        ScreenshotManager.takeScreenshot(page, "product-selected-" + productName);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }
}
