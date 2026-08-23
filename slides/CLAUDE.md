# CLAUDE.md

This file describes the slide tooling for the course.

## Project

This directory contains the Reveal.js slides for "Entwicklung einer
Datenbankanwendung". The course runs for three days. The stage is 1920 by 1080
pixels and uses a dark, monospace theme.

German is the working language for slide text and speaker notes. Code,
identifiers, SQL keywords, and file names stay in English.

## Commands

Run commands from `slides/`.

```bash
make install
make run
make run DECK=day2
make lint
make format
make debug
make debug DECK=day1
make shot DECK=day1 SLIDE=0/2/1
make pdf DECK=day1 PDF=day1.pdf
make pdf-all
make clean
```

`DECK` accepts `index`, `day1`, `day2`, `day3`, or the same name with an `.html`
suffix. It defaults to `index.html`. `SLIDE` uses `h/v/f`, where `v` defaults to
zero and `f` to -1, the state before the first fragment. `f=0` shows the first
fragment.

Serve the files over HTTP. The chapter loader uses `fetch()`, which browsers
block when `index.html` is opened through `file://`.

## Architecture

### Content pipeline: `docs/draft.md` to decks

`../docs/draft.md` is the content specification. Each chapter will contain a
`### Reveal.js Prompt` section with the slide order, fragment states, diagrams,
and speaker notes. The matching file in `decks/` is the HTML output. Change the
specification when a content decision changes, then update the deck.

### Deck composition

`index.html` lists all 14 chapters. `day1.html`, `day2.html`, and `day3.html`
list only the chapters taught on that day. Each placeholder names one
`decks/NN-name.html` file.

`assets/deck.js` fetches each chapter before Reveal initializes. It places the
chapter's top-level sections inside one horizontal stack. A missing file becomes
a red "Failed to load" slide, while the remaining chapters still load.

### Reveal navigation model

Reveal addresses a state as `(h, v, f)`: chapter, slide inside that chapter, and
fragment. A chapter file contains top-level `<section>` elements. The loader
nests them under the chapter placeholder, so they become vertical slides.

`Reveal.getTotalSlides()` returns a linear count. The debug script resolves the
real `(h, v)` pair for every slide with `Reveal.getIndices()` before navigating.
It advances fragments with `Reveal.availableFragments()` and
`Reveal.nextFragment()`.

### Theme tokens and layouts

`assets/theme.css` owns all colors and layout rules. Deck HTML uses the existing
tokens such as `--color-bg`, `--color-blue`, `--color-amber`, `--color-red`,
`--color-white`, `--color-grey`, and `--color-green`. Do not add hard-coded
colors to a deck.

Use these layouts where the content calls for them:

- `.chapter-slide` opens a chapter.
- `.lead-question` stops on one question for the room.
- `.code-slide` shows highlighted code with line numbers and an optional
  `.code-slide__notes` column.
- `.exercise-slide` shows the task, time, and hand-in.
- `.redline`, `.redline__step`, and `.redline__step--active` draw the eight-step
  course path.

Code steps use the Reveal highlight plugin. Put a sequence such as
`data-line-numbers="1-4|6-9|11-14"` on the `<code>` element.

## Debug tooling

`make debug` captures every slide and every fragment at 1920 by 1080 pixels.
`fragment-00` is the base state before the first fragment, `fragment-01` the
state after the first click. It writes PNG files and
`debug-screenshots/index.json`. The index records the deck, linear slide number,
`(h, v)` coordinates, fragment number, heading, and file.

`make shot SLIDE=h/v/f` captures one state. It exits with a nonzero status when
the requested slide does not exist. Both tools start through the Makefile, which
also starts and stops the local server.

Check each PNG for clipped text, overlap, uneven padding, small type, horizontal
code scrolling, and incorrect line highlights.

## Prettier

`.prettierrc` uses two spaces, no tabs, bracket spacing, and wrapped prose. Run
`make lint` before handing off a deck. Run `make format` when the check reports
drift. `package-lock.json` is committed so installs use the recorded dependency
tree.
