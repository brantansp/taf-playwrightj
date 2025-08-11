package com.project.toolshop.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.project.examples.MockResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AddToCartTest extends BaseTest{

    @Test
    void testAddToCart() {
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByPlaceholder("Search").fill("Pliers");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
        assertThat(page.locator(".card")).hasCount(4);

        List<String> products = page.locator(".card").allInnerTexts();
        Assertions.assertThat(products).allMatch(name -> name.contains("Pliers"));

        Locator productNames = page.locator(".card")
                .filter(new Locator.FilterOptions().setHasText("Out of stock"))
                        .getByTestId("product-name");

        assertThat(productNames).hasCount(1);
        assertThat(productNames).hasText("Long Nose Pliers");
    }

    @Test
    void uploadFilesTest() throws URISyntaxException {
        page.navigate("https://practicesoftwaretesting.com/contact");

        var fileUpload = page.getByLabel("Attachment");

        Path fileToUpload = Paths.get(ClassLoader.getSystemResource("data/test-file.txt").toURI());

        page.setInputFiles("#attachment",fileToUpload);

        Assertions.assertThat(fileUpload.inputValue()).endsWith("test-file.txt");
    }

    @DisplayName("Filling the contact us form")
    @ParameterizedTest
    @ValueSource(strings = {"First name","Last name","Email","Message"})
    void testContactUsForm(String values) throws InterruptedException {
        page.navigate("https://practicesoftwaretesting.com/contact");
        page.waitForLoadState(LoadState.NETWORKIDLE);

        var firstName = page.getByLabel("First name");
        var lastName = page.getByLabel("Last name");
        var email = page.getByPlaceholder("Your email *");
        var message = page.locator("#message");
        var subject = page.getByLabel("Subject");
        var submitButton = page.getByTestId("contact-submit");

        // Filling the values

        firstName.fill("John");
        lastName.fill("Doe");
        email.fill("john.doe@test.com");
        message.fill("This is a test message. This is a test message. This is a test message. This is a test message. This is a test message.");
        subject.selectOption("Warranty");

        page.getByLabel(values).clear();

        submitButton.click();

        var errorMessages = page.getByRole(AriaRole.ALERT).getByText(values+" is required");
        assertThat(errorMessages).isVisible();

        //assertThat(email).not().isDisabled();
    }

    @DisplayName("AssertJ assertions")
    @Test
    void assertJAssertionsTest(){
        page.navigate("https://practicesoftwaretesting.com/");
        page.waitForCondition(()-> page.getByTestId("product-price").count() > 0);

        List <Double> productPrices = page.getByTestId("product-price")
                .allInnerTexts()
                .stream()
                .map(price -> Double.parseDouble(price.replace("$","")))
                .toList();

        Assertions.assertThat(productPrices)
                .isNotEmpty()
                .allMatch(price -> price > 0)
                .doesNotContain(0.0)
                .allMatch(price -> price < 1000.0)
                .allSatisfy(price -> {
                    Assertions.assertThat(price).isGreaterThan(0.0);
                    Assertions.assertThat(price).isLessThan(1000.0);
                });
    }

    @Test
    @DisplayName("Sorting products by name in ascending order test")
    void sortingByNameTest() throws InterruptedException {
        page.navigate("https://practicesoftwaretesting.com/");
        page.waitForCondition(()-> page.getByTestId("product-price").count() > 0);

        page.getByLabel("Sort").selectOption("Name (A - Z)");

        //Thread.sleep(2000); // Wait for the sorting to complete
        page.waitForLoadState(LoadState.NETWORKIDLE);

        List <String> allProductName = page.getByTestId("product-name").allInnerTexts();

        allProductName
                .stream()
                .map(String::toLowerCase)
                .reduce((first, second) -> {
                    Assertions.assertThat(first).isLessThanOrEqualTo(second);
                    return second;
                });

        Assertions.assertThat(allProductName)
                .isSortedAccordingTo(String::compareToIgnoreCase);

        Assertions.assertThat(allProductName)
                .isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER);

        Assertions.assertThat(allProductName)
                .isSortedAccordingTo(Comparator.naturalOrder());
    }

    @Test
    @DisplayName("Sorting products by name in descending test")
    void sortingByNameDescendingTest() throws InterruptedException {
        page.navigate("https://practicesoftwaretesting.com/");
        page.waitForCondition(() -> page.getByTestId("product-price").count() > 0);

        page.getByLabel("Sort").selectOption("Name (Z - A)");

        //Thread.sleep(2000); // Wait for the sorting to complete
        page.waitForLoadState(LoadState.NETWORKIDLE);

        List<String> allProductName = page.getByTestId("product-name").allInnerTexts();

        Assertions.assertThat(allProductName)
                .isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    @DisplayName("Waits in playwright are needed when reading texts of an element")
    void waitsTest(){
        page.navigate("https://practicesoftwaretesting.com/");
        page.waitForSelector(".card-img-top");

        List <String> allTexts = page.locator(".card-img-top")
                .all()
                .stream()
                .map(image -> image.getAttribute("alt"))
                .toList();

        Assertions.assertThat(allTexts).contains("Pliers");

        page.getByRole(AriaRole.MENUITEM, new Page.GetByRoleOptions().setName("Categories")).click();
        //page.getByRole(AriaRole.MENUBAR).getByText("Categories").click();

        page.getByRole(AriaRole.MENUBAR).getByText("Power Tools").click();

        //page.waitForSelector(".card");
        page.waitForSelector(".card", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));

        var filteredProducts = page.getByTestId("product-name").allInnerTexts();

        Assertions.assertThat(filteredProducts).contains("Sheet Sander");
    }

    @Test
    @DisplayName("Explicit waits are NOT needed when using Playwright actions")
    void explicitWaitsTest() {
        page.navigate("https://practicesoftwaretesting.com/");

        page.getByLabel("Screwdriver").click();

        Assertions.assertThat(page.getByLabel("Screwdriver").isChecked());

        page.getByText("Phillips Screwdriver").click();

        page.locator("#btn-add-to-cart").click();

        assertThat(page.getByRole(AriaRole.ALERT)).isVisible();
        assertThat(page.getByRole(AriaRole.ALERT)).hasText("Product added to shopping cart.");

        page.waitForCondition(() -> page.getByRole(AriaRole.ALERT).isHidden());
    }

    @Test
    @DisplayName("Waiting for network conditions")
    void waitForNetworkConditionTest(){
        page.navigate("https://practicesoftwaretesting.com/");

        page.waitForSelector("[data-test=product-price]");

        page.waitForResponse("**/products?sort**", ()->{
            page.getByLabel("Sort").selectOption("Price (High - Low)");
        });

        List <Double> prices = page.getByTestId("product-price")
                .allInnerTexts()
                .stream()
                .map(price -> Double.parseDouble(price.replace("$","")))
                .toList();

        Assertions.assertThat(prices).isSortedAccordingTo(Comparator.reverseOrder());
        System.out.println("Prices: " + prices);
    }

    @Test
    @DisplayName("Mocking the api response of the AUT")
    void mockingAPIResponse(){
        page.route("**/products/search?q=Pliers", route -> {
            route.fulfill(new Route.FulfillOptions()
                    .setBody(MockResponse.MOCK_RESPONSE_SINGLE_PRODUCT)
                    .setStatus(200)
            );
        });
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByPlaceholder("Search").fill("Pliers");
        page.getByTestId("search-submit").click();


        assertThat(page.getByTestId("product-name")).hasCount(1);
        assertThat(page.getByTestId("product-name")).hasText("Super Pliers");
    }
}