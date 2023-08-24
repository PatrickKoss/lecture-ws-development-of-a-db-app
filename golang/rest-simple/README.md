# Installation
Install sql migrate:
```bash
go install github.com/rubenv/sql-migrate/...@latest
```

Apply migrations:
```bash
sql-migrate up -config="internal/adapter/repository/sqlboiler/dbconfig.yaml" -env=local -limit=0
```

Install sqlboiler:
```bash
go install github.com/volatiletech/sqlboiler/v4@latest
go install github.com/volatiletech/sqlboiler/v4/drivers/sqlboiler-sqlite3@latest
```

Create models:
```bash
sqlboiler sqlite3 -c internal/adapter/repository/sqlboiler/sqlboiler.toml
```
