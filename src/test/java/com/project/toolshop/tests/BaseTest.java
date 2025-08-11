package com.project.toolshop.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public abstract class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;
    protected static Page page;

    @BeforeAll
    static void setupPlaywright() {
        playwright = Playwright.create();
        playwright.selectors().setTestIdAttribute("data-test");
        browser = playwright.chromium()
                .launch(new BrowserType
                        .LaunchOptions().setHeadless(false)
                        .setArgs(List.of("--no-sandbox", "--disable-gpu", "--disable-extensions")));

    }

    @BeforeEach
    void setupTest() {
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }

    @AfterEach
    void tearDownTest() {
        if (page != null) {
            page.close();
        }
        if (browserContext != null) {
            browserContext.close();
        }
    }
}
