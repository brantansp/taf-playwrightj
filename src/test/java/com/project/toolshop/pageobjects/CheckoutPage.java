package com.project.toolshop.pageobjects;

import com.microsoft.playwright.Page;
import com.project.domain.CartLineItem;
import com.project.toolshop.utilities.ScreenshotManager;
import io.qameta.allure.Step;

import java.util.List;

public class CheckoutPage {
    private final Page page;

    public CheckoutPage(Page page) {
        this.page = page;
    }

    @Step("Navigate to checkout page and getting the cart line items")
    public List<CartLineItem> getCartLineItems() {
        page.locator("app-cart tbody tr").first().waitFor();
        ScreenshotManager.takeScreenshot(page, "checkout-page-cart-line-items");
        return page.locator("app-cart tbody tr")
                .all()
                .stream()
                .map(row -> {
                    String productItemName = trimmed(row.getByTestId("product-title").innerText());
                    int productQuantity = Integer.parseInt(row.getByTestId("product-quantity").inputValue());
                    double productPrice = price(row.getByTestId("product-price").innerText());
                    double productTotalPrice = price(row.getByTestId("line-price").innerText());
                    return new CartLineItem(productItemName, productQuantity, productPrice, productTotalPrice);
                }).toList();
    }

    private String trimmed(String value) {
        return value.strip().replaceAll("\u00A0","");
    }

    private double price(String s) {
        return s.isEmpty() ? 0.0 : Double.parseDouble(s.replace("$", ""));
    }
}