# Normalisierung

Der Geräteexport hat den Schlüssel `(resource_code, allocation_sequence, inspection_sequence)` und enthält Ressource, Kategorie, Standort, Verantwortlichen, Ausgabe und Prüfung.

```text
resource_code -> resource_id, resource_name, measure, category_id, current_location_id
category_id -> category_name
current_location_id -> location_code, location_name
(resource_code, allocation_sequence) -> personnel_code, allocated_at, returned_at
personnel_code -> custodian_name, email
(resource_code, inspection_sequence) -> inspected_on, result
```

Ressourcen-, Ausgabe- und Prüfungsdaten hängen nur von Teilen des Exportschlüssels ab. Kategorie, Standort und Verantwortlicher hängen transitiv. Eine Umbenennung des Standorts würde sonst jede Exportzeile der dort gelagerten Ressourcen ändern.

Die Zerlegung folgt den aufgeführten Abhängigkeiten. Beim Abtrennen von `reference_items(category_id, category_name)` ist `category_id` die Schnittmenge mit der Restrelation und bestimmt die gesamte abgetrennte Relation. Nach dem Kriterium für binäre Zerlegungen ist dieser Schritt verlustfrei. Dasselbe Argument gilt nacheinander für Standorte, Verantwortliche, Ressourcen, Ausgaben und Prüfungen.

Der Export ist als vollständiger Verbund der genannten Projektionen definiert. Treffen zu einem Elternsatz mehrere unabhängige Kindmengen zusammen, enthält der Export jede zugehörige Kombination. Diese Join-Abhängigkeit ist für das verlustfreie Zurückverbinden der Kindprojektionen nötig und folgt nicht allein aus den funktionalen Abhängigkeiten.

Die Aussage bezieht sich auf die Zeilen des Exports. Ein Verantwortlicher ohne Ausgabe kommt darin nicht vor und lässt sich daraus auch nicht rekonstruieren. Das A2-Modell darf solche eigenständigen Stammdaten trotzdem speichern. Optionale Standortzuordnungen werden über `resources.current_location_id` und einen LEFT JOIN dargestellt.

Nach der Zerlegung ist bei jeder nicht trivialen funktionalen Abhängigkeit der Determinant ein Kandidaten- oder Superschlüssel. Damit erfüllen die Relationen 3NF. Das Ergebnis entspricht `relations.sql`.

In jeder entstandenen Relation ist jeder aufgeführte Determinant ein Kandidaten- oder Superschlüssel. Das Modell erfüllt daher auch BCNF.
