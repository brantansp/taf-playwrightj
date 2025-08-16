package com.project.toolshop.utilities;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;

public class ScreenshotManager {
    public static void takeScreenshot(Page page, String screenShotName) {
        String screenshotPath = "target/screenshots/screenshot-" + System.currentTimeMillis() + ".png";
        var screenshot =page.screenshot(new Page.ScreenshotOptions()
                .setFullPage(true)
                .setPath(Paths.get(screenshotPath))
        );

        Allure.addAttachment(screenShotName, new ByteArrayInputStream(screenshot));
    }
}
