CORE_PROJECTS := jdbc repository-basic backend
STARTER_PROJECTS := exercises/b3-jdbc/starter exercises/c2-spring-resource/starter
EXTRA_PROJECTS := repository repository-exercise zone-example advanced-backend
PROJECTS ?= $(CORE_PROJECTS) $(STARTER_PROJECTS) $(EXTRA_PROJECTS)
GRADLE_FLAGS ?= --max-workers=2

.PHONY: build-core build-starters check-sql build-all test-all frontend-test help

build-core:
	$(MAKE) build-all PROJECTS="$(CORE_PROJECTS)"

build-starters:
	$(MAKE) build-all PROJECTS="$(STARTER_PROJECTS)"

check-sql:
	python3 sql/check.py

build-all:
	@set -e; \
	for project in $(PROJECTS); do \
		printf '==> %s: ./gradlew build\n' "$$project"; \
		if (cd "$$project" && ./gradlew build $(GRADLE_FLAGS)); then \
			printf '==> %s: build passed (exit 0)\n' "$$project"; \
		else \
			status=$$?; \
			printf '==> %s: build failed (exit %s)\n' "$$project" "$$status" >&2; \
			exit "$$status"; \
		fi; \
	done

test-all:
	@set -e; \
	for project in $(PROJECTS); do \
		printf '==> %s: ./gradlew test\n' "$$project"; \
		if (cd "$$project" && ./gradlew test $(GRADLE_FLAGS)); then \
			printf '==> %s: test passed (exit 0)\n' "$$project"; \
		else \
			status=$$?; \
			printf '==> %s: test failed (exit %s)\n' "$$project" "$$status" >&2; \
			exit "$$status"; \
		fi; \
	done

frontend-test:
	cd frontend && npm test -- --runInBand

help:
	@printf '%s\n' \
		'check-sql  Verify the worked SQL schema, seed data, and queries.' \
		'build-core  Build and test the completed JDBC, repository, and Spring examples.' \
		'build-starters  Build the JDBC and Spring lecture starters.' \
		'build-all  Build all Gradle projects and stop at the first failure.' \
		'test-all   Test all Gradle projects and stop at the first failure.' \
		'frontend-test  Test the prepared Next.js frontend.' \
		'help       Show these targets.'
