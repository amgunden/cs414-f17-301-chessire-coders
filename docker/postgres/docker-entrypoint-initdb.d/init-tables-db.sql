DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE login
(
  email          VARCHAR(64)           NOT NULL
    CONSTRAINT login_pkey
    PRIMARY KEY,
  nick_name      VARCHAR(30)           NOT NULL
    CONSTRAINT unq_login_nick_name
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
  nick_name          VARCHAR(30)
);

CREATE TABLE jungle_user
(
  nick_name  VARCHAR(30)          NOT NULL
    CONSTRAINT user_pkey
    PRIMARY KEY,
  name_first VARCHAR(20),
  name_last  VARCHAR(20),
  registered BOOLEAN DEFAULT TRUE NOT NULL
);

ALTER TABLE login
  ADD CONSTRAINT fk_login_user_nick_name
FOREIGN KEY (nick_name) REFERENCES jungle_user;

ALTER TABLE login_attempt
  ADD CONSTRAINT fk_login_attempt_user_nick_name
FOREIGN KEY (nick_name) REFERENCES jungle_user;

CREATE TABLE user_session
(
  session_id BIGSERIAL NOT NULL
    CONSTRAINT user_session_pkey
    PRIMARY KEY,
  ip_address VARCHAR(44),
  auth_token CHAR(24)
    CONSTRAINT unq_user_session_auth_token
    UNIQUE,
  expires_on TIMESTAMP,
  nick_name  VARCHAR(30)
    CONSTRAINT fk_user_session_user_nick_name
    REFERENCES jungle_user
);


CREATE TABLE game
(
  game_id              BIGSERIAL                        NOT NULL
    CONSTRAINT game_pkey
    PRIMARY KEY,
  player_one_nick_name VARCHAR(30)                      NOT NULL
    CONSTRAINT fk_game_player_one_nick_name
    REFERENCES jungle_user,
  player_two_nick_name VARCHAR(30)
    CONSTRAINT fk_game_player_two_nick_name
    REFERENCES jungle_user,
  turn_of_player       VARCHAR(12) DEFAULT 'PLAYER_ONE' NOT NULL,
  CONSTRAINT chk_game_turn_of_player
  CHECK (turn_of_player IN ('PLAYER_ONE', 'PLAYER_TWO')),

  game_state           VARCHAR(20) DEFAULT 'PENDING'    NOT NULL,
  CONSTRAINT chk_game_state
  CHECK (game_state IN ('PENDING', 'ONGOING', 'DRAW', 'WINNER_PLAYER_ONE', 'WINNER_PLAYER_TWO')),
  start_date_time      TIMESTAMP,
  end_date_time        TIMESTAMP
);


CREATE TABLE game_piece
(
  piece_id        BIGSERIAL   NOT NULL
    CONSTRAINT game_piece_pkey
    PRIMARY KEY,
  player_owner    VARCHAR(10) NOT NULL,
  CONSTRAINT chk_game_piece_player_owner
  CHECK (player_owner IN ('PLAYER_ONE', 'PLAYER_TWO')),
  game_id         BIGINT      NOT NULL
    CONSTRAINT fk_game_piece_game_id
    REFERENCES game,
  piece_type      VARCHAR(8),
  CONSTRAINT chk_game_piece_type
  CHECK (piece_type IN ('CAT', 'DOG', 'ELEPHANT', 'FOX', 'LEOPARD', 'LION', 'RAT', 'TIGER')),
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


CREATE TABLE invitation
(
  invitation_id            BIGSERIAL                      NOT NULL
    CONSTRAINT invitation_pkey
    PRIMARY KEY,
  user_sender_nick_name    VARCHAR(30)                    NOT NULL
    CONSTRAINT fk_invitation_sender_nick_name
    REFERENCES jungle_user,
  user_recipient_nick_name VARCHAR(30)                    NOT NULL
    CONSTRAINT fk_invitation_recipient_nick_name
    REFERENCES jungle_user,
  game_id                  BIGINT                         NOT NULL
    CONSTRAINT fk_invitation_game_id
    REFERENCES game,
  invite_status            VARCHAR(10) DEFAULT 'PENDING'  NOT NULL,
  CONSTRAINT chk_invitation_status
  CHECK (invite_status IN ('PENDING', 'ACCEPTED', 'REJECTED')),
  invitation_created_time  TIMESTAMP DEFAULT now()        NOT NULL
);
