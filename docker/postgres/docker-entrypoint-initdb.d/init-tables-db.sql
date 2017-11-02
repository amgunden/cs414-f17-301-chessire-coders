CREATE TYPE GAMESTATUSTYPE AS ENUM ('in progress', 'completed', 'abandoned');

CREATE TYPE PIECETYPE AS ENUM ('dog', 'cat', 'rat', 'elephant', 'wolf', 'leopard', 'tiger', 'lion');

CREATE TYPE PLAYERCOLORTYPE AS ENUM ('red', 'black');

CREATE TYPE PLAYEROUTCOMETYPE AS ENUM ('win', 'loss', 'draw');


CREATE TABLE "User"
(
  "UserID"    BIGSERIAL PRIMARY KEY,
  "NameFirst" VARCHAR(20),
  "NameLast"  VARCHAR(20),
  "NickName"  VARCHAR(30) NOT NULL
);


CREATE TABLE "Login"
(
  "Username"   VARCHAR(30) PRIMARY KEY,
  "HashedPass" CHAR(64) NOT NULL,
  "Salt"       CHAR(32) NOT NULL,
  "UserID"     INTEGER  NOT NULL REFERENCES "User"
);

CREATE TABLE "Player"
(
  "PlayerID"    BIGSERIAL PRIMARY KEY,
  "UserID"      INTEGER           NOT NULL REFERENCES "User",
  "PlayerColor" PLAYERCOLORTYPE   NOT NULL,
  "Outcome"     PLAYEROUTCOMETYPE NOT NULL
);

CREATE TABLE "Game"
(
  "GameID"            BIGSERIAL PRIMARY KEY,
  "gameStartDateTime" TIMESTAMP,
  "gameEndDateTime"   TIMESTAMP,
  "PlayerOneID"       INTEGER        NOT NULL REFERENCES "Player",
  "PlayerTwoID"       INTEGER        NOT NULL REFERENCES "Player",
  "GameStatus"        GAMESTATUSTYPE NOT NULL
);

CREATE TABLE "GamePiece"
(
  "PieceID"   BIGSERIAL PRIMARY KEY,
  "PlayerID"  INTEGER   NOT NULL REFERENCES "Player",
  "PieceType" PIECETYPE NOT NULL,
  "CoordY"    INTEGER   NOT NULL,
  "CoordX"    INTEGER   NOT NULL,
  "GameID"    INTEGER   NOT NULL REFERENCES "Game"
);

CREATE TABLE "GameInvitation"
(
  "InvitationID" BIGSERIAL PRIMARY KEY,
  "Sender"       INTEGER NOT NULL REFERENCES "User",
  "Recipient"    INTEGER NOT NULL REFERENCES "User"
);