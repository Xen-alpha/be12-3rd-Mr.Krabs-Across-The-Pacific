CREATE TABLE user_tier IF NOT EXISTS (
    idx BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    grade VARCHAR(255) NOT NULL
);

INSERT INTO user_tier(grade) VALUES ('Bronze');
INSERT INTO user_tier(grade) VALUES ('Silver');
INSERT INTO user_tier(grade) VALUES ('Gold');
INSERT INTO user_tier(grade) VALUES ('Platinum');