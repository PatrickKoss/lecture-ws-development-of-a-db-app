# Normalisierung

Der Inventarexport hat den Schlüssel `(inventory_code, artist_id, role, loan_id)` und enthält Exponat, Galerie, Urheberbeteiligung und Leihgabe.

```text
inventory_code -> exhibit_id, title, insured_value, gallery_id
gallery_id -> gallery_name, gallery_floor
artist_id -> artist_name, birth_year
-- Die Beteiligungsrelation hat nur Schlüsselattribute und keine nicht triviale FD.
loan_id -> exhibit_id, borrower, starts_on, ends_on
```

Exponat, Urheber und Leihgabe hängen nur von Teilen des Exportschlüssels ab. Galeriedaten hängen transitiv über `gallery_id`. Wird die Galerie "Stadtgeschichte" umbenannt, müsste jede zugeordnete Exportzeile geändert werden.

Die Zerlegung trennt `galleries(gallery_id, ...)` ab. `gallery_id` ist die Schnittmenge mit der Restrelation und bestimmt die gesamte Galerieprojektion. Der Schritt ist verlustfrei. Exponate, Urheber und Leihgaben werden danach mit `inventory_code`, `artist_id` und `loan_id` als Determinanten abgetrennt.

Der Export ist als vollständiger Verbund der genannten Projektionen definiert. Treffen zu einem Elternsatz mehrere unabhängige Kindmengen zusammen, enthält der Export jede zugehörige Kombination. Diese Join-Abhängigkeit ist für das verlustfreie Zurückverbinden der Kindprojektionen nötig und folgt nicht allein aus den funktionalen Abhängigkeiten.

Die Aussage gilt für vorhandene Inventarexportzeilen. Ein Urheber ohne Beteiligung und ein Exponat ohne Leihgabe lässt sich aus den betreffenden Exportspalten nicht rekonstruieren. Das A2-Modell darf solche Zeilen trotzdem speichern. Exponate ohne Galerie werden über nullable `gallery_id` und einen LEFT JOIN dargestellt.

In jeder entstandenen Relation ist der Determinant einer nicht trivialen Abhängigkeit ein Kandidaten- oder Superschlüssel. Damit erfüllen die Relationen 3NF. Das Ergebnis entspricht `relations.sql`.

In jeder entstandenen Relation ist jeder aufgeführte Determinant ein Kandidaten- oder Superschlüssel. Das Modell erfüllt daher auch BCNF.
