-- +migrate Up
ALTER TABLE accounts ADD email2 varchar(255);

-- +migrate Down
CREATE TABLE IF NOT EXISTS accounts_backup (
    user_id serial PRIMARY KEY,
    username VARCHAR ( 50 ) UNIQUE NOT NULL,
    password VARCHAR ( 1000 ) NOT NULL,
    email VARCHAR ( 255 ) UNIQUE NOT NULL,
    created_on TIMESTAMP NOT NULL,
    last_login TIMESTAMP
);
INSERT INTO accounts_backup SELECT user_id, username, password, email, created_on, last_login FROM accounts;
DROP TABLE accounts;
ALTER TABLE accounts_backup RENAME TO accounts;