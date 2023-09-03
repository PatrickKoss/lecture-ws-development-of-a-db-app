run: set-db
	cargo run --manifest-path=api/Cargo.toml

.PHONY: build
build: set-db
	cargo build --manifest-path=api/Cargo.toml

build-db: set-db
	cargo build --manifest-path=db/Cargo.toml

build-service: set-db
	cargo build --manifest-path=service/Cargo.toml

set-db:
	MIGRATIONS_DIR=$(shell pwd)/db/migrations && \
	if [ "$(basename $(shell pwd))" != "api" ]; then cd api; fi && \
	cargo install sqlx-cli && \
	sqlx database create --database-url=$$DATABASE_URL && \
	sqlx migrate run --database-url=$$DATABASE_URL --source=$$MIGRATIONS_DIR && \
	cargo sqlx prepare --database-url=$$DATABASE_URL

clean:
	cargo clean --manifest-path=api/Cargo.toml

test:
	cargo test --manifest-path=api/Cargo.toml
	cargo test --manifest-path=service/Cargo.toml
