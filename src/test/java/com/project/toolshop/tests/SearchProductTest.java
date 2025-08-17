package com.project.toolshop.tests;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.project.domain.CartLineItem;
import com.project.examples.ChromiumCustomOptions;
import com.project.toolshop.pageobjects.*;
import com.project.toolshop.utilities.Takesscreenshot;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;
import java.util.List;


@UsePlaywright(ChromiumCustomOptions.class)
@Feature("Searching the products")
public class SearchProductTest implements Takesscreenshot {
    SearchComponents searchComponents;
    ProductList productList;
    ProductPage productPage;
    NavBar navBar;
    CheckoutPage checkoutPage;

    @BeforeEach
    void setup(Page page, BrowserContext context) {
        page.navigate("https://practicesoftwaretesting.com/");
        page.waitForLoadState(LoadState.NETWORKIDLE);

        searchComponents = new SearchComponents(page);
        productList = new ProductList(page);
        productPage = new ProductPage(page);
        navBar = new NavBar(page);
        checkoutPage = new CheckoutPage(page);

        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSources(true)
                        .setSnapshots(true)
        );
    }

    @AfterEach
    void recordTrace(TestInfo test, BrowserContext context) {
        String testName = test.getDisplayName().replace(" ","-");
        context.tracing().stop(
                new Tracing.StopOptions()
                        .setPath(Paths.get("target/traces/traces-"+testName+".zip")));

        //npx playwright show-trace traces.zip
        // This command can be used to view the trace in a browser or visit https://trace.playwright.dev/
    }

    @Test
    @DisplayName("Search for the products by keywords")
    @Story("Search for the products by keywords")
    void searchProductsByKeyWordsWOPOM(Page page) {

        page.waitForResponse("**/products/search?q=tape", () -> {
            page.getByPlaceholder("Search").fill("tape");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
        });
        List<String> matchingNames = page.getByTestId("product-name").allInnerTexts();
        Assertions.assertThat(matchingNames).contains("Tape Measure 7.5m", "Measuring Tape", "Tape Measure 5m");
    }

    @Test
    @DisplayName("Search for the products by keywords using Page Object Model")
    @Story("Search for the products by keywords using Page Object Model")
    void searchProductsByKeyWordsPOM() {
        searchComponents.searchBy("tape");
        var matchingProducts = productList.getMatchingProductNames();
        Assertions.assertThat(matchingProducts).contains("Tape Measure 7.5m", "Measuring Tape", "Tape Measure 5m");
    }

    @Test
    @DisplayName("Adding pliers to cart and checkout with the order")
    @Story("Adding pliers to cart and checkout with the order")
    void addPliersToCartAndCheckout() {
        searchComponents.searchBy("pliers");
        productList.selectTheProduct("Combination Pliers");
        productPage.increaseTheProductQuantity(3);
        productPage.clickOnAddToCart();
        navBar.clickOnCart();
        List<CartLineItem> lineItem = checkoutPage.getCartLineItems();

        Assertions.assertThat(lineItem)
                .hasSize(1)
                .first()
                .satisfies(item -> {
                    Assertions.assertThat(item.productItemName()).startsWith("Combination Pliers");
                    Assertions.assertThat(item.productQuantity()).isEqualTo(3);
                    Assertions.assertThat(item.productPrice()).isEqualTo(14.15);
                    Assertions.assertThat(item.totalPrice()).isEqualTo(14.15 * 3);
                });
    }

    @Test
    @DisplayName("Adding two products to cart and checkout with the order")
    @Story("Adding two products to cart and checkout with the order")
    void addTwoProductsToCartAndCheckout(){
        navBar.navigateToHomePage();
        searchComponents.searchBy("pliers");
        productList.selectTheProduct("Combination Pliers");
        productPage.increaseTheProductQuantity(3);
        productPage.clickOnAddToCart();
        navBar.clickOnCart();

        navBar.navigateToHomePage();
        searchComponents.searchBy("sander");
        productList.selectTheProduct("Sheet Sander");
        productPage.increaseTheProductQuantity(2);
        productPage.clickOnAddToCart();
        navBar.clickOnCart();

        List<CartLineItem> lineItems = checkoutPage.getCartLineItems();
        Assertions.assertThat(lineItems)
                .hasSize(2);

        List<String> productNames = lineItems.stream().map(CartLineItem::productItemName).toList();
        Assertions.assertThat(productNames).contains("Combination Pliers","Sheet Sander");
    }
}
