#!/usr/bin/env sh
set -eu
test -f starter/schema.sql
test -f starter/seed.sql
db=$(mktemp)
trap 'rm -f "$db"' EXIT
sqlite3 "$db" < starter/schema.sql
sqlite3 "$db" < starter/seed.sql
sqlite3 "$db" 'PRAGMA foreign_key_check;' | test ! -s /dev/stdin
