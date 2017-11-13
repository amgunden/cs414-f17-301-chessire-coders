DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE login
(
  user_id        BIGINT                NOT NULL
    CONSTRAINT login_pkey
    PRIMARY KEY,
  email          VARCHAR(64)           NOT NULL
    CONSTRAINT unq_login_email
    UNIQUE,
  hashed_pass    VARCHAR(44)           NOT NULL,
  account_locked BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE login_attempt
(
  login_attempt_id   BIGSERIAL NOT NULL
    CONSTRAINT login_attempt_pkey
    PRIMARY KEY,
  login_attempt_time TIMESTAMP NOT NULL,
  login_successful   BOOLEAN   NOT NULL,
  user_id            BIGINT
);

CREATE TABLE jungle_user
(
  user_id    BIGSERIAL            NOT NULL
    CONSTRAINT user_pkey
    PRIMARY KEY,
  name_first VARCHAR(20),
  name_last  VARCHAR(20),
  nick_name  VARCHAR(30)          NOT NULL
    CONSTRAINT unq_user_nickname
    UNIQUE,
  registered BOOLEAN DEFAULT TRUE NOT NULL
);

ALTER TABLE login
  ADD CONSTRAINT fk_login_user_userid
FOREIGN KEY (user_id) REFERENCES jungle_user;

ALTER TABLE login_attempt
  ADD CONSTRAINT fk_login_attempt_user_userid
FOREIGN KEY (user_id) REFERENCES jungle_user;

CREATE TABLE user_session
(
  session_id BIGSERIAL NOT NULL
    CONSTRAINT user_session_pkey
    PRIMARY KEY,
  ip_address VARCHAR(44),
  auth_token CHAR(24)
    CONSTRAINT unq_usersession_authtoken
    UNIQUE,
  expires_on TIMESTAMP,
  user_id    BIGINT
    CONSTRAINT fk_usersession_user_userid
    REFERENCES jungle_user
);


CREATE TYPE GAMESTATUS AS ENUM ('PENDING', 'ONGOING', 'DRAW', 'WINNER_PLAYER_ONE', 'WINNER_PLAYER_TWO');

CREATE TABLE game
(
  game_id         BIGSERIAL NOT NULL
    CONSTRAINT game_pkey
    PRIMARY KEY,
  player_one_id   BIGINT    NOT NULL
    CONSTRAINT fk_game_player_one_id
    REFERENCES jungle_user,
  player_two_id   BIGINT
    CONSTRAINT fk_game_player_two_id
    REFERENCES jungle_user,
  game_state      GAMESTATUS DEFAULT 'PENDING',
  start_date_time TIMESTAMP,
  end_date_time   TIMESTAMP
);

CREATE TYPE PLAYEROWNER AS ENUM ('PLAYER_ONE', 'PLAYER_TWO');

CREATE TYPE PIECETYPE AS ENUM ('CAT', 'DOG', 'ELEPHANT', 'FOX', 'LEOPARD', 'LION', 'RAT', 'TIGER');

CREATE TABLE game_piece
(
  piece_id        BIGSERIAL   NOT NULL
    CONSTRAINT game_piece_pkey
    PRIMARY KEY,
  player_owner    PLAYEROWNER NOT NULL,
  game_id         BIGINT      NOT NULL
    CONSTRAINT fk_game_piece_game_id
    REFERENCES game,
  piece_type      PIECETYPE,
  position_row    INTEGER     NOT NULL,
  CONSTRAINT chk_game_piece_row
    CHECK (0 <= position_row AND position_row < 9),
  position_column INTEGER     NOT NULL
  CONSTRAINT chk_game_piece_column
    CHECK (0 <= position_column AND position_column < 7)
);

ALTER TABLE game_piece
  ADD CONSTRAINT unq_row_column_game
UNIQUE (position_row, position_column, game_id);
