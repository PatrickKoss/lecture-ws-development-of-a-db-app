PROJECTS := jdbc repository repository-exercise backend zone-example advanced-backend

.PHONY: build-all test-all frontend-test help

build-all:
	@set -e; \
	for project in $(PROJECTS); do \
		printf '==> %s: ./gradlew build\n' "$$project"; \
		if (cd "$$project" && ./gradlew build); then \
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
		if (cd "$$project" && ./gradlew test); then \
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
		'build-all  Build all Gradle projects and stop at the first failure.' \
		'test-all   Test all Gradle projects and stop at the first failure.' \
		'frontend-test  Test the prepared Next.js frontend.' \
		'help       Show these targets.'
