[![Build Status](https://travis-ci.org/tking2096/cs414-f17-301-chessire-coders.svg?branch=development)](https://travis-ci.org/tking2096/cs414-f17-301-chessire-coders)
[![Stories in Ready](https://badge.waffle.io/tking2096/cs414-f17-301-chessire-coders.png?label=ready&title=Ready)](http://waffle.io/tking2096/cs414-f17-301-chessire-coders)

## CSU CS414 - Chessire Coders

This is the project page for the ChesshireCoders, the best group in CSU's Fall 2017 CS414 class!

[Developer Documentation](https://github.com/tking2096/cs414-f17-301-chessire-coders/wiki)

## The Project

For this class, we were tasked with creating a Chess-based board game entirely in Java, and entirely from scratch. We were given the following requirments that our game system must fulfill:

- There is a registration system in which new users can create an account in our game system.
- User's must be able to un-register their accounts at any time.
- An authentication system exists that allows registered users to log in given a correct username and password.
- An invitation system must exist where any player can invite another player to participate in a game with them.
- The reciever of the invitation has the choice to accept or reject the invitation.
- A player may forfeit/quit a joined game at any time.
- The game system must track each played game, including information about start and end times, as well as the outcome.
- Each player has a player profile that lists the games he or she has played.
- And of course, the game system must enforce the rules of the game.

Our team has also chosen to implement a basic AI feature so that any player may play against a computer.

## Jungle

Our team was tasked with creating an implementation of [Jungle](https://en.wikipedia.org/wiki/Jungle_(board_game)), a chess variant in which there are two players, each owning 8 pieces which have different power levels and special rules:

- Rat
- Cat
- Fox
- Dog
- Leopard
- Tiger
- Lion
- Elephant

Jungle also has a number of different tiles that a piece may occupy:

- A normal tile: No special rules, any piece may move onto a normal tile.
- A river tile: Only the Rat piece can occupy the river tiles. However, the Leopard and Tiget pieces may jump over the river tiles.
- A trap tile: Any piece that occupies a trap tile has its power level decrease to 1, meaning it can be captured by any piece.
- The den tiles: Each player owns a den tile. One player may win by moving one of his/her pieces onto the opponents den tile.
