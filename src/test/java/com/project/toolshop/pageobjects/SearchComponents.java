package com.project.toolshop.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.project.toolshop.utilities.ScreenshotManager;
import io.qameta.allure.Step;

public class SearchComponents {
    private final Page page;

    public SearchComponents(Page page) {
        this.page = page;
    }

    @Step("Search for products with keyword {keyword}")
    public void searchBy(String keyword) {
        page.waitForResponse("**/products/search?q=**", () -> {
            page.getByPlaceholder("Search").fill(keyword);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
        });
        ScreenshotManager.takeScreenshot(page, "search-results-" + keyword);
    }

    public void clickOnFilter(String filterName) {
        page.waitForResponse("**/products?**by_category=**", () -> {
            page.getByLabel(filterName).click();
        });
    }
}
