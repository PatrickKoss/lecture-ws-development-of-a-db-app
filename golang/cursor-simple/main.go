package main

import (
	"database/sql"
	"fmt"
	_ "github.com/mattn/go-sqlite3"
	"log"
)

func main() {
	db, err := sql.Open("sqlite3", "./accounts.db")
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	sqlStmt := `
	CREATE TABLE IF NOT EXISTS accounts (
		user_id text PRIMARY KEY,
		username text UNIQUE NOT NULL,
		password text NOT NULL,
		email text UNIQUE NOT NULL,
		created_on text NOT NULL,
		last_login text
	);
	`
	_, err = db.Exec(sqlStmt)
	if err != nil {
		log.Printf("%q: %s\n", err, sqlStmt)
		return
	}

	rows, err := db.Query("SELECT * FROM accounts")
	if err != nil {
		log.Fatal(err)
	}
	defer rows.Close()
	printRows(rows)

	tx, err := db.Begin()
	if err != nil {
		log.Fatal(err)
	}
	stmt, err := tx.Prepare("INSERT INTO accounts(user_id, username, password, email, created_on) values(?, ?, ?, ?, ?)")
	if err != nil {
		log.Fatal(err)
	}
	defer stmt.Close()
	_, err = stmt.Exec("c996c60b-c617-4c05-8eca-e3391a9b495e", "test", "test", "test@test.com", "2022-10-10T13:10:11Z")
	if err != nil {
		log.Printf("db table entry test@test.com already exists and therefore ignored")
	} else {
		tx.Commit()
	}

	rows, err = db.Query("SELECT * FROM accounts")
	if err != nil {
		log.Fatal(err)
	}
	defer rows.Close()
	printRows(rows)
}

func printRows(rows *sql.Rows) {
	for rows.Next() {
		var user_id string
		var username string
		var password string
		var email string
		var created_on string
		var last_login sql.NullString
		err := rows.Scan(&user_id, &username, &password, &email, &created_on, &last_login)
		if err != nil {
			log.Fatal(err)
		}
		fmt.Println(user_id, username, password, email, created_on, last_login.String)
	}
	err := rows.Err()
	if err != nil {
		log.Fatal(err)
	}
}
