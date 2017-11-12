DROP ALL OBJECTS;

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
  login_attempt_id     IDENTITY    NOT NULL
    CONSTRAINT login_attempt_pkey
    PRIMARY KEY,
  login_attempt_time TIMESTAMP NOT NULL,
  login_successful   BOOLEAN   NOT NULL,
  user_id            BIGINT,
  CONSTRAINT unq_login_attempt_userid_time
  UNIQUE (user_id, login_attempt_time)
);

CREATE TABLE jungle_user
(
  user_id    IDENTITY            NOT NULL
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
  session_id IDENTITY NOT NULL
    CONSTRAINT user_session_pkey
    PRIMARY KEY,
  ip_address    VARCHAR(44),
  auth_token    CHAR(24)
    CONSTRAINT unq_usersession_authtoken
    UNIQUE,
  expires_on    TIMESTAMP,
  user_id       BIGINT
    CONSTRAINT fk_usersession_user_userid
    REFERENCES jungle_user
);

