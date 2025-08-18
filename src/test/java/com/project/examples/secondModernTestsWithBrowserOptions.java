package com.project.examples;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Arrays;

@UsePlaywright(secondModernTestsWithBrowserOptions.CustomOptions.class)
public class secondModernTestsWithBrowserOptions {
    public static class CustomOptions implements OptionsFactory {
        @Override
        public Options getOptions() {
            return new Options().setBrowserName("chromium")
                    .setHeadless(true)
                    .setLaunchOptions(
                            new BrowserType.LaunchOptions()
                                    .setEnv(System.getenv())
                                    .setDownloadsPath(Paths.get("downloads"))
                                    //.setProxy("http://proxy.example.com:8080")
                                    //.setSlowMo(50)
                                    .setArgs(Arrays.asList(
                                            "--no-sandbox",
                                            "--disable-gpu",
                                            "--disable-extensions")
                                    )
                    );
        }
    }

    @Test
    void testSecondModernMethod(Page page) {
        page.navigate("https://google.com");
        String title = page.title();
        System.out.println("Page title: " + title);
        Assertions.assertEquals("Google", title);
    }
}
