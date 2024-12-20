CREATE TABLE profile
(
    id         INT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    username   VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    `role`     VARCHAR(255) NOT NULL,
    status     VARCHAR(255) NOT NULL,
    start_time time NULL,
    end_time   time NULL,
    created_at datetime     NOT NULL,
    `visible`  BIT(1)       NOT NULL,
    CONSTRAINT pk_profile PRIMARY KEY (id)
);

ALTER TABLE profile
    ADD CONSTRAINT uc_profile_email UNIQUE (email);

ALTER TABLE profile
    ADD CONSTRAINT uc_profile_username UNIQUE (username);