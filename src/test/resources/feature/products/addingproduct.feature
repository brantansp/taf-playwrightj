Feature: Adding product to the cart

  Rule: Customer should be able to search products by its name
    Example: Jane searches for Adjustable wrench product
      Given Jane is on the products page
      When searching for the "Adjustable Wrench" product
      And the product "Adjustable Wrench" should be displayed

    Example: Jane Searches for Saw products
      Given Jane is on the products page
      When searching for the "Saw" product
      Then the these products should be displayed
        | Wood Saw     |
        | Circular Saw |

    Example: Jane Searches for Saw products and check its prices
      Given Jane is on the products page
      When searching for the "Saw" product
      Then the these products and its prices should be displayed
        | productName  | productPrice |
        | Wood Saw     | $12.18       |
        | Circular Saw | $80.19       |

    Example: Jane searches for product that does not exits
      Given Jane is on the products page
      When searching for the "no-product" product
      Then there should not be any product listed
      And the message "There are no products found." should be displayed

  Rule: Customer should be able to use filter to get only the required result
    Example: Jane searches for saw and filter hand saw
      Given Jane is on the products page
      When searching for the "saw" product
      And click on the filter for the "Hand Saw" filter
      Then the these products and its prices should be displayed
        | productName | productPrice |
        | Wood Saw    | $12.18       |

  Rule: Customer should be able to sort products by various category
    Scenario Outline: Jane sorts the products with available category
      Given Jane is on the products page
      When selecting the "<sort>" option
      Then the first "<product>" should be displayed
      Examples:
        | sort               | product             |
        | Name (A - Z)       | Adjustable Wrench   |
        | Name (Z - A)       | Wood Saw            |
        | Price (High - Low) | Drawer Tool Cabinet |
        | Price (Low - High) | Washers             |
