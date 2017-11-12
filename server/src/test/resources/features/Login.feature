Feature: Login Management

  Users should be able to login to the game system.

  Rules:
  - The user has already registered for an account.
  - User must present the same credentials presented on account creation.
  - After 3 bad attempts to log in, the account is locked.

  @DBClean
  Scenario: A registered user that provides correct credentials is authenticated by the server.
    Given the following accounts exist:
      | email        | nick name | password |
      | bob@test.com | bob       | bob123   |
    When they log in with correct credentials:
      | email        | password |
      | bob@test.com | bob123   |
    Then they are authenticated
    And the client is sent a success response

  @DBClean
  Scenario: A registered user that provides incorrect credentials is not authenticated by the server.
    Given the following accounts exist:
      | email        | nick name | password |
      | bob@test.com | bob       | bob123   |
    When they log in with incorrect credentials:
      | email        | password    |
      | bob@test.com | badPassword |
    Then they are not authenticated
    And the client is sent an error response

  @DBClean
  Scenario: A registered user that provides incorrect credentials more than 3 times is locked out.
    Given the following accounts exist:
      | email        | nick name | password |
      | bob@test.com | bob       | bob123   |
    When they log in with incorrect credentials:
      | email        | password |
      | bob@test.com | bad      |
      | bob@test.com | bad      |
      | bob@test.com | bad      |
    Then their account is locked
    And the client is sent an error response

  @DBClean
  Scenario: An unregistered user cannot be authenticated by the server.
    Given a user has not created an account
    When they log in with any credentials:
      | email            | password |
      | not-bob@test.com | not-bob  |
    Then the client is sent an error response