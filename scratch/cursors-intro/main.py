import sqlite3

# to interact with a database we need to connect to it
con = sqlite3.connect("accounts.db")
# executing sql queries requires a cursor
cur = con.cursor()

# create a database table
cur.execute("""
CREATE TABLE IF NOT EXISTS accounts (
	user_id serial PRIMARY KEY,
	username VARCHAR ( 50 ) UNIQUE NOT NULL,
	password VARCHAR ( 1000 ) NOT NULL,
	email VARCHAR ( 255 ) UNIQUE NOT NULL,
	created_on TIMESTAMP NOT NULL,
        last_login TIMESTAMP
);
""")

# query the database
res = cur.execute("""
SELECT * FROM accounts;
""")
# first time should return any results
for r in res.fetchall():
    print(r)

# create a table entry
try:
    cur.execute("""
    INSERT INTO accounts (user_id, username, password, email, created_on)
    VALUES ('c996c60b-c617-4c05-8eca-e3391a9b495e', 'test', 'test', 'test@test.com', '2022-10-10T13:10:11Z');
    """)
    con.commit()
except Exception:
    print("db table entry test@test.com already exists and therefore ignored")

# query the table again
res = cur.execute("""
SELECT * FROM accounts
""")
for r in res.fetchall():
    print(r)  # ('c996c60b-c617-4c05-8eca-e3391a9b495e', 'test', 'test', 'test@test.com', '2022-10-10T13:10:11Z', None)

# close the connection as best practicep
con.close()
