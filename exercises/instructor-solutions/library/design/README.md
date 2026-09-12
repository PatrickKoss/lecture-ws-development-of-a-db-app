# Musterlösung

> Zeitbox für die Erstellung: 45 Minuten nach Abschluss der Aufgabenblätter

Diese Dateien sind für die gemeinsame Auswertung gedacht. Studierende öffnen sie erst im Debrief.

## Normalisierte Tabellen

Das Referenzmodell hat sieben Tabellen. `book_authors` setzt die Beziehung zwischen Buch und Autor als Zuordnungstabelle um.

### `members`

Attribute sind `id`, `membership_number`, `first_name`, `last_name`, `email`, `joined_on` und `active`. `id` ist der Primary Key. `membership_number` und `email` sind jeweils eindeutige Alternativschlüssel. Alle Attribute außer `id` sind Pflichtfelder. `active` ist auf 0 oder 1 beschränkt.

Funktionale Abhängigkeiten:

- `id -> membership_number, first_name, last_name, email, joined_on, active`
- `membership_number -> id, first_name, last_name, email, joined_on, active`
- `email -> id, membership_number, first_name, last_name, joined_on, active`

### `authors`

Attribute sind `id`, `first_name`, `last_name` und `birth_year`. `id` ist der Primary Key. Name und Geburtsjahr bilden zusammen einen eindeutigen Alternativschlüssel.

Funktionale Abhängigkeiten:

- `id -> first_name, last_name, birth_year`
- `(first_name, last_name, birth_year) -> id`

### `books`

Attribute sind `id`, `isbn`, `title`, `publication_year`, `subject_area` und `shelf_code`. `id` ist der Primary Key. `isbn` sowie die Kombination aus `title` und `shelf_code` sind Alternativschlüssel. Erscheinungsjahr und Textlängen haben Wertebereichsprüfungen.

Funktionale Abhängigkeiten:

- `id -> isbn, title, publication_year, subject_area, shelf_code`
- `isbn -> id, title, publication_year, subject_area, shelf_code`
- `(title, shelf_code) -> id, isbn, publication_year, subject_area`
- `subject_area -> shelf_code`

Die letzte Abhängigkeit ist die BCNF-Falle. `subject_area` ist kein Superschlüssel. `shelf_code` ist aber ein Primattribut des Alternativschlüssels `(title, shelf_code)`. Die Tabelle erfüllt daher die 3NF, aber nicht die BCNF. Eine BCNF-Zerlegung würde `subject_shelves(subject_area, shelf_code)` abtrennen. Für das Kursmodell bleibt die 3NF-Fassung bestehen, damit `shelf_code` direkt am Buch lesbar bleibt.

### `book_authors`

Attribute sind `book_id`, `author_id` und `author_order`. Der Primary Key besteht aus `book_id` und `author_id`. `book_id` verweist auf `books.id`, `author_id` auf `authors.id`. Die Kombination aus `book_id` und `author_order` ist eindeutig.

Funktionale Abhängigkeiten:

- `(book_id, author_id) -> author_order`
- `(book_id, author_order) -> author_id`

### `copies`

Attribute sind `book_id`, `copy_number`, `barcode`, `branch_code`, `acquired_on` und `condition`. `book_id` und `copy_number` bilden den Primary Key. `book_id` verweist auf `books.id`. `barcode` ist ein Alternativschlüssel. Zweigstelle und Zustand haben feste Wertemengen.

Funktionale Abhängigkeiten:

- `(book_id, copy_number) -> barcode, branch_code, acquired_on, condition`
- `barcode -> book_id, copy_number, branch_code, acquired_on, condition`

`copies` ist der Kandidat für eine schwache Entität. Die Exemplarnummer ist nur innerhalb eines Buchs eindeutig.

### `reservations`

Attribute sind `id`, `member_id`, `book_id`, `reserved_on`, `expires_on` und `status`. `id` ist der Primary Key. `member_id` verweist auf `members.id`, `book_id` auf `books.id`. Mitglied, Buch und Vormerkdatum bilden zusammen einen Alternativschlüssel. Das Ablaufdatum darf nicht vor dem Vormerkdatum liegen.

Funktionale Abhängigkeiten:

- `id -> member_id, book_id, reserved_on, expires_on, status`
- `(member_id, book_id, reserved_on) -> id, expires_on, status`

### `loans`

Attribute sind `id`, `member_id`, `book_id`, `copy_number`, `reservation_id`, `loaned_on`, `due_on` und `returned_on`. `id` ist der Primary Key. `member_id` verweist auf `members.id`. `book_id` und `copy_number` verweisen gemeinsam auf `copies`. `reservation_id` ist optional und eindeutig. Der zusammengesetzte Foreign Key aus `reservation_id`, `member_id` und `book_id` verhindert die Zuordnung einer fremden Vormerkung. Ein partieller eindeutiger Index erlaubt pro Exemplar höchstens eine Ausleihe ohne Rückgabedatum.

Funktionale Abhängigkeiten:

- `id -> member_id, book_id, copy_number, reservation_id, loaned_on, due_on, returned_on`
- `reservation_id -> id, member_id, book_id, copy_number, loaned_on, due_on, returned_on`, sofern `reservation_id` nicht `NULL` ist

`loans` löst die Beziehung zwischen Mitglied und Exemplar auf. Ausleihtag, Fälligkeit und Rückgabedatum sind Attribute dieser Beziehung.

## Annahmen zu den offenen Fragen

Die Referenzlösung behandelt eine Vormerkung als Wunsch nach einem Buchtitel. Erst die Ausleihe ordnet ein physisches Exemplar zu. Eine gut begründete Vormerkung für ein bestimmtes Exemplar ist ebenfalls akzeptabel, braucht aber andere Foreign Keys.

Die Leihfrist beträgt in beiden Zweigstellen 28 Tage. Die Zweigstelle ändert daran nichts. Abweichende Regeln pro Zweigstelle sind möglich, verlangen aber eine eigene Modellierung der Regeln.

Das Kursmodell speichert nur das aktuelle Fälligkeitsdatum. Es führt keine Verlängerungshistorie. Wer Verlängerungen nachvollziehbar als eigene Entität oder Historientabelle modelliert, löst die Unklarheit ebenfalls sauber.

## Dateien

- `er.svg` zeigt das Referenzmodell in Crow's-Foot-Notation.
- `../sql/schema.sql` enthält das SQLite-Schema.
- `../sql/seed.sql` enthält 10 bis 20 Zeilen pro Tabelle.
- `../sql/queries.sql` löst die acht SQL-Aufgaben.
