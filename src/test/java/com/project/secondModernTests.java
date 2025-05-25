package com.project;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@UsePlaywright
public class secondModernTests {

    @Test
    void testSecondModernMethod(Page page) {
        page.navigate("https://google.com");
        String title = page.title();
        System.out.println("Page title: " + title);
        Assertions.assertEquals("Google", title);
    }
}
