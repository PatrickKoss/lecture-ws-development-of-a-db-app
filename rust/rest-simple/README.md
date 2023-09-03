# Introduction
This is the rust implementation of the simple rest api. 

# Getting Started
Before running any command remember to export the DATABASE_URL environment variable. 
```bash
export DATABASE_URL=sqlite://$PWD/students.db
```

Run the application.
```bash
make run
```

Run the test suite:
```bash
make test
```

Visit the swagger ui:
```bash
http://localhost:8081/swagger-ui/
```
