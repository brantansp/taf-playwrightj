package com.project.cucumber.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SearchAndSortPage {

    Page page;

    public SearchAndSortPage(Page page) {
        this.page = page;
    }

    public void selectSortOption(String sortOption) {
        page.waitForResponse("**/products?**sort=**", () -> {
            page.getByLabel("Sort").selectOption(sortOption);
        });
    }
}
