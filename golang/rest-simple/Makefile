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

run:
	go run cmd/main.go
