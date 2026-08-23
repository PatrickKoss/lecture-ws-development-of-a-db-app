# Implementation plan: slides, exercises and code for "Development of a database app"

This document is the working plan for building the full 3-day course. It is
written so that an orchestrator agent can read it, split it into work packages,
hand each package to a subagent, and verify the result. Humans can read it too.

The course already has code in `java/`. The slides, the exercise catalog and the
instructor material do not exist yet. That is what we build.

## 1. What we are building

Three deliverables, one repository.

1. A reveal.js slide deck in `slides/`, same tooling and visual style as
   `/home/patrick/projects/distributed-systems/slides-short` (dark, mono,
   SVG-heavy, 1920x1080, one HTML fragment per chapter, `make debug`, `make shot`,
   `make pdf`).
2. An exercise catalog in `exercises/`: 8 real-world domains (plus 2 spares),
   each carried through every stage of the course, so a group keeps "their"
   system from the first ER diagram to the finished REST API.
3. The Java code in `java/`, cleaned up and aligned with the slides, plus an
   instructor guide with timings, lead questions and expected answers.

Audience: 3rd semester students, first contact with databases, only Java.
Teaching style: 25-45 minute blocks, instructor asks questions and leads the
students to the answer, then a group exercise, then back to slides. 8 groups.
The course runs 3 days.

Language: German. Decided. All slide text, speaker notes, exercise cards,
the instructor guide and the schedule are written in German. Code, identifiers,
SQL keywords, file names, commit messages and this plan stay English. The
existing Java READMEs stay English; new "Where this sits on the red line"
sections in them are German so students read one language in the course
material. Technical terms keep their English form where that is what students
will meet in practice (Repository, Controller, Foreign Key, Primary Key, JOIN),
with the German term given once in brackets on first use. Unslop applies to the
German text the same way; the patterns list is language-independent.

## 2. The red line

Every chapter has to answer "where are we on the line, and why is this step
needed for the next one". The line is:

```
idea in someone's head
  -> talk about it: abstraction, ER model            (Day 1)
  -> tables that do not lie to us: normalization     (Day 1)
  -> ask the tables questions: SQL                   (Day 2)
  -> ask them from Java: JDBC, cursor, statements    (Day 2)
  -> stop repeating ourselves: repository pattern    (Day 2)
  -> let other people use it: REST API with Spring   (Day 3)
  -> make it solid: layers, validation, errors,
     OpenAPI, tests, middleware                      (Day 3)
```

Two domains run in parallel through the whole course:

- The instructor's worked example is the **university** domain: `Student`,
  `Course`, `Lecturer`, `Enrollment`. It already exists in `rest-simple` and in
  both exercise skeletons (`Student`). The cursor example currently uses `Book`
  and the repository example uses `Account`; see section 8 for the decision to
  align them.
- Each group has its own domain from the catalog (section 7) and applies every
  step to it.

The opening deck shows the line as one diagram. Every chapter deck starts with
the same diagram with the current step highlighted. The closing deck shows it
again, this time with every box mapped to the files the students wrote.

## 3. Reference setup to copy

Source of truth for tooling and style:
`/home/patrick/projects/distributed-systems/slides-short`.

What to copy, and what it does:

| File / dir                  | Purpose                                                                                           |
| --------------------------- | ------------------------------------------------------------------------------------------------- |
| `Makefile`                  | `install`, `run`, `lint`, `format`, `debug`, `shot SLIDE=h/v/f`, `pdf [PDF=out.pdf]`, `clean`. Boots a silent `http-server`, waits, runs Playwright, kills server. |
| `package.json`              | `reveal.js`, `http-server`, `playwright`, `prettier`. Scripts `dev`, `serve`, `lint`, `format`, `debug`. |
| `index.html`                | Thin shell. `<section data-external-replace="decks/NN-*.html">` placeholders, inline loader that `fetch`es each fragment before `Reveal.initialize`. Stage 1920x1080, `margin: 0.04`, `navigationMode: linear`. |
| `decks/NN-*.html`           | One file per chapter. A file may contain several top-level `<section>` elements (vertical stack). |
| `assets/theme.css`          | Palette tokens (`--color-bg`, `--color-blue`, `--color-amber`, `--color-red`, `--color-white`, `--color-grey`, `--color-green`, ...), `.slide-title`, `.slide-caption`, `.slide-context`, `.slide-frame*`, `.chapter-slide`, `.citation-slide`. Decks never hard-code hex values. |
| `assets/frame.svg`          | Decorative frame used on chapter slides.                                                          |
| `scripts/debug-slides.mjs`  | Walks every slide and fragment, writes PNGs + `debug-screenshots/index.json`. Resolves `(h, v)` via `Reveal.getIndices` so vertical stacks are not skipped. |
| `scripts/shot-slide.mjs`    | Screenshot of one `(h, v, f)`; exits non-zero on out-of-range coordinates.                        |
| `scripts/export-pdf.mjs`    | Loads `?print-pdf`, waits for layout, prints one slide per page at 20in x 11.25in.                |
| `CLAUDE.md`                 | Explains the pipeline (draft.md prompt -> deck HTML), the navigation model, the tokens, the debug tooling. |
| `../docs/draft.md`          | The content spec. Per chapter: narrative, then a `### Reveal.js Prompt` block describing base state, fragments and visuals. The prompt is the spec, the HTML is the artifact. |
| `../.prettierrc`, husky     | Two-space indent, `proseWrap: always`; pre-commit `prettier --check`.                             |

Two small extensions on top of the reference:

- Per-day shells. Besides `index.html` (whole course) add `day1.html`,
  `day2.html`, `day3.html` that include only that day's decks. The scripts read
  `DECK` (default `index.html`) so `make pdf DECK=day1 PDF=day1.pdf` and
  `make debug DECK=day2` work. `make pdf-all` writes all four PDFs.
- Code slides. Enable the reveal.js highlight plugin (`plugin/highlight`) and
  add a `.code-slide` layout in `theme.css` (title top-left, code block with
  line numbers and `data-line-numbers="1-3|5-9"` step highlighting, optional
  annotation column on the right). The lecture has many more code slides than
  the talk had.

Also new in `theme.css`: `.exercise-slide` (amber "Exercise" tag top-left,
timer hint top-right, task text large, "deliverable" box bottom), and
`.lead-question` (big centered question used when the instructor stops and
asks the room). Both reuse the existing tokens.

## 4. Repository layout after the work

```
lecture-ws-development-of-a-db-app/
  docs/
    implementation-plan.md        this file
    draft.md                      content spec, one section per chapter (WP-B)
    instructor-guide.md           timings, lead questions, expected answers (WP-F)
    schedule.md                   3-day timetable with block lengths (WP-F)
  slides/
    Makefile  package.json  index.html  day1.html  day2.html  day3.html
    CLAUDE.md  README.md  .prettierrc
    assets/theme.css  assets/frame.svg  assets/img/...
    decks/00-opening.html ... decks/13-closing.html
    scripts/debug-slides.mjs  shot-slide.mjs  export-pdf.mjs
    debug-screenshots/            git-ignored
  exercises/
    README.md                     how groups are assigned, what to hand in
    _template/                    the per-domain folder skeleton
    library/  pizza-delivery/  gym/  cinema/  bike-rental/
    vet-clinic/  car-workshop/  event-tickets/
    hotel/  food-delivery-marketplace/        (spares)
      each: README.md, 01-er.md, 02-normalization.md, 03-sql.md,
            04-jdbc.md, 05-repository.md, 06-rest.md,
            sql/schema.sql, sql/seed.sql, sql/queries.sql,
            solutions/ (instructor only, see section 7)
  java/
    cursor-simple/  repository-simple/  repository-simple-exercise/
    rest-simple-exercise/  rest-simple-exercise-example/  rest-simple/
    sql/university/ schema.sql seed.sql queries.sql   (lecture example data)
```

`exercises/*/solutions/` is committed but listed in the student handout as "do
not open before the debrief". If the repo is public to students, the
orchestrator moves solutions to a separate branch `instructor` instead. Ask the
user which one.

## 5. Rules every agent follows

### 5.1 Writing rules: unslop

Every agent that writes prose (slides, speaker notes, READMEs, exercise cards,
commit messages, the instructor guide) applies the `unslop` rules. No em dashes,
sentence case headings, no "bold label: restated sentence" lists, no puffery, no
"Let me know if", plain words, active voice, short sentences.

- Claude Code agents: the rules are inlined in the global `~/.claude/CLAUDE.md`
  and also available as the `unslop` skill. Invoke the skill at the start of
  every writing task anyway, and run the self-audit step before returning.
- Codex agents: load the `unslop` skill from `~/.codex/skills/unslop`
  explicitly. It takes precedence over the generic writing section in the
  system-wide `~/.codex/AGENTS.md`. If the two disagree, unslop wins.

Slides are not exempt. Slide text is the worst place for AI tells because there
is so little of it that every filler word shows.

### 5.2 Screenshot loop for slide agents

A deck is not done until its agent has looked at it. The loop:

1. Write or change `decks/NN-name.html`.
2. `make lint` (prettier). Fix.
3. `make shot SLIDE=h` for the first slide of the chapter, then `make debug`
   once the chapter is complete (or `make debug DECK=dayN` to limit the run).
4. Open every PNG of the chapter with the Read tool and check, per slide and per
   fragment state:
   - nothing overflows the 1920x1080 stage, no text cut at the bottom or right
   - text does not overlap SVG shapes, arrows end where they should
   - boxes inside a diagram share the same padding and corner radius, columns
     align on a grid, equal gaps between repeated elements
   - title, context tag and caption sit at the frame positions defined in
     `theme.css`, not hand-positioned
   - font sizes are readable from the back row (body text >= 28px equivalent at
     stage size, code >= 24px), contrast against `--color-bg` is fine
   - fragment order makes sense when read click by click
   - code slides: no horizontal scrollbar, line highlights match the notes
5. Fix, re-shoot, repeat until clean. Then write a two-line summary of what was
   checked into the work package result.
6. A second agent (reviewer, see 9.4) repeats step 4 on the final screenshots
   without seeing the first agent's notes.

### 5.3 Verification for code agents

Run what CI would run. This repo has no GitHub workflow yet, so the gate is:
`./gradlew build test` in every touched Java project, `sqlite3 < schema.sql`
and `sqlite3 < seed.sql` and `queries.sql` for every SQL file, `make lint` in
`slides/`. If a project has a `verify-exercise.sh`, run it. Report failures
verbatim; do not declare done with a red build.

### 5.4 Content rules for slides

- One idea per slide. If the speaker notes need more than 6 sentences, split.
- Every chapter opens with the red-line diagram, current step highlighted.
- Every chapter has at least one `.lead-question` slide: the question the
  instructor asks before showing the answer. The expected answer and two wrong
  answers that students usually give go into `<aside class="notes">`.
- Every chapter ends with a "real world" slide: one concrete system, one
  concrete fact (for example "the Deutsche Bahn ticket API is a REST API with
  an OpenAPI spec", "SQLite runs in every Android phone and every Firefox
  profile", "Spring Data repositories are the same idea as the
  `AbstractRepository` you wrote").
- Exercises are slides too (`.exercise-slide`) so the deck can be presented
  front to back without switching windows. The full task lives in
  `exercises/<domain>/0N-*.md`; the slide shows the short version and the time
  box.
- Speaker notes carry the instructor's script. The instructor guide links to
  them rather than duplicating them.
- No decorative emojis, no stock photos. Diagrams are inline SVG using the
  theme tokens.

## 6. Curriculum and deck list

Days run 9:00-16:00 (decided): lunch 12:15-13:00, two 15 minute breaks, about
5.5 hours of teaching per day. The block times below add up to more than that on
purpose; the reserve rules at the end of this section say what is cut first,
and `docs/schedule.md` holds the binding timetable. Block = slides with back and forth; Ex = group
exercise. Slide counts are targets, not quotas.

### Day 1: from idea to a schema

| #  | Deck file                       | Block                                                                                   | Time    | Slides |
| -- | ------------------------------- | --------------------------------------------------------------------------------------- | ------- | ------ |
| 00 | `00-opening.html`               | Who, why, the 3-day map, the red line, group and domain assignment                       | 20 min  | 6-8    |
| 01 | `01-why-abstraction.html`       | "Build me an app for the pizza place." Why we plan before we type. Map vs territory. What goes wrong without a shared model (a war story). Ex 0: describe your domain in 5 sentences, underline nouns and verbs (15 min). | 35 + 15 | 10-12  |
| 02 | `02-er-model.html`              | Entities, attributes, keys, relationships, cardinalities (1:1, 1:n, n:m), optionality, weak entities, Chen vs crow's foot, what ER is not (not tables, not a class diagram, no types, no indexes). Worked example: university. Common mistakes. Ex 1: ER diagram for your domain (40 min), two groups present. | 45 + 40 + 15 | 18-22 |
| 03 | `03-er-to-tables.html`          | Mapping rules: entity -> table, 1:n -> foreign key, n:m -> join table, weak entity -> composite key, attribute types, NULL. First look at `CREATE TABLE`. Ex 2: map your ER to tables on paper (20 min). | 30 + 20 | 10-12  |
| 04 | `04-normalization.html`         | Insert/update/delete anomalies on one flat spreadsheet. Functional dependencies. 1NF, 2NF, 3NF, BCNF ("3.5NF"). When real systems stop (and when they denormalize on purpose). Ex 3: a messy flat table for your domain, normalize to 3NF, then check your Day 1 ER against it (40 min). | 45 + 40 | 18-22  |
| 04b| (in `04`)                       | Day 1 wrap: what we have now is a schema. Tomorrow we ask it questions.                   | 10 min  | 2      |

### Day 2: SQL, then the first Java program that talks to a database

| #  | Deck file                       | Block                                                                                   | Time    | Slides |
| -- | ------------------------------- | --------------------------------------------------------------------------------------- | ------- | ------ |
| 05 | `05-sql-basics.html`            | What SQL is (declarative: say what, not how). `sqlite3` CLI live. DDL: `CREATE TABLE`, types, `PRIMARY KEY`, `NOT NULL`, `UNIQUE`, `FOREIGN KEY`. DML: `INSERT`, `SELECT ... WHERE ... ORDER BY ... LIMIT`, `UPDATE`, `DELETE`. Ex 4: create your schema in SQLite and seed 10-20 rows per table (40 min). | 45 + 40 | 18-22  |
| 06 | `06-sql-joins-aggregation.html` | `JOIN` explained as row matching (animated SVG, not just Venn), `LEFT JOIN`, `GROUP BY`, `COUNT/SUM/AVG/MIN/MAX`, `HAVING`, subqueries, `NULL` surprises, `DISTINCT`. Ex 5: 8 queries for your domain from easy to hard, last two need `GROUP BY` + `HAVING` and a subquery (45 min). | 45 + 45 | 18-22  |
| 07 | `07-jdbc-cursor.html`           | Bridge to Java: what a driver is, `DriverManager`, `Connection`, `Statement` vs `PreparedStatement`, `ResultSet` as a cursor, mapping a row to an object, `try-with-resources`, SQL injection live demo, the SQLite date gotcha. Walk through `java/cursor-simple`. Ex 6: `repository-simple-exercise`: implement `StudentRepositoryImpl` (45 min), then add one table from your own domain (30 min). | 45 + 75 | 20-24  |
| 08 | `08-repository-pattern.html`    | Why the CRUD code looks the same for every table. Interface first. `AbstractRepository<T>` with reflection and `@Entity`/`@Column` (`java/repository-simple`). "This is what Spring Data does for you." ORM in two sentences. Migrations: why `CREATE TABLE IF NOT EXISTS` is not enough, Flyway `V1__`. Ex 7: add `findByX` to your repository and a second entity with a foreign key (30 min). | 40 + 30 | 14-18  |
| 08b| (in `08`)                       | Day 2 wrap: our data is safe on disk and we have a clean Java API for it. Nobody else can reach it yet. | 10 min | 2 |

### Day 3: a REST API with Spring, done properly

| #  | Deck file                       | Block                                                                                   | Time    | Slides |
| -- | ------------------------------- | --------------------------------------------------------------------------------------- | ------- | ------ |
| 09 | `09-http-rest-openapi.html`     | Client/server, request/response, HTTP methods, status codes (200/201/204/400/404/409/500), headers, JSON. REST: resources and nouns, URL design, idempotency of PUT/DELETE. OpenAPI/Swagger: the contract. Real APIs: GitHub, Stripe. Ex 8: design your API on paper: resources, endpoints, request/response JSON, status codes (25 min). | 40 + 25 | 16-20  |
| 10 | `10-spring-boot.html`           | What a framework takes off your plate. Dependency injection in one diagram. `@SpringBootApplication`, `@RestController`, `@GetMapping`, `@Service`, `@Repository`, `@Entity`, `JpaRepository` (= yesterday's `AbstractRepository`), `application.properties`, `bootRun`, Swagger UI. Live: `rest-simple-exercise` phases 1-2 (health endpoint -> first GET -> JPA entity -> first POST). Ex 9: get your first resource to list and create via Swagger UI (45 min). | 45 + 45 | 18-22  |
| 11 | `11-good-design.html`           | The three layers: presentation, service, repository. When a service layer is worth it and when the controller may just validate and save (our case). DTO vs entity and why you never return the entity. `@Valid` and bean validation. `@ControllerAdvice`, `ErrorResponse`, right status codes. Naming. Hexagonal architecture as the bigger picture (`rest-simple`), shown, not required. Ex 10: full CRUD for your main resource plus one relationship, validation, error handling, visible in Swagger (75 min). | 40 + 75 | 18-22  |
| 12 | `12-making-it-solid.html`       | Tests: `MockMvc` controller test, service unit test with Mockito, what to test and what not. Middleware: logging filter with correlation id, actuator health and metrics, CORS. Security outline only: why `rest-simple` has JWT, what a filter chain is. Docker in one slide. Ex 11 (if time): write two tests, add the logging filter (40 min). | 35 + 40 | 14-18  |
| 13 | `13-closing.html`               | The red line again, every box now points at a file in the students' projects. "What you built is how the backend of X works." What to learn next. Feedback. | 20 min | 6-8    |

Total target: 190-240 slides including exercise and chapter slides.

### Reserve content

If a day runs short: 06 gets window functions shown but not exercised, 08 gets a
"transactions and `BEGIN/COMMIT`" slide, 12 gets pagination and `PATCH`. If a
day runs long: 12 becomes optional, Ex 7 is dropped, Ex 10 is shortened to
"main resource only".

## 7. Exercise catalog

Eight domains, two spares. Each group keeps one domain for all three days. All
domains are designed to hit the same teaching points so the debrief is
comparable across groups:

- 4-6 entities, at least one 1:n and one n:m relationship, one relationship
  with its own attributes (so the join table is not empty), one optional
  relationship, one candidate for a weak entity
- a flat "spreadsheet from the owner" table with obvious repetition for the
  normalization exercise, including one transitive dependency (3NF) and one
  BCNF trap
- a query set that needs `JOIN`, `LEFT JOIN`, `GROUP BY`, `HAVING`, a subquery
  and a date comparison
- one main resource for the REST exercise and one relationship endpoint

| Domain               | Entities (draft)                                                   | Flat table source                        | Main REST resource |
| -------------------- | ------------------------------------------------------------------ | ---------------------------------------- | ------------------ |
| library              | Member, Book, Copy, Loan, Author, Reservation                      | "loans.xlsx" from the front desk         | `/loans`           |
| pizza-delivery       | Customer, Order, OrderItem, Pizza, Topping, Driver, Address        | order notepad of the shop                | `/orders`          |
| gym                  | Member, Membership, Course, Trainer, Booking, Room                 | course sign-up sheet                     | `/bookings`        |
| cinema               | Movie, Screening, Hall, Seat, Ticket, Customer                     | ticket sales CSV                         | `/screenings`      |
| bike-rental          | Station, Bike, Customer, Rental, Maintenance, Tariff               | rental log                               | `/rentals`         |
| vet-clinic           | Owner, Pet, Vet, Appointment, Treatment, Medication                | appointment book                         | `/appointments`    |
| car-workshop         | Customer, Vehicle, WorkOrder, Mechanic, Part, WorkOrderPart        | invoice list                             | `/work-orders`     |
| event-tickets        | Event, Venue, Organizer, TicketCategory, Ticket, Buyer             | box office export                        | `/events`          |
| hotel (spare)        | Guest, Room, RoomType, Booking, Service, BookingService            | reception sheet                          | `/bookings`        |
| food-marketplace (spare) | Restaurant, Dish, Customer, Order, OrderItem, Courier, Review  | platform export                          | `/orders`          |

Per domain folder (`exercises/<domain>/`):

| File                 | Content                                                                                                   |
| -------------------- | --------------------------------------------------------------------------------------------------------- |
| `README.md`          | One paragraph of story ("You are hired by ..."), what the group hands in at the end of each day.           |
| `01-er.md`           | The narrative with deliberate ambiguities (so groups must ask questions), the Ex 1 task, hand-in format.   |
| `02-normalization.md`| The flat table (markdown table, 12-20 rows), the anomalies to find, the Ex 3 task.                         |
| `03-sql.md`          | Ex 4 and Ex 5: schema and seed requirements, the 8 queries in prose ("how many X per Y, only Y with more than 2").|
| `04-jdbc.md`         | Ex 6: which entity to add to `repository-simple-exercise`, the CLI menu items to add.                      |
| `05-repository.md`   | Ex 7: the `findBy` method and the second entity.                                                           |
| `06-rest.md`         | Ex 8-11: resources, endpoints, validation rules, error cases, test ideas.                                  |
| `sql/schema.sql`     | Reference schema (solution for Ex 2/4), runs on SQLite 3.                                                 |
| `sql/seed.sql`       | 10-20 rows per table, realistic names, dates in ISO format.                                                |
| `sql/queries.sql`    | Solutions for the 8 queries with expected row counts as comments.                                          |
| `solutions/`         | ER diagram as SVG (same style as slides), normalized tables, `06-rest` endpoint list with status codes.     |

The instructor's university domain gets the same treatment in
`java/sql/university/` and is what the slides show.

Assignment: `exercises/README.md` holds a table "group -> domain" with empty
group names to fill on Day 1. The opening deck has a slide for it.

## 8. Code work in `java/`

The code exists. The work is alignment, not rewriting. Order of decisions,
with the default the orchestrator takes if the user does not say otherwise:

1. Align `cursor-simple` (currently `Book`) and `repository-simple` (currently
   `Account`) to the university domain (`Student`) so that Day 2 shows one
   entity evolving from raw JDBC to repository to Spring Data. Default: yes,
   do it; keep the old versions in git history. The `Book` variant can stay as
   `cursor-simple-book` only if the user wants it for the library group.
2. `cursor-simple/Main.java` duplicates the date parsing three times. Extract
   a `rowToStudent(ResultSet)` method. That is a teaching point (the
   repository chapter starts from exactly this duplication), so the slide in
   07 shows the "before" and the slide in 08 the "after".
3. `repository-simple-exercise`: keep as the Ex 6 skeleton. Check that
   `verify-exercise.sh` works on a clean checkout, that the README matches the
   slide's task wording, and add the "add your own entity" part as a second
   section.
4. `rest-simple-exercise`: the README is a 7-phase todo list. Trim it to what
   the slides cover (phases 1-3 = Day 3 morning, phase 4 = shown from
   `rest-simple`, phases 5-7 = deck 12). Each phase gets a link to the slide
   number and the exercise card. Default port and Swagger URL must be correct
   on first run.
5. `rest-simple-exercise-example` is the worked solution (`Zone`). Keep, check
   it builds, reference it from the instructor guide only.
6. `rest-simple` is the full hexagonal version with JWT, metrics, Flyway. It
   is the "bigger picture" in deck 11 and 12. Make sure `make run` and
   `make test` pass; do not extend it.
7. Add `java/sql/university/{schema,seed,queries}.sql` used in decks 05 and
   06 and as the `students.db` seed for Day 2 and 3, so the live demos and the
   students' databases match the slides.
8. Add a top-level `Makefile` in `java/` with `build-all` and `test-all` that
   loop over the projects, so the code verification gate is one command.

Each Java project README gets a short "Where this sits on the red line" section
at the top: which chapter uses it, what the student learns here, what comes
next.

## 9. Work packages for the orchestrator

Dependencies: WP-A and WP-B first (they can run in parallel). WP-C needs both.
WP-D and WP-E can run alongside WP-C. WP-F needs WP-B and WP-D. WP-G is last.

Concurrency notes: deck agents touch only their own `decks/NN-*.html` and may
append classes to `theme.css` only through the orchestrator (send the CSS
snippet back; the orchestrator merges to avoid conflicts). `index.html` and
the day shells are edited by the orchestrator. Exercise agents touch only
their domain folder. Code agents touch one Java project each. Use worktrees if
two agents must touch the same project.

### WP-A: slide tooling scaffold

Input: the reference repo. Output: `slides/` with everything from section 3,
one placeholder deck, `make install && make lint && make debug && make pdf`
green. Includes `slides/CLAUDE.md` rewritten for this course (same sections as
the reference, content updated), `.gitignore` for `node_modules`,
`debug-screenshots`, `*.pdf`, `.server.*`. Adds the highlight plugin, the
`.code-slide`, `.exercise-slide`, `.lead-question` layouts, and the `DECK`
variable in Makefile and scripts. Run the full screenshot loop once on the
placeholder to prove the tooling works. One agent.

### WP-B: content spec `docs/draft.md`

Input: this plan, the Java code, the reference `draft.md` format. Output: one
section per deck 00-13 with:

- narrative (what the instructor says, 3-6 paragraphs, in the instructor's
  voice)
- the list of lead questions with expected and typical wrong answers
- the real-world anchor
- slide-by-slide `### Reveal.js Prompt` blocks: base state, fragments, visual,
  notes text, which CSS layout to use
- the exercise slide text and time box
- the "reserve" slides marked as such

One agent for the whole draft so the voice stays consistent, then a reviewer
agent checks the red line (each chapter references the previous and the next),
checks that the university example is used consistently, and runs the unslop
audit. This is the package the user should read and approve before decks are
generated, because it is cheaper to change prose than 14 decks.

### WP-C-00 to WP-C-13: decks

One agent per deck, in parallel, up to the concurrency cap. Input: the deck's
section of `draft.md`, `slides/CLAUDE.md`, `theme.css`, two finished reference
decks from `distributed-systems/slides-short/decks` for style. Output: the
deck file, screenshots checked per 5.2, a result note with slide count,
fragment count, and anything the agent could not fit. Definition of done:

- `make lint` clean
- every slide screenshot reviewed by the agent, issues fixed
- speaker notes present on every content slide
- red-line diagram at the start with the right step highlighted
- at least one lead-question slide, one real-world slide, exercise slides where
  the draft says so
- no hard-coded colors, no text outside the stage

Deck 00 and 13 depend on the final chapter list and should run last in the
batch.

### WP-D-<domain>: exercise domains

One agent per domain, in parallel. Input: section 7, `exercises/_template/`,
the university example as style reference. Output: the domain folder per
section 7, SQL verified with `sqlite3` (schema loads, seed loads, every query
runs and returns the commented row count). Definition of done includes a
self-check that the domain hits all teaching points in section 7 (list them
with yes/no in the result note). The `_template/` and `exercises/README.md`
come from a small WP-D-0 that runs first.

### WP-E-1 to WP-E-8: code alignment

One agent per item in section 8, sequentially for items that touch the same
project. Gate: `./gradlew build test` and, where present, `verify-exercise.sh`.
Result note lists the commands run and their exit codes.

### WP-F: instructor material

Input: `draft.md`, the exercise folders, the deck screenshots. Output:
`docs/schedule.md` (timetable with clock times for a 9:00 start, block, deck,
exercise, break) and `docs/instructor-guide.md` (per block: goal, lead
questions with answers, common student mistakes and how to steer, debrief
script for each exercise, what to do if time runs out). One agent, unslop
audit at the end.

### WP-G: final verification

Sequential, after everything else.

1. `make lint`, `make debug`, `make pdf-all` in `slides/`. Open the PDFs and
   spot-check 10 random pages against the PNGs (print layout drops fragments
   sometimes).
2. A fresh reviewer agent walks all `debug-screenshots/` with the 5.2 checklist
   and writes `docs/review-slides.md` with per-slide findings. The orchestrator
   dispatches fixes back to the deck agents and re-runs.
3. Consistency pass: grep decks and exercises for entity names, chapter
   numbers, time boxes, port numbers (8081), URLs; they must agree with
   `draft.md`, `schedule.md` and the READMEs.
4. `make -C java build-all test-all`; every `exercises/*/sql` re-run.
5. unslop audit on every `.md` file touched in this effort.
6. Git: small commits per work package, messages in plain language, no AI
   boilerplate.

### 9.4 Agent prompt skeletons

Deck agent:

```
You build deck NN for the course "Development of a database app".
Read, in this order: docs/implementation-plan.md sections 2, 3, 5; slides/CLAUDE.md;
slides/assets/theme.css; docs/draft.md section "NN ..."; two reference decks:
/home/patrick/projects/distributed-systems/slides-short/decks/01-*.html and 05-*.html.
Write slides/decks/NN-name.html only. Use theme tokens, never hex.
Then run the screenshot loop from plan section 5.2 until clean.
Apply the unslop skill to all slide text and notes.
Return: slide count, fragment count, list of screenshots reviewed, open issues.
Do not edit index.html, theme.css, or other decks; return CSS you need as a snippet.
```

Exercise agent:

```
You build the exercise folder for domain <name>.
Read docs/implementation-plan.md section 7, exercises/_template/, java/sql/university/.
Produce every file in the template. Verify SQL with sqlite3. Apply unslop.
Return the teaching-point checklist with yes/no and the sqlite3 output summary.
```

Reviewer agent (slides):

```
Review slides/debug-screenshots/ for deck NN (see index.json for the mapping).
Use the checklist in docs/implementation-plan.md section 5.2. Do not fix anything.
Return a table: slide, fragment, issue, severity (blocks / should fix / nit).
```

## 10. Open decisions for the user

The orchestrator asks these once, before WP-B, and records the answers at the
top of `draft.md`:

1. (decided) Language is German, see section 1.
2. (decided) Solutions in `exercises/*/solutions/` on main.
3. (decided) Align `cursor-simple` and `repository-simple` to `Student`.
4. (decided) Three consecutive days, 9:00-16:00.
5. (decided) VS Code.
6. (decided) Nothing graded, all in-class.

All six are decided; see section 12 for the log.

## 11. Definition of done for the whole effort

- `slides/`: `make install`, `make lint`, `make debug`, `make pdf-all` run
  green on a clean clone; all 14 decks present; every screenshot reviewed
  twice; `slides.pdf`, `day1.pdf`, `day2.pdf`, `day3.pdf` produced.
- `exercises/`: 10 domain folders complete, SQL verified, teaching-point
  checklist all yes.
- `java/`: every project builds and tests pass with one command; READMEs
  link to slides; university SQL seed present.
- `docs/`: `draft.md`, `schedule.md`, `instructor-guide.md`,
  `review-slides.md` present and unslop-clean.
- The red line is visible: a reader who only opens `00-opening.html`,
  `13-closing.html` and `schedule.md` can explain what the course does and in
  which order.

## 12. Task tracking (orchestrator log)

Executor: Codex CLI, model `gpt-5.6-sol`, reasoning `high`, run with
`codex exec --skip-git-repo-check --dangerously-bypass-approvals-and-sandbox`
(explicitly allowed by the user). Up to 4 subagents in parallel. The
orchestrator (Claude) updates this table after every work package.

Status values: `todo`, `running`, `review`, `done`, `blocked`.

| ID        | Task                                              | Status  | Notes |
| --------- | ------------------------------------------------- | ------- | ----- |
| WP-A      | slides/ tooling scaffold                          | done    | make install/lint/debug/pdf-all green, placeholder deck 00 checked |
| WP-B      | docs/draft.md content spec (German)               | done    | 231 slides, 12 exercises, 4 reserve slides; open: rest-simple field names differ (name, mnr, createdOn), left as is since rest-simple is only the bigger picture |
| WP-B-rev  | review of draft.md (red line, unslop)             | done    | 232 slides; code/SQL facts verified against repo; 146 text blocks revised; open: rest-simple-exercise README says registrationDate and lacks studentNumber (fix in WP-E-4) |
| WP-D-0    | exercises/README.md + _template/                  | done    | 13 template files + CHECKLIST.md |
| WP-E-7    | java/sql/university seed + queries                | done    | 12 queries verified with sqlite3 |
| WP-E-8    | java/Makefile build-all/test-all                  | done    | all 6 projects build |
| WP-E-1    | align cursor-simple + repository-simple to Student| done    | Main (2 mappings) + MainRefactored (rowToStudent); AbstractRepository fixed; builds green |
| WP-E-2    | cursor-simple rowToStudent refactor               | done    | folded into WP-E-1 |
| WP-E-3    | repository-simple-exercise check + own-entity part| done    | README rewritten (German), Teil 2 eigene Domäne; verify-exercise.sh only checks build + markers (weak, noted) |
| WP-E-4    | rest-simple-exercise README trim + links          | done    | 810 to 336 lines, German, phases linked to decks 10-12 and Übungen 9-11, enrollmentDate/studentNumber fixed; orchestrator commented out spring-boot-starter-security in build.gradle.kts (it put a login wall in front of Swagger UI, the example project already had it commented out); build ok, api-docs/health/swagger-ui 200 without auth |
| WP-E-5    | rest-simple-exercise-example build check          | done    | builds, api-docs 200; known: V1_Create_zone.sql misnamed, Flyway skips it, ddl-auto=update covers it (check in WP-G) |
| WP-E-6    | rest-simple build/test check                      | done    | build+test green, health and api-docs 200 |
| WP-C-00   | deck 00 opening                                   | done    | 7 slides, 11 steps, lint ok; agent found that debug-slides.mjs started at fragment 0 (base state never captured): orchestrator fixed both scripts, fragment-00 is now the base state and `make shot` f defaults to -1 |
| WP-C-01   | deck 01 why abstraction                           | done    | 11 slides, 33 fragments; relation labels on slide 7 dim, recheck in WP-G |
| WP-C-02   | deck 02 ER model                                  | done    | 20 slides, 45 fragment steps, lint ok, spot check clean |
| WP-C-03   | deck 03 ER to tables                              | done    | 11 slides; polish: slide 3.7 (n:m join table) right arrow covers `id` in courses box, fix in WP-G |
| WP-C-04   | deck 04 normalization                             | done    | 20 slides, lint ok; polish: 3NF schema slide, `enrollment_date` touches the students box border |
| WP-C-05   | deck 05 SQL basics                                | done    | 20 slides, 51 steps, SQL matches queries.sql; lint ok (final rerun hit deck 07 mid-edit, rerun in WP-G) |
| WP-C-06   | deck 06 joins and aggregation                     | done    | 20 slides, 40 steps, lint ok; `make debug` hit a page-load timeout twice with the growing index deck, agent fell back to `make shot` per state; orchestrator raised the Reveal-ready timeout 30s to 120s in all three Playwright scripts |
| WP-C-07   | deck 07 JDBC cursor                               | done    | 22 slides, 49 steps, lint ok; polish: "Dasselbe Row Mapping zweimal" sits in the top half, lower half empty, center it vertically |
| WP-C-08   | deck 08 repository pattern                        | done    | 17 slides, lint ok; polish: Spring Data bridge slide, callout text touches its border and footer uses decorative em dashes |
| WP-C-09   | deck 09 HTTP REST OpenAPI                         | done    | 18 slides, lint ok; debug PNG of the POST pipeline showed the 409 branch mid-fade (250 ms wait vs 600 ms fade), verified final state with make shot; orchestrator raised the post-fragment wait to 800 ms |
| WP-C-10   | deck 10 Spring Boot                               | done    | 20 slides, 52 steps, lint ok, spot check clean |
| WP-C-11   | deck 11 good design                               | done    | 20 slides, 54 steps, lint ok, spot check clean |
| WP-C-12   | deck 12 making it solid                           | done    | 18 slides, lint ok; orchestrator added `font-variant-ligatures: none` for code in theme.css (`>=` rendered as a ligature) |
| WP-C-13   | deck 13 closing                                   | done    | 8 slides, 29 fragments, lint ok |
| WP-D-1    | domain library                                    | done    | pilot, 18/18 checklist, 8 queries verified; golden example for the rest |
| WP-D-2    | domain pizza-delivery                             | done    | verified |
| WP-D-3    | domain gym                                        | done    | verified |
| WP-D-4    | domain cinema                                     | done    | 18/18, 8 queries verified |
| WP-D-5    | domain bike-rental                                | done    | verified |
| WP-D-6    | domain vet-clinic                                 | done    | verified |
| WP-D-7    | domain car-workshop                               | done    | verified |
| WP-D-8    | domain event-tickets                              | done    | verified; all 8 domain SQL sets re-run by orchestrator, all ok |
| WP-D-9    | spare domain hotel                                | done    | verified |
| WP-D-10   | spare domain food-marketplace                     | done    | verified |
| WP-F      | schedule.md + instructor-guide.md                 | done    | schedule 3x420 min, Ex 7 dropped, Ex 10 55 min; orchestrator merged the 5-min deck 07 pre-lunch sliver into Übung 5 (50 min) and moved deck 07 whole after lunch; guide 611 lines, security bullet updated |
| WP-G-1    | java build-all/test-all + exercise SQL re-run      | done    | 6 projects build+test green; 10 domains schema/seed/queries clean with sqlite3 |
| WP-G-2    | slides lint/debug/pdf-all + blind review          | done    | lint clean, 812 PNGs, 4 PDFs; reviews in docs/review/review-NN.md: 1 blockiert (deck 03 grade type), 74 sollte, ~20 nit |
| WP-G-3    | fix review findings + polish list                 | done    | all blockiert/sollte findings fixed and re-shot, incl. deck 03 grade REAL, deck 08 footer em dashes, deck 07 centering; lint clean |
| WP-G-4    | consistency grep + unslop audit of .md            | done    | orchestrator pass done: no em dashes in decks, ports consistent, registrationDate gone (rest-simple-exercise-example README rewritten German, instructor-only), stray cursor-simple/logs removed; exercise time boxes aligned to schedule (Ex 5 50 min, Ex 10 55 min, Beziehungsendpunkt optional in all 06-rest.md); markdown corpus verified clean of curly quotes and em dashes (only pre-existing java/rest-simple/deploy/README.md keeps them, untouched) |
| WP-G-5    | commits per work package                          | todo    |       |

Orchestrator notes:

- `~/.codex/skills/unslop` did not exist; symlinked to `~/.claude/skills/unslop` (2026-08-23) so Codex agents find it.
- Exercise slide layout from WP-A leaves the middle empty; deck agents must fill it with 2-4 task steps.
- Highlight theme gives code blocks a brownish background; acceptable, deck agents may override with `--color-grey-dim` via theme if it looks off.
- Codex agents must be launched with `-C` = repo root; prompts use repo-relative paths.
- Deck agents run in parallel: Makefile now uses per-port pid/log files; each deck agent uses `PORT=80NN OUT_DIR=debug-screenshots/NN`.
- Reference deck `05-consistent-hashing.html` named in the first deck prompts does not exist in distributed-systems; agents fell back to `05-cross-channel-timing.html`. Prompts from WP-C-05 on name the correct file.
- Polish list for WP-G (visual nits seen in orchestrator spot checks, not blocking): deck 01 slide 7 relation labels in the pizzeria diagram are dim and "wohnt an" overlaps the arrow; deck 03 slide 7 (n:m join table) the right arrow head covers `id` in the courses box.

Decisions taken so far (all from the user, 2026-08-23):

- Language: German.
- Solutions live in `exercises/*/solutions/` on main.
- Align `cursor-simple` and `repository-simple` to `Student`.
- Days run 9:00-16:00, three consecutive days.
- Students use VS Code, nothing is graded.
