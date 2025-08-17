package com.project.domain;

public record UserAddress(String street,
                          String city,
                          String state,
                          String country,
                          String postal_code) {
}
