CREATE TABLE zone
(
    dns_name    VARCHAR(63) PRIMARY KEY,
    name        VARCHAR(200)  NOT NULL,
    description VARCHAR(4000) NOT NULL
);

INSERT INTO zone (dns_name, name, description)
VALUES ('patrick.example', 'Patrick', 'Example zone');
