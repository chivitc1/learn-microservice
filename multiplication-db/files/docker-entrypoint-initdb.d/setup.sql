--DROP DATABASE IF EXISTS multiplication;
--CREATE DATABASE multiplication;
--\c "multiplication"

DROP TABLE IF EXISTS multiplication;
DROP SEQUENCE IF EXISTS multiplication_id_seq;
CREATE SEQUENCE multiplication_id_seq;

DROP TABLE IF EXISTS multiplication_result_attempt;
DROP SEQUENCE IF EXISTS multiplication_result_attempt_id_seq;
CREATE SEQUENCE multiplication_result_attempt_id_seq;

DROP TABLE IF EXISTS "user";
DROP SEQUENCE IF EXISTS user_id_seq;
CREATE SEQUENCE user_id_seq;

DROP TABLE IF EXISTS "badge_card"
DROP SEQUENCE IF EXISTS badge_card_id_seq;
CREATE SEQUENCE badge_card_id_seq;

DROP TABLE IF EXISTS "score_card"
DROP SEQUENCE IF EXISTS score_card_id_seq;
CREATE SEQUENCE score_card_id_seq;

CREATE TABLE multiplication (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('multiplication_id_seq'),
    factor_a INT NOT NULL,
    factor_b INT NOT NULL
);

CREATE TABLE multiplication_result_attempt (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('multiplication_result_attempt_id_seq'),
    user_id BIGINT NOT NULL,
    MULTIPLICATION_ID BIGINT NOT NULL,
    result_attempt INT NOT NULL,
    correct BIT NOT NULL
);

CREATE TABLE "user" (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('user_id_seq'),
    alias VARCHAR NOT NULL
);

CREATE TABLE "badge_card" (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('badge_card_id_seq'),
    user_id BIGINT NOT NULL,
    badge_timestamp TIMESTAMP,
    badge VARCHAR NOT NULL
);

CREATE TABLE "score_card" (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('score_card_id_seq'),
    user_id BIGINT NOT NULL,
    attempt_id BIGINT NOT NULL,
    score_timestamp TIMESTAMP,
    score INT NOT NULL
);