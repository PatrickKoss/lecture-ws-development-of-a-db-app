# Slides: Entwicklung einer Datenbankanwendung

This directory contains the Reveal.js deck for the three-day database
application course. Slide text and speaker notes are written in German.

## Project

The full course is in `index.html`. The files `day1.html`, `day2.html`, and
`day3.html` contain the daily subsets. Slide content is specified in
`../docs/draft.md` and implemented as one chapter file under `decks/`.

## Commands

```bash
make install
make run
make run DECK=day1
make lint
make format
make debug DECK=day2
make shot DECK=day1 SLIDE=0/2/1
make pdf DECK=day1 PDF=day1.pdf
make pdf-all
```

`DECK` defaults to `index.html`. It also accepts `index`, `day1`, `day2`, and
`day3` without the suffix. The files must be served over HTTP because the shell
loads chapters with `fetch()`.

## Architecture

### Content pipeline: `docs/draft.md` to decks

Each chapter in `../docs/draft.md` defines the narrative, slide sequence,
fragments, visuals, and notes. Its HTML counterpart is
`decks/NN-chapter-name.html`.

### Deck composition

The four shell files list chapter placeholders. `assets/deck.js` fetches the
chapter files and initializes Reveal. A missing chapter appears as a red error
slide and does not stop the rest of the deck.

### Navigation model

Reveal uses `(h, v, f)`: chapter, slide, fragment. For example, `0/2/1` opens
the third slide of chapter 00 at its second visible code state.

### Theme tokens and layouts

`assets/theme.css` defines the palette. Deck files use its CSS variables instead
of color literals.

The main layouts are `.chapter-slide`, `.lead-question`, `.code-slide`, and
`.exercise-slide`. Code slides support line numbers and stepped highlights.
Exercise slides reserve fixed areas for the tag, time, task, and hand-in. The
`.redline` helpers draw the eight course steps and mark the current one.

## Keyboard

- `ArrowRight` or `Space` moves forward.
- `ArrowLeft` moves back.
- `s` opens speaker notes.
- `o` opens the overview.

## Debug tooling

`make debug` writes every rendered state to `debug-screenshots/` and records the
coordinates in `debug-screenshots/index.json`. `make shot` captures one state.
Both commands use a 1920 by 1080 Chromium viewport.

## Prettier

`make lint` checks HTML, CSS, JavaScript, JSON, and Markdown. `make format`
writes the configured two-space format. Commit `package-lock.json` with changes
to dependencies.
