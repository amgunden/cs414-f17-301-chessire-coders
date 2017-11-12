Feature: Create Game

  The whole point of this project is play games right? So you have to be able to create a game to play one!

  Would you like to play a game...?

  Rules:
  - A user must be logged in to create a game.
  - Upon creating a game, everything be stored in the database.
  - A created game should contain 16 pieces, no more, no less.
  - Each game should have 8 pieces per player.
  - Every piece should be positioned correctly.

  @DBClean
  Scenario: Normal game creation
    Given the following accounts exist:
      | email        | nick name | password |
      | bob@test.com | bob       | bob123   |
    And they log in with correct credentials:
      | email        | password |
      | bob@test.com | bob123   |
    When they create a game
    Then the client is sent a success response
    And the game was created