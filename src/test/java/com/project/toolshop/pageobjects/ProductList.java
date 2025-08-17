package com.project.toolshop.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.project.domain.ProductSummary;
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

    public List<ProductSummary> getProductSummary() {
        return page.locator(".card").all()
                .stream()
                .map(
                        productCard -> {
                            String productName = trimmed(productCard.getByTestId("product-name").textContent());
                            String productPrice = productCard.getByTestId("product-price").textContent();
                            return new ProductSummary(productName, productPrice);
                        }).toList();

    }

    @Step("Selecting the product with name {productName}")
    public void selectTheProduct(String productName) {
        page.locator(".card", new Page.LocatorOptions().setHasText(productName)).click();
        ScreenshotManager.takeScreenshot(page, "product-selected-" + productName);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    private String trimmed(String value) {
        return value.strip().replaceAll("\u00A0","");
    }

    private double price(String s) {
        return s.isEmpty() ? 0.0 : Double.parseDouble(s.replace("$", ""));
    }

    public String getNoProductsMessage() {
        return page.getByTestId("search_completed").textContent();
    }
}
