package com.project.cucumber.steps;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import java.util.Arrays;

import static com.project.toolshop.utilities.ScreenshotManager.takeScreenshot;

public class PlaywrightCucumberFixture {
    private static final ThreadLocal<Playwright> playwright =
            java.lang.ThreadLocal.withInitial(()->{
                Playwright playwright = Playwright.create();
                playwright.selectors().setTestIdAttribute("data-test");
                return playwright;
            });

    private static final ThreadLocal<Browser> browser = java.lang.ThreadLocal.withInitial(() ->
            playwright.get().chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(true)
                            .setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions"))
            ));


    private static final ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    @Before(order = 100)
    public void setupTest() {
        browserContext.set(browser.get().newContext());
        page.set(browserContext.get().newPage());
    }

    @After
    public void tearDownTest() {
        takeScreenshot(page.get(), "end-of-test-screenshot");
        if (browserContext.get() != null) {
            browserContext.get().close();
        }
    }

    @AfterAll
    public static void tearDown(){
        if (browser.get() != null) {
            browser.get().close();
            browser.remove();
        }
        if (playwright.get() != null) {
            playwright.get().close();
            playwright.remove();
        }
    }

    public static Page getPage() {
        return page.get();
    }

    public static BrowserContext getBrowserContext() {
        return browserContext.get();
    }
}
