package com.project.examples;

import net.datafaker.Faker;

public record User (String first_name,
                    String last_name,
                    UserAddress address,
                    String phone,
                    String dob,
                    String password,
                    String email) {

    /**
     * {
     "first_name": "John",
     "last_name": "Doe",
     "address": {
     "street": "Street 1",
     "city": "City",
     "state": "State",
     "country": "Country",
     "postal_code": "1234AA"
     },
     "phone": "0987654321",
     "dob": "1970-01-01",
     "password": "SuperSecure@123",
     "email": "john@doe.example"
     * }
     */

    public static User random_user() {
        Faker faker = new Faker();
        return new User(
                faker.name().firstName(),
                faker.name().lastName(),
                new UserAddress(
                        faker.address().streetName(),
                        faker.address().cityName(),
                        faker.address().state(),
                        "India",
                        faker.address().postcode()
                ),
                "0987654321",
                "1970-01-01",
                "Ape!123#",
                faker.internet().emailAddress()
        );
    }

}
