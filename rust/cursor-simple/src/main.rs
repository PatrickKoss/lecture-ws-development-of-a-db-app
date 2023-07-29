extern crate rusqlite;
extern crate chrono;

use rusqlite::{params, Connection, Result};
use chrono::prelude::*;

#[derive(Debug)]
struct Account {
    user_id: String,
    username: String,
    password: String,
    email: String,
    created_on: String,
    last_login: Option<String>,
}

fn main() -> Result<()> {
    let conn = Connection::open("accounts.db")?;

    conn.execute(
        "CREATE TABLE IF NOT EXISTS accounts (
                  user_id TEXT PRIMARY KEY,
                  username TEXT UNIQUE NOT NULL,
                  password TEXT NOT NULL,
                  email TEXT UNIQUE NOT NULL,
                  created_on TEXT NOT NULL,
                  last_login TEXT
                  )",
        params![],
    )?;

    let mut stmt = conn.prepare("SELECT * FROM accounts")?;
    let account_iter = stmt.query_map([], |row| {
        Ok(Account {
            user_id: row.get(0)?,
            username: row.get(1)?,
            password: row.get(2)?,
            email: row.get(3)?,
            created_on: row.get(4)?,
            last_login: row.get(5).ok(),
        })
    })?;

    for account in account_iter {
        println!("{:?}", account.unwrap());
    }

    conn.execute(
        "INSERT OR IGNORE INTO accounts (user_id, username, password, email, created_on)
         VALUES (?1, ?2, ?3, ?4, ?5)",
        params![
            "c996c60b-c617-4c05-8eca-e3391a9b495e",
            "test",
            "test",
            "test@test.com",
            Utc::now().to_rfc3339()
        ],
    )?;

    let mut stmt = conn.prepare("SELECT * FROM accounts")?;
    let account_iter = stmt.query_map([], |row| {
        Ok(Account {
            user_id: row.get(0)?,
            username: row.get(1)?,
            password: row.get(2)?,
            email: row.get(3)?,
            created_on: row.get(4)?,
            last_login: row.get(5).ok(),
        })
    })?;

    for account in account_iter {
        println!("{:?}", account.unwrap());
    }

    Ok(())
}
