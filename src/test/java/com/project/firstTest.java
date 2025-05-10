package com.project;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class firstTest {

    @Test
    void test() {
        Playwright playwright = Playwright.create();

        Browser browser = playwright.chromium().launch();

        Page page = browser.newPage();

        page.navigate("https://google.com");

        String title = page.title();

        Assertions.assertTrue(title.contains("google"));

        browser.close();
        playwright.close();
    }
}
