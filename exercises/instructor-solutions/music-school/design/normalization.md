# Normalisierung

Der Kursprogramm-Export hat den Schlüssel `(course_code, student_email, instrument_code)`. Er bildet das Kreuzprodukt aus Anmeldungen und Instrumentanforderungen.

```text
course_code -> title, fee, level, staff_code, room_code
staff_code -> teacher_name
room_code -> room_capacity
student_email -> student_name
(course_code, student_email) -> enrolled_at, enrollment_status
instrument_code -> instrument_name
(course_code, instrument_code) -> quantity_required
```

Kurstitel, Schüler und Instrument hängen nur von Teilen des Exportschlüssels ab. Lehrkraft und Raum hängen transitiv vom Kurscode ab. Wird Raum `R-204` auf acht Plätze erweitert, müsste der Export jede Zeile dieses Raums ändern.

Die Zerlegung trennt zuerst `teachers(staff_code, teacher_name)` ab. `staff_code` ist die Schnittmenge mit der verbleibenden Exportrelation und bestimmt die Lehrkraftprojektion. Der Schritt ist verlustfrei. Räume, Schüler, Instrumente, Kurse und beide Zuordnungen werden danach mit dem jeweiligen Determinanten abgetrennt.

Der Export ist als vollständiger Verbund der genannten Projektionen definiert. Treffen zu einem Elternsatz mehrere unabhängige Kindmengen zusammen, enthält der Export jede zugehörige Kombination. Diese Join-Abhängigkeit ist für das verlustfreie Zurückverbinden der Kindprojektionen nötig und folgt nicht allein aus den funktionalen Abhängigkeiten.

Der Beweis gilt für vorhandene Kursprogrammzeilen. Ein Schüler ohne Anmeldung und ein Instrument ohne Kursanforderung lässt sich aus diesem Export nicht rekonstruieren, bleibt im A2-Modell aber unabhängig speicherbar. Ein Online-Kurs ohne Raum wird mit nullable `room_id` und LEFT JOIN dargestellt.

Für jede nicht triviale Abhängigkeit ist der Determinant nach der Zerlegung ein Kandidaten- oder Superschlüssel. Deshalb erfüllen alle Relationen die formale 3NF-Bedingung. Das Ergebnis entspricht `relations.sql`.

Jeder Determinant ist in seiner entstandenen Relation ein Kandidatenschlüssel. Alle Relationen erfüllen deshalb auch BCNF.
