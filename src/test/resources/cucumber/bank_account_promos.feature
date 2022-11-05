Feature: Bank account promo, get 10% extra in your $2000+ deposits, up to $500

  Scenario: Successfully promo applied, cap not reached.
    Given Account with a balance of 0
    When Trying to deposit 3000
    Then Account balance should be 3300

  Scenario: Successfully promo applied, cap reached.
    Given Account with a balance of 1000
    When Trying to deposit 6000
    Then Account balance should be 7500

  Scenario: Promo not applied
    Given Account with a balance of 0
    When Trying to deposit 1500
    Then Account balance should be 1500
