package com.project.cucumber.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.project.cucumber.pages.SearchAndSortPage;
import com.project.domain.ProductSummary;
import com.project.toolshop.pageobjects.*;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;

public class productSteps {

    SearchComponents searchComponents;
    ProductList productList;
    ProductPage productPage;
    NavBar navBar;
    CheckoutPage checkoutPage;
    SearchAndSortPage searchAndSortPage;
    Page page;

    @Before
    public void setup() {
        page = PlaywrightCucumberFixture.getPage();
        navBar = new NavBar(page);
        searchComponents = new SearchComponents(page);
        productList = new ProductList(page);
        productPage = new ProductPage(page);
        checkoutPage = new CheckoutPage(page);
        searchAndSortPage = new SearchAndSortPage(page);
    }

    @Given("Jane is on the products page")
    public void jane_is_on_the_products_page() {
        page.navigate("https://practicesoftwaretesting.com/");
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    @When("searching for the {string} product")
    public void searchingForTheProduct(String productName) {
        searchComponents.searchBy(productName);

    }

    @And("the product {string} should be displayed")
    public void theProductShouldBeDisplayed(String productName) {
        var matchingProducts = productList.getMatchingProductNames();
        Assertions.assertThat(matchingProducts).contains(productName);
    }

    @Then("the these products should be displayed")
    public void theTheseProductsShouldBeDisplayed(List<String> productNames) {
        var matchingProducts = productList.getMatchingProductNames();
        Assertions.assertThat(matchingProducts).containsAll(productNames);
    }

    @DataTableType
    public ProductSummary productSummaryEntry(Map<String, String> productData) {
        return new ProductSummary(
                productData.get("productName"),
                productData.get("productPrice")
        );
    }

    @Then("the these products and its prices should be displayed")
    public void theTheseProductsAndItsPricesShouldBeDisplayed(List<ProductSummary> expectedProductSummary)/*(DataTable productPrices)*/ {
        var actualProductSummary = productList.getProductSummary();

        /* DataTable can be used replaced with DataTableCOnverter
        List<Map<String, String>> expectedProducts = productPrices.asMaps(String.class, String.class);
        List<ProductSummary> expectedProductSummary = expectedProducts
                .stream()
                .map(product -> new ProductSummary(
                        product.get("productName"),
                        product.get("productPrice")
                )).toList();*/

        Assertions.assertThat(actualProductSummary).containsExactlyInAnyOrderElementsOf(expectedProductSummary);
    }

    @Then("there should not be any product listed")
    public void thereShouldNotBeAnyProductListed() {
        var matchingProducts = productList.getMatchingProductNames();
        Assertions.assertThat(matchingProducts).isEmpty();
    }

    @And("the message {string} should be displayed")
    public void theMessageShouldBeDisplayed(String searchCompletedMessage) {
        var message = productList.getNoProductsMessage();
        Assertions.assertThat(message).isEqualTo(searchCompletedMessage);
    }

    @And("click on the filter for the {string} filter")
    public void clickOnTheFilterForTheFilter(String filterName) {
        searchComponents.clickOnFilter(filterName);
    }

    @When("selecting the {string} option")
    public void selectingTheOption(String sortOption) {
        searchAndSortPage.selectSortOption(sortOption);
    }

    @Then("the first {string} should be displayed")
    public void theFirstShouldBeDisplayed(String firstProductName) {
        var matchingProducts = productList.getMatchingProductNames();
        Assertions.assertThat(matchingProducts).first().isEqualTo(firstProductName);
    }
}
