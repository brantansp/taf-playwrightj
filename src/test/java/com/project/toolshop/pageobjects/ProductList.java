package com.project.toolshop.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import java.util.List;

public class ProductList {
    private final Page page;

    public ProductList(Page page) {
        this.page = page;
    }

    public List<String> getMatchingProductNames() {
        return page.getByTestId("product-name").allInnerTexts();
    }

    public void selectTheProduct(String productName) {
        page.locator(".card", new Page.LocatorOptions().setHasText(productName)).click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }
}
