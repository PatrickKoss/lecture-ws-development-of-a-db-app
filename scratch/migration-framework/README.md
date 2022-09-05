# Introduction
After learning how to connect to a database with programming code we realized
we need to manage the creation and update of our database tables. Migrationsframeworks
are entering the stage. We use [sql-migrate](https://github.com/rubenv/sql-migrate)
to understand how a migration framework works before jumping into a 
full-fledged ORM like django.

# Getting Started
Running under windows you might consider installing choco:
```bash
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
```
Then install golang:
````bash
choco install golang
````
Eventually you need to install gcc:
```bash
choco install mingw -y
```
Install sql-migrate:
```bash
go install github.com/rubenv/sql-migrate/...@latest
```

# Playground
After getting started we want to apply the tool. We created two migration files
and now want them to appear in the database:
```bash
sql-migrate up
```
To confirm that our migrations are in the database run:
```bash
sqlite3 accounts.db
.schema
```
Next we want to roll back the latest migration because of eventual mistakes:
```bash
sql-migrate down
```
Check the schema again for confirming the downgrade:
```bash
sqlite3 accounts.db
.schema
```

# Explanation
On the first execution sql-migrate will run the command:
````
CREATE TABLE IF NOT EXISTS "gorp_migrations" ("id" varchar(255) not null primary key, "applied_at" datetime);
```` 
which create a table to store the migrations in. The tool watches the specified
migrations folder (in our case [migrations](migrations)) and creates tables 
entries for up migrations in order of files in the directory. While downgrading
it reads the latest entries in the migrations table and execute the "down" command
in the file. After that it will delete the table entry.