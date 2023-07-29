package main

import (
	"database/sql"
	"fmt"
	"log"
	"time"

	_ "github.com/mattn/go-sqlite3"
)

type Account struct {
	ID        string
	Username  string
	Password  string
	Email     string
	CreatedOn time.Time
}

type Repository interface {
	All() ([]Account, error)
	Get(id string) (*Account, error)
	Create(account Account) error
	Update(account Account) error
	Delete(id string) error
}

type AccountRepository struct {
	db *sql.DB
}

func NewAccountRepository() (Repository, error) {
	db, err := sql.Open("sqlite3", "./accounts.db")
	if err != nil {
		return nil, err
	}

	_, err = db.Exec(`CREATE TABLE IF NOT EXISTS accounts (
        id TEXT PRIMARY KEY,
        username TEXT NOT NULL,
        password TEXT NOT NULL,
        email TEXT NOT NULL,
        created_on DATETIME NOT NULL
    )`)

	if err != nil {
		return nil, err
	}

	return &AccountRepository{db: db}, nil
}

func (r *AccountRepository) All() ([]Account, error) {
	rows, err := r.db.Query("SELECT id, username, password, email, created_on FROM accounts")
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	var accounts []Account
	for rows.Next() {
		var a Account
		err = rows.Scan(&a.ID, &a.Username, &a.Password, &a.Email, &a.CreatedOn)
		if err != nil {
			return nil, err
		}
		accounts = append(accounts, a)
	}

	return accounts, rows.Err()
}

func (r *AccountRepository) Get(id string) (*Account, error) {
	var a Account
	err := r.db.QueryRow("SELECT id, username, password, email, created_on FROM accounts WHERE id = ?", id).Scan(&a.ID, &a.Username, &a.Password, &a.Email, &a.CreatedOn)
	if err == sql.ErrNoRows {
		return nil, nil
	} else if err != nil {
		return nil, err
	}
	return &a, nil
}

func (r *AccountRepository) Create(account Account) error {
	_, err := r.db.Exec("INSERT INTO accounts (id, username, password, email, created_on) VALUES (?, ?, ?, ?, ?)", account.ID, account.Username, account.Password, account.Email, account.CreatedOn)
	return err
}

func (r *AccountRepository) Update(account Account) error {
	_, err := r.db.Exec("UPDATE accounts SET username = ?, password = ?, email = ?, created_on = ? WHERE id = ?", account.Username, account.Password, account.Email, account.CreatedOn, account.ID)
	return err
}

func (r *AccountRepository) Delete(id string) error {
	_, err := r.db.Exec("DELETE FROM accounts WHERE id = ?", id)
	return err
}

func main() {
	repo, err := NewAccountRepository()
	if err != nil {
		log.Fatal(err)
	}

	newAccount := Account{
		ID:        "test2",
		Username:  "test2",
		Password:  "test2",
		Email:     "test2@test.com",
		CreatedOn: time.Now(),
	}

	err = repo.Create(newAccount)
	if err != nil {
		log.Fatal(err)
	}

	accounts, err := repo.All()
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println("All accounts:", accounts)

	account, err := repo.Get("test2")
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println("Single account:", account)

	newAccount.Username = "test3"
	err = repo.Update(newAccount)
	if err != nil {
		log.Fatal(err)
	}

	account, err = repo.Get("test2")
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println("Updated account:", account)

	err = repo.Delete("test2")
	if err != nil {
		log.Fatal(err)
	}

	accounts, err = repo.All()
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println("All accounts after deletion:", accounts)
}
