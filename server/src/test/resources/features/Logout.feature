Feature: Log out
  After logging in, a user should be able to log out to exit their session.
  Rules:
  - The user must be registered.
  - The user must be logged in.
  - Upon logout, the user's session must be expired.

  @DBClean
  Scenario: Normal logout
    Given the following accounts exist:
      | email        | nick name | password |
      | bob@test.com | bob       | bob123   |
    And they log in with correct credentials:
      | email        | password |
      | bob@test.com | bob123   |
    When they log out
    Then their session is expired