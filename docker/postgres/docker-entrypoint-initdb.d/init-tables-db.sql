CREATE TABLE "User"
(
  "UserID"    BIGSERIAL   NOT NULL,
  "NameFirst" VARCHAR(20),
  "NameLast"  VARCHAR(20),
  "NickName"  VARCHAR(30) NOT NULL,

  CONSTRAINT "PK_User_UserID" PRIMARY KEY ("UserID"),
  CONSTRAINT "UNQ_User_NickName" UNIQUE ("NickName")
);


CREATE TABLE "Login"
(
  "UserID"     INTEGER     NOT NULL,
  "Email"      VARCHAR(64) NOT NULL,
  "HashedPass" CHAR(44)    NOT NULL,

  CONSTRAINT "PK_Login_UserID" PRIMARY KEY ("UserID"),
  CONSTRAINT "UNQ_Login_Email" UNIQUE ("Email"),
  CONSTRAINT "FK_UserID_User" FOREIGN KEY ("UserID") REFERENCES "User" ("UserID")
);

CREATE TABLE "LoginAttempt"
(
  "UserID" INT       NOT NULL,
  "Time"   TIMESTAMP NOT NULL,
  "Locked" BOOLEAN   NOT NULL DEFAULT FALSE,

  CONSTRAINT "FK_LoginID_Login" FOREIGN KEY ("UserID") REFERENCES "Login" ("UserID")
);
