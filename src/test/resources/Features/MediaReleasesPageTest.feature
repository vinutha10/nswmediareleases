Feature: Media releases page validation

  @ministerFilter @mediaReleases
  Scenario Outline: Expand and search by minister filter
    Given I navigate to media releases page
    When I see "Filter by Minister" dropdown is present and click expand
    Then I should verify all 14 ministers are present
    When I select "<categoryText>" filter and click search
    Then I should see "<categoryText>" displayed as tags on media release page
    Examples:
      | categoryText   |
      | Deputy Premier |

  @filterReset @mediaReleases
  Scenario Outline: Reset button
    Given I navigate to media releases page
    Then I should see tags on the page
    When I see "Filter by Minister" dropdown is present and click expand
    When I select "<categoryText>" filter and click search
    And I click on Reset Filter button
    Then I should see all filters are cleared
    And I should see the results are matching as I landed on media releases page
    Examples:
      | categoryText   |
      | Treasurer      |
