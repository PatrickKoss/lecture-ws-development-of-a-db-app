.PHONY: help check pdf clean

SOURCE := pruefung.md
STYLESHEET := print.css
BUILD_DIR := build
HTML := $(BUILD_DIR)/pruefung.html
PDF := pruefung.pdf
PANDOC ?= pandoc
CHROME ?= $(shell command -v google-chrome 2>/dev/null || command -v chromium 2>/dev/null || command -v chromium-browser 2>/dev/null)

help:
	@echo "make pdf                  build pruefung.pdf from pruefung.md"
	@echo "make clean                remove generated HTML and PDF"
	@echo "make pdf CHROME=/path     use a specific Chrome or Chromium binary"

check:
	@command -v $(PANDOC) >/dev/null 2>&1 || { echo "pandoc is required"; exit 1; }
	@test -n "$(CHROME)" || { echo "Google Chrome or Chromium is required"; exit 1; }
	@test -x "$(CHROME)" || { echo "browser is not executable: $(CHROME)"; exit 1; }

pdf: check $(PDF)

$(PDF): $(HTML)
	@$(CHROME) \
		--headless \
		--disable-gpu \
		--no-sandbox \
		--no-pdf-header-footer \
		--print-to-pdf="$(abspath $@)" \
		"file://$(abspath $<)" \
		2>$(BUILD_DIR)/chrome.log || { cat $(BUILD_DIR)/chrome.log; exit 1; }
	@echo "created $(PDF)"

$(HTML): $(SOURCE) $(STYLESHEET)
	@mkdir -p $(BUILD_DIR)
	@$(PANDOC) $(SOURCE) \
		--from=gfm+raw_html \
		--to=html5 \
		--standalone \
		--embed-resources \
		--css=$(STYLESHEET) \
		--output=$@

clean:
	@rm -rf $(BUILD_DIR) $(PDF)
