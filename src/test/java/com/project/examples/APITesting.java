package com.project.examples;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.RequestOptions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.stream.Stream;

@UsePlaywright(ChromiumCustomOptions.class)
public class APITesting {

    record Product(String productName, Double productPrice) {}

    private static APIRequestContext apiRequestContext;

    @BeforeAll
    public static void setupRequestContext(Playwright playwright){
        apiRequestContext = playwright.request().newContext(
          new APIRequest.NewContextOptions()
                  .setBaseURL("https://api.practicesoftwaretesting.com")
                  .setIgnoreHTTPSErrors(true)
                  //.setExtraHTTPHeaders(new HashMap<>(){{
                  //    put("Accept","application/json");
                  //}})
        );
    }

    @BeforeEach
    void beforeEach(Page page){
        page.navigate("https://practicesoftwaretesting.com");
    }

    @AfterEach
    void tearDown(){
        if(apiRequestContext != null){
            apiRequestContext.dispose();
        }
    }

    @DisplayName("Check the price of known product")
    @ParameterizedTest(name = "Checking the product {0}")
    @MethodSource("Product")
    void checkProductPrices(Product product, Page page){
        page.fill("[placeholder='Search']", product.productName);
        page.click("button:has-text('Search')");
        page.waitForLoadState(LoadState.NETWORKIDLE);

        //Need to assert that the product card has both the product name and product price correct
        Locator productCard = page.locator(".card").filter(
                new Locator.FilterOptions()
                        .setHasText(product.productName)
                        .setHasText(Double.toString(product.productPrice))
        );
        assertThat(productCard.isVisible()).isEqualTo(true);
    }

    static Stream<Product> Product() {
        APIResponse apiResponse = apiRequestContext.get("/products?page=2");
        Assertions.assertThat(apiResponse.status()).isEqualTo(200);

        JsonObject response = new Gson().fromJson(apiResponse.text(), JsonObject.class);
        JsonArray array = response.getAsJsonArray("data");

        return array.asList().stream().map(jsonElement -> {
            JsonObject productjson = jsonElement.getAsJsonObject();
            return new Product(
                    productjson.get("name").getAsString(),
                    productjson.get("price").getAsDouble()
            );
        });
    }

    @Test
    void user_registration_via_api(){
        User user = User.random_user();
        //String userJson = new Gson().toJson(user);

        var response = apiRequestContext.post("/users/register",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setData(user)
        );

        Gson gson = new Gson();
        User createdUser = gson.fromJson(response.text(), User.class);

        System.out.println(createdUser.first_name());

        assertThat(response.status()).isEqualTo(201);
    }

    @Test
    void user_registration_via_api_with_invalid_data(){
        User user = new User(
                null,
                "Doe",
                new UserAddress(
                        "Street 1",
                        "City",
                        "State",
                        "Country",
                        "1234AA"
                ),
                "0987654321",
                "1970-01-01",
                "SuperSecure@123",
                "test@test.com"
        );

        var response = apiRequestContext.post("/users/register",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setData(user)
        );

        assertThat(response.status()).isEqualTo(422);

        Gson gson = new Gson();
        JsonObject details= gson.fromJson(response.text(), JsonObject.class);
        assertThat(details.has("first_name")).isTrue();
        assertThat(details.get("first_name").getAsString()).isEqualTo("The first name field is required.");
    }
}