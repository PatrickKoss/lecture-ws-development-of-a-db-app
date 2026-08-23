.PHONY: help install run lint format debug shot pdf pdf-all clean

PORT ?= 8000
BASE_URL ?= http://localhost:$(PORT)
SERVER_PID := .server-$(PORT).pid
SERVER_LOG := .server-$(PORT).log
DECK ?= index.html
DECK_FILE := $(if $(filter %.html,$(DECK)),$(DECK),$(DECK).html)
PDF ?= slides.pdf

define with_server
	@rm -f $(SERVER_PID) $(SERVER_LOG)
	@npx http-server -p $(PORT) -c-1 -s > $(SERVER_LOG) 2>&1 & echo $$! > $(SERVER_PID)
	@trap 'kill `cat $(SERVER_PID)` 2>/dev/null; rm -f $(SERVER_PID)' EXIT INT TERM; \
		echo "waiting for $(BASE_URL)/$(DECK_FILE)..."; \
		for i in 1 2 3 4 5 6 7 8 9 10; do \
			curl -sf $(BASE_URL)/$(DECK_FILE) > /dev/null && break || sleep 0.5; \
		done; \
		BASE_URL=$(BASE_URL) DECK=$(DECK_FILE) $(1)
endef

help:
	@echo "make install                         install npm dependencies and Playwright Chromium"
	@echo "make run [DECK=day1]                 serve a deck at $(BASE_URL)"
	@echo "make lint                            check HTML, CSS, JS, JSON, and Markdown with Prettier"
	@echo "make format                          format HTML, CSS, JS, JSON, and Markdown with Prettier"
	@echo "make debug [DECK=day2]               screenshot every slide and fragment"
	@echo "make shot SLIDE=h/v/f [DECK=day1]   screenshot one slide; v defaults to 0, f to -1 (base state)"
	@echo "make pdf [DECK=day1] [PDF=day1.pdf] export one deck to PDF"
	@echo "make pdf-all                         export slides.pdf and all three day PDFs"
	@echo "make clean                           remove screenshots, PDFs, and server files"

install:
	npm install
	npx playwright install chromium

run:
	npx http-server -p $(PORT) -c-1 -o /$(DECK_FILE)

lint:
	npm run lint

format:
	npm run format

debug:
	$(call with_server,node scripts/debug-slides.mjs)

shot:
	@if [ -z "$(SLIDE)" ]; then \
		echo "usage: make shot SLIDE=h/v/f [DECK=day1]"; \
		exit 1; \
	fi
	$(call with_server,SLIDE=$(SLIDE) node scripts/shot-slide.mjs)

pdf:
	$(call with_server,OUT_FILE=$(PDF) node scripts/export-pdf.mjs)

pdf-all:
	$(MAKE) pdf DECK=index.html PDF=slides.pdf
	$(MAKE) pdf DECK=day1 PDF=day1.pdf
	$(MAKE) pdf DECK=day2 PDF=day2.pdf
	$(MAKE) pdf DECK=day3 PDF=day3.pdf

clean:
	rm -rf debug-screenshots *.pdf $(SERVER_PID) $(SERVER_LOG)
