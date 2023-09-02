-- +migrate Up
CREATE TABLE IF NOT EXISTS student
(
);

-- +migrate Down
DROP TABLE student;
