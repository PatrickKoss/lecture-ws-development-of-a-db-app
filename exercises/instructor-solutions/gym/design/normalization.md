# Normalisierung

Der Buchungsexport verbindet Mitglied, Mitgliedschaft, Tarif, Kurs, Termin, Trainer und Raum. Durch die Mitgliedschaftshistorie lautet der Exportschlüssel `(membership_number, membership_start, course_code, session_date, start_time)`.

```text
membership_number -> member_name, email, joined_on, active
(membership_number, membership_start) -> plan_code, membership_end
plan_code -> plan_name, monthly_fee_cents, minimum_term_months
course_code -> title, level, duration_minutes, room_code
(course_code, session_date, start_time) -> trainer_number, maximum_participants, cancelled
trainer_number -> trainer_name, email, specialty
room_code -> room_name, capacity, floor
(membership_number, course_code, session_date, start_time) -> booked_on, attended
```

Mitglied, Kurs und Termin hängen von echten Teilmengen des Exportschlüssels ab. Trainer- und Raumdaten hängen transitiv. Bei einer Kapazitätsänderung des Raums müsste jede Buchungszeile dieses Raums geändert werden.

Die Zerlegung trennt `members(membership_number, ...)` ab. `membership_number` ist die Schnittmenge mit der Restrelation und bestimmt die gesamte Mitgliederprojektion. Der Schritt ist verlustfrei. Kurse, Termine, Trainer, Räume, Tarife und Mitgliedschaften werden danach mit ihrem jeweiligen Determinanten abgetrennt.

Der Export ist als vollständiger Verbund der genannten Projektionen definiert. Treffen zu einem Elternsatz mehrere unabhängige Kindmengen zusammen, enthält der Export jede zugehörige Kombination. Diese Join-Abhängigkeit ist für das verlustfreie Zurückverbinden der Kindprojektionen nötig und folgt nicht allein aus den funktionalen Abhängigkeiten.

Der Beweis gilt für vorhandene Buchungszeilen. Ein Kurs ohne Termin oder ein Mitglied ohne Buchung lässt sich aus dem Buchungsexport nicht rekonstruieren, kann im A2-Modell aber unabhängig existieren. Kurse ohne Stammraum werden mit nullable `room_id` und LEFT JOIN dargestellt.

Für jede nicht triviale Abhängigkeit `X -> A` verlangt 3NF: `X` ist ein Superschlüssel oder `A` ist ein Primattribut. Bei allen Abhängigkeiten außer der folgenden BCNF-Falle ist der Determinant ein Kandidaten- oder Superschlüssel. In der Ausnahme ist die rechte Seite prim. Damit erfüllt das Ergebnis in `relations.sql` die formale 3NF-Bedingung.

Die Lehrannahme `level -> duration_minutes` verletzt in `courses` die BCNF. `level` ist kein Superschlüssel, `duration_minutes` ist aber Teil des Alternativschlüssels `(title, duration_minutes)`. `course_levels(level, duration_minutes)` wäre die BCNF-Zerlegung.
