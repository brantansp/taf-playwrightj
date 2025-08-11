package com.project.examples;

public class MockResponse {
    public static final String MOCK_RESPONSE_SINGLE_PRODUCT = """
            {
                "current_page": 1,
                "data": [
                    {
                        "id": "01JWMKFV4QASVG8W3QM475NKXN",
                        "name": "Super Pliers",
                        "description": "Ut cursus dui non ante convallis, facilisis auctor leo luctus. Maecenas a rhoncus metus. Sed in efficitur dolor, vulputate accumsan odio. Sed ex quam, dictum in fringilla at, vestibulum eu sem. Quisque ante orci, vulputate non porttitor eu, aliquet et nunc. Nunc a rhoncus dui. Nunc ac est non eros scelerisque maximus at a eros. Phasellus sed egestas diam, at tempus erat. Morbi sit amet congue tellus, at accumsan magna. Etiam non ornare nisl, sed luctus nisi. Pellentesque ut odio ut sapien aliquet eleifend.",
                        "price": 9.17,
                        "is_location_offer": false,
                        "is_rental": false,
                        "in_stock": true,
                        "product_image": {
                            "id": "01JWMKFV3CPSJC3P97TJBE8CGE",
                            "by_name": "Yasin Hasan",
                            "by_url": "https://unsplash.com/@yasin",
                            "source_name": "Unsplash",
                            "source_url": "https://unsplash.com/photos/dwlxTSpfKXg",
                            "file_name": "pliers05.avif",
                            "title": "Slip joint pliers"
                        }
                    }
                ],
                "from": 1,
                "last_page": 1,
                "per_page": 9,
                "to": 1,
                "total": 1
            }
            """;
}
