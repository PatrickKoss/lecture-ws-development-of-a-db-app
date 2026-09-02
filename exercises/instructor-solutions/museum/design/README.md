# Musterlösung Museum

Das Referenzmodell trennt Exponate, Galerien, Urheber und Leihgaben. `exhibit_artists` löst die n:m-Beziehung zwischen Exponaten und Urhebern auf. Das Attribut `role` gehört zu dieser Beziehung.

## Funktionale Abhängigkeiten

- `inventory_code -> title, insured_value, gallery_id`
- `gallery_id -> gallery_name, gallery_floor`
- `artist_id -> artist_name, birth_year`
- `(exhibit_id, artist_id, role)` ist der Schlüssel von `exhibit_artists`.
- `loan_id -> exhibit_id, borrower, starts_on, ends_on`

Die breite Exportrelation wiederholt Galerienamen und Etage für jedes Exponat. Eine Umbenennung der Galerie müsste viele Zeilen ändern. Die Zerlegung in `galleries` und `exhibits` entfernt diese Änderungsanomalie.

`gallery_id` darf in `exhibits` leer sein. Ein neu erfasstes oder verliehenes Exponat muss noch keinen Ausstellungsort haben.

## Dateien

- `er.mmd` ist das Referenzmodell in Mermaid.
- `er.svg` ist die gerenderte Fassung für den Debrief.
- `../sql/` enthält Schema, Seed-Daten und geprüfte Abfragen.
