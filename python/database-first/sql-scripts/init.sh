#!/bin/sh
# create db
echo "SELECT 'CREATE DATABASE db_first' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'db_first')\gexec" | psql -U postgres
# create user
psql -U postgres --command "CREATE USER db_first WITH PASSWORD '123456';"
psql -U postgres --command "ALTER USER db_first WITH SUPERUSER;"
psql -U postgres --command "GRANT ALL PRIVILEGES ON DATABASE db_first to db_first;"
cd sql-scripts
psql -U db_first db_first -f create_db_tables.sql

exit
