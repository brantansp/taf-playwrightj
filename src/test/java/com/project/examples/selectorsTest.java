package com.project.examples;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@UsePlaywright(selectorsTest.CustomOptions.class)
public class selectorsTest {

    public static class CustomOptions implements OptionsFactory {
        @Override
        public Options getOptions() {
            return new Options().setBrowserName("chromium")
                    .setHeadless(false)
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

    @DisplayName("Test selectors")
    @Test
    public void testSelectors(Page page) {
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByAltText("Combination Pliers").click();
        PlaywrightAssertions.assertThat(page.getByText("ForgeFlex Tools")).isVisible();
        page.getByTitle("Practice Software Testing - Toolshop").click();
    }

    @DisplayName("Test selectors with different locator")
    @Test
    public void testSelectorsWithDifferentLocator(Page page) {
        page.navigate("https://practicesoftwaretesting.com/auth/login");
        page.getByLabel("Email address").fill("test@test.in");
        page.getByPlaceholder("Your password").first().fill("tester");

        //page.locator("//input[@value='Login']").click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
        //  Here the element is - <input _ngcontent-ng-c1371883730="" type="submit" data-test="login-submit" aria-label="Login" class="btnSubmit" value="Login">
        //  Playwright can find the specifying it as AriaRole Button although it is input element (Semantic way of user understanding the page elements)

        List<String> allProducts = page.locator(".btn-card")
                .filter(new Locator.FilterOptions().setHas(page.getByText("drill")))
                .getByText("drill")
                .allTextContents();
    }
}