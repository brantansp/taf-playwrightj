package com.project.toolshop.utilities;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.AfterEach;

public interface Takesscreenshot {

    @AfterEach
    default void takeScreenshot(Page page){
        ScreenshotManager.takeScreenshot(page, "Taking screenshot at the end");
    };

}
