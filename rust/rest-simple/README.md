# Introduction
Rust implementation of the rest api.

# Getting Started
1. Create the database lib
```bash
cargo new db --lib
```
2. Go to db
```bash
cd db 
```

3. Add the following to Cargo.toml
```toml
[dependencies]
diesel = { version = "2.1.0", features = ["sqlite"] }
dotenv = "0.15.0"
```

4. Install diesel_cli
```bash
cargo install diesel_cli --no-default-features --features "sqlite-bundled"
```

5. Create the .env file
```bash
echo DATABASE_URL=./students.db > .env
```

6. Initialize diesel
```bash
mkdir -p migrations && diesel setup
```

7. Create migration
```bash
diesel migration generate create_students
```

8. Edit the migration file

9. Run the migration
```bash
diesel migration run
```

10. Create the schema
```bash
diesel print-schema > src/schema.rs
```
