# Getting Started
Create the database with the following command:
```
sqlite3 ../api/students.db
```
Then, create the table with the following command:
```
 sqlx migrate run --database-url "sqlite:../api/students.db"
```
Check if the queries are correct:
```
cargo sqlx prepare --database-url "sqlite:../api/students.db"
```
