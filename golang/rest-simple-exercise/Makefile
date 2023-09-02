GOLANGCI_VERSION = 1.53.3

download:
	go mod download

.PHONY: build
build:
	CGO_ENABLED=0 go build -ldflags "-s -w" -o ./bin/rest-simple -v cmd/main.go

test:
	go test -race ./...

mocks:
	go generate ./...

GOLANGCI_LINT = bin/golangci-lint-$(GOLANGCI_VERSION)
$(GOLANGCI_LINT):
	curl -sSfL https://raw.githubusercontent.com/golangci/golangci-lint/master/install.sh | bash -s -- -b bin v$(GOLANGCI_VERSION)
	@mv bin/golangci-lint "$(@)"

lint: $(GOLANGCI_LINT) download
	$(GOLANGCI_LINT) run -v

run: migrate
	go run cmd/main.go

SQL_MIGRATE = bin/sql-migrate
$(SQL_MIGRATE):
	GOBIN=$(PWD)/bin go install github.com/rubenv/sql-migrate/...@latest

migrate: $(SQL_MIGRATE)
	$(SQL_MIGRATE) up -config="internal/adapter/repository/sqlboiler/dbconfig.yaml" -env=local -limit=0

SQL_BOILER = bin/sqlboiler
$(SQL_BOILER):
	GOBIN=$(PWD)/bin go install github.com/volatiletech/sqlboiler/v4@latest
    GOBIN=$(PWD)/bin go install github.com/volatiletech/sqlboiler/v4/drivers/sqlboiler-sqlite3@latest

models: $(SQL_BOILER)
	$(SQL_BOILER) sqlite3 -c internal/adapter/repository/sqlboiler/sqlboiler.toml

SWAG = bin/swag
$(SWAG):
	GOBIN=$(PWD)/bin go install github.com/swaggo/swag/cmd/swag@latest

swagger: $(SWAG)
	$(SWAG) init -g internal/adapter/api/api.go -o ./api
