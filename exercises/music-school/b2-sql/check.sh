#!/usr/bin/env sh
set -eu
db=$(mktemp)
trap 'rm -f "$db"' EXIT
sqlite3 "$db" < ../b1-schema/starter/schema.sql
sqlite3 "$db" < ../b1-schema/starter/seed.sql
sqlite3 -header -column "$db" < starter/queries.sql
