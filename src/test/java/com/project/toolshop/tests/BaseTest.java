package com.project.toolshop.tests;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.project.toolshop.utilities.ScreenshotManager.takeScreenshot;

public abstract class BaseTest {
    protected static ThreadLocal<Playwright> playwright =
            ThreadLocal.withInitial(()->{
                Playwright playwright = Playwright.create();
                playwright.selectors().setTestIdAttribute("data-test");
                return playwright;
            });

    protected static ThreadLocal<Browser> browser = ThreadLocal.withInitial(() ->
            playwright.get().chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(true)
                            .setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions"))
            ));


    protected BrowserContext browserContext;
    protected Page page;

    @BeforeEach
    void setupTest() {
        browserContext = browser.get().newContext();
        page = browserContext.newPage();
    }

    @AfterEach
    void tearDownTest() {
        takeScreenshot(page, "end-of-test-screenshot");
        if (browserContext != null) {
            browserContext.close();
        }
    }


    @AfterAll
    static void tearDown(){
        if (browser.get() != null) {
            browser.get().close();
            browser.remove();
        }
        if (playwright.get() != null) {
            playwright.get().close();
            playwright.remove();
        }
    }
}
