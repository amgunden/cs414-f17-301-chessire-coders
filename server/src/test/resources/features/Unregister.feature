Feature: Unregistering a user from the system.

  A user should have the option to unregister from our game system as per the assignment description.
  Rules:
  - The user must have an account.
  - The user must be logged in.
  - The user's session is expired after un-registration.
  - The user's login row is deleted.

  @DBClean
  Scenario: Normal un-registration.
    Given the following accounts exist:
      | email        | nick name | password |
      | bob@test.com | bob       | bob123   |
    And they log in with correct credentials:
      | email        | password |
      | bob@test.com | bob123   |
    When they un-register their account
    Then they are logged out
    And they have been marked as un-registered
