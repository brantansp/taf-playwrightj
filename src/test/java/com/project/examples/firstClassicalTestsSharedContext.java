package com.project.examples;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

public class firstClassicalTestsSharedContext {
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    @BeforeAll
    static void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
    }

    @BeforeEach
    void setUpTest() {
        page = context.newPage();
    }

    @AfterAll
    static void tearDown() {
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Test
    void testFirstClassicalMethod() {
        page.navigate("https://google.com");
        String title = page.title();
        System.out.println("Page title: " + title);
        Assertions.assertEquals("Google", title);
    }

    @Test
    void testSecondClassicalMethod() {
        page.navigate("https://facebook.com");
        String title = page.title();
        System.out.println("Page title: " + title);
        Assertions.assertEquals("Facebook – log in or sign up", title);
    }
}
