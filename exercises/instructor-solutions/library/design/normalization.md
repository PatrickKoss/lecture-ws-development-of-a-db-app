# Normalisierung

Der Ausleihexport enthält Mitglied, Buch, Autoren, Exemplar, Vormerkung und Ausleihe. Durch die wiederholten Autoren wird eine Zeile mit `(loan_id, author_id)` identifiziert.

```text
loan_id -> membership_number, book_id, copy_number, reservation_id, loaned_on, due_on, returned_on
membership_number -> member_name, email, joined_on, active
book_id -> isbn, title, publication_year, subject_area, shelf_code
isbn -> book_id, title, publication_year, subject_area, shelf_code
author_id -> author_name, birth_year
(book_id, author_id) -> author_order
(book_id, copy_number) -> barcode, branch_code, acquired_on, condition
reservation_id -> membership_number, book_id, reserved_on, expires_on, status
```

Ausleih-, Buch- und Autorendaten hängen nur von Teilen des Exportschlüssels ab. Mitglieder und Exemplare hängen transitiv. Wenn sich eine E-Mail-Adresse ändert, müsste der Export jede frühere Ausleihe dieses Mitglieds ändern.

Die Zerlegung trennt zuerst `members(membership_number, ...)` ab. `membership_number` ist die Schnittmenge mit der Restrelation und bestimmt die Mitgliederprojektion. Der binäre Zerlegungsschritt ist deshalb verlustfrei. Dasselbe Kriterium gilt anschließend für Bücher, Autoren, Exemplare, Vormerkungen und Ausleihen mit ihren jeweiligen Determinanten.

Der Export ist als vollständiger Verbund der genannten Projektionen definiert. Treffen zu einem Elternsatz mehrere unabhängige Kindmengen zusammen, enthält der Export jede zugehörige Kombination. Diese Join-Abhängigkeit ist für das verlustfreie Zurückverbinden der Kindprojektionen nötig und folgt nicht allein aus den funktionalen Abhängigkeiten.

Der Beweis bezieht sich auf vorhandene Ausleihexportzeilen. Ein Buch ohne Exemplar oder ein Mitglied ohne Ausleihe kommt im Export nicht vor und lässt sich daraus nicht rekonstruieren. Das A2-Modell speichert solche Stammdaten trotzdem. Ausleihen ohne Vormerkung werden über nullable `reservation_id` und einen LEFT JOIN dargestellt.

Für jede nicht triviale Abhängigkeit `X -> A` verlangt 3NF: `X` ist ein Superschlüssel oder `A` ist ein Primattribut. Bei allen Abhängigkeiten außer der folgenden BCNF-Falle ist der Determinant ein Kandidaten- oder Superschlüssel. In der Ausnahme ist die rechte Seite prim. Damit erfüllt das Ergebnis in `relations.sql` die formale 3NF-Bedingung.

Die Kursannahme `subject_area -> shelf_code` verletzt BCNF. `subject_area` ist kein Superschlüssel, `shelf_code` gehört aber zum Alternativschlüssel `(title, shelf_code)`. Eine BCNF-Fassung trennt `subject_shelves(subject_area, shelf_code)` ab.
