# Introduction
This repository provides some code examples for building a web app 
and understanding how to connect to a database with the programming 
language python.

# Project Structure
- [scratch](scratch) covers the idea of using the driver that create a cursor
to connect to a database. It shows the need of a migration framework to 
handle database migrations. Lastly it provides an example of using the 
repository pattern to abstract the database connection which also serves
as introduction to an orm.
- [base](base) provides an example of the django-rest-api and its orm
- [database-first](database-first) shows first building a relational
database and then generate programming code out of it which then can
be used as orm.
- [raw-sql](raw-sql) is mix of all previous concept, speaking cursors and orm.
