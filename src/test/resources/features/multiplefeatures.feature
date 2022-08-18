Feature: Login + add and edit a user
  Background: User should be logged in
    Given user is on login page
    When user enters valid username and password
    And presses the Login button
    Then user should log in and be redirected to homepage

    Scenario: Add a user with valid data
      Given user is on homepage
      When user clicks on "Add user+"
      * enters valid username
      * enters valid email
      * enters valid full name
      * enters valid password
      * selects 1 or more traits
      * selects gender
      * clicks on Submit button from Add User page
      Then a new user should be added

    Scenario Outline: Edit a specific user
      Given user is on homepage
      And clicks on <memberFullName>'s Edit button
      When enters valid username
      * enters valid email
      * enters valid full name
      * enters valid password
      * selects 1 or more traits
      * selects gender
      * clicks on Submit button from Edit User page

      Examples:
      | memberFullName |
      | Oliver125 |
      | Charlotte392 |

      Scenario: Delete specific users
        Given user is on homepage
        When clicks on Delete buttons of "Elijah535, Olivia352"
        Then the users should be deleted