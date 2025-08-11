package com.project.toolshop.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SearchComponents {
    private final Page page;

    public SearchComponents(Page page) {
        this.page = page;
    }

    public void searchBy(String keyword) {
        page.waitForResponse("**/products/search?q=" + keyword, () -> {
            page.getByPlaceholder("Search").fill(keyword);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
        });
    }
}
