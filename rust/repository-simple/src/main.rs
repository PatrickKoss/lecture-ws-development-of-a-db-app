extern crate anyhow;
extern crate chrono;
extern crate rusqlite;
extern crate uuid;

use anyhow::Result;
use chrono::prelude::*;
use rusqlite::{Connection, params};
use uuid::Uuid;

#[derive(Debug, Clone)]
pub struct Account {
    id: String,
    username: String,
    password: String,
    email: String,
    created_on: NaiveDateTime,
}

pub trait Repository<T> {
    fn all(&self) -> Result<Vec<T>>;
    fn get(&self, id: &str) -> Result<Option<T>>;
    fn create(&self, t: T) -> Result<usize>;
    fn update(&self, id: &str, t: T) -> Result<usize>;
    fn delete(&self, id: &str) -> Result<usize>;
}

pub struct AccountRepository {
    conn: Connection,
}

impl AccountRepository {
    pub fn new() -> Result<Box<dyn Repository<Account>>> {
        let conn = Connection::open("accounts.db")?;
        conn.execute(
            "CREATE TABLE IF NOT EXISTS accounts (
            id TEXT PRIMARY KEY,
            username TEXT NOT NULL,
            password TEXT NOT NULL,
            email TEXT NOT NULL,
            created_on TEXT NOT NULL
        )",
            [],
        )?;

        Ok(Box::new(Self { conn }))
    }
}

impl Repository<Account> for AccountRepository {
    fn all(&self) -> Result<Vec<Account>> {
        let mut stmt = self.conn.prepare("SELECT id, username, password, email, created_on FROM accounts")?;
        let accounts_iter = stmt.query_map([], |row| {
            let created_on = NaiveDateTime::parse_from_str(&row.get::<_, String>(4)?, "%Y-%m-%d %H:%M:%S")
                .map_err(|_| rusqlite::Error::InvalidQuery); // convert ParseError to rusqlite::Error

            Ok(Account {
                id: row.get(0)?,
                username: row.get(1)?,
                password: row.get(2)?,
                email: row.get(3)?,
                created_on: created_on?,
            })
        });

        match accounts_iter {
            Ok(iter) => {
                let accounts: rusqlite::Result<Vec<Account>> = iter.collect();
                accounts.map_err(|err| anyhow::anyhow!(err)) // Here the rusqlite::Error is converted to anyhow::Error
            },
            Err(err) => Err(anyhow::anyhow!(err)), // If an error occurs in query_map, convert it to anyhow::Error
        }
    }

    fn get(&self, id: &str) -> anyhow::Result<Option<Account>> {
        let mut stmt = self.conn.prepare("SELECT id, username, password, email, created_on FROM accounts WHERE id = ?1")?;
        let rows = stmt.query_map(params![id], |row| {
            let created_on = NaiveDateTime::parse_from_str(&row.get::<_, String>(4)?, "%Y-%m-%d %H:%M:%S")
                .map_err(|_| rusqlite::Error::InvalidQuery); // convert ParseError to rusqlite::Error

            Ok(Account {
                id: row.get(0)?,
                username: row.get(1)?,
                password: row.get(2)?,
                email: row.get(3)?,
                created_on: created_on?,
            })
        })?;

        let mut accounts = Vec::new();
        for account_result in rows {
            accounts.push(account_result?);
        }

        Ok(accounts.into_iter().next())
    }

    fn create(&self, t: Account) -> Result<usize> {
        self.conn.execute(
            "INSERT INTO accounts (id, username, password, email, created_on) VALUES (?1, ?2, ?3, ?4, ?5)",
            params![t.id, t.username, t.password, t.email, t.created_on.format("%Y-%m-%d %H:%M:%S").to_string()],
        ).map_err(|err| anyhow::anyhow!(err))
    }

    fn update(&self, id: &str, t: Account) -> Result<usize> {
        self.conn.execute(
            "UPDATE accounts SET username = ?1, password = ?2, email = ?3, created_on = ?4 WHERE id = ?5",
            params![t.username, t.password, t.email, t.created_on.format("%Y-%m-%d %H:%M:%S").to_string(), id],
        ).map_err(|err| anyhow::anyhow!(err))
    }

    fn delete(&self, id: &str) -> Result<usize> {
        self.conn.execute("DELETE FROM accounts WHERE id = ?1", params![id]).map_err(|err| anyhow::anyhow!(err))
    }
}

fn main() -> Result<()> {
    let repo = AccountRepository::new()?;

    let new_account = Account {
        id: Uuid::new_v4().to_string(),
        username: "test2".to_string(),
        password: "test2".to_string(),
        email: "test2@test.com".to_string(),
        created_on: chrono::Local::now().naive_local(),
    };

    repo.create(new_account)?;

    let accounts = repo.all()?;
    println!("All accounts: {:?}", accounts);

    let account = repo.get(&accounts[0].id)?;
    println!("Single account: {:?}", account);

    if let Some(account) = account {
        let mut updated_account = account.clone();
        updated_account.username = "test3".to_string();
        repo.update(&account.id, updated_account)?;
    }

    let accounts = repo.all()?;
    println!("All accounts after deletion: {:?}", accounts);

    Ok(())
}
