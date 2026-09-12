# Normalisierung

Der Scannerexport hat den Schlüssel `(tracking_code, event_number, attempt_id)` und enthält Paket, Absender, Start- und Zieldepot, Statusmeldung und Zustellversuch.

```text
tracking_code -> parcel_id, recipient, weight, origin_depot_code, destination_depot_code, sender_email
depot_code -> depot_city
sender_email -> sender_name
(tracking_code, event_number) -> status, recorded_at, scan_depot_code
attempt_id -> tracking_code, attempted_at, outcome
```

Paketdaten, Statusmeldung und Zustellversuch hängen nur von Teilen des Exportschlüssels ab. Depotstadt und Absendername hängen transitiv. Eine Umbenennung des Depots würde sonst viele Scannerzeilen ändern und kann widersprüchliche Städtenamen erzeugen.

Beim Abtrennen von `depots(depot_code, depot_city)` ist `depot_code` die Schnittmenge mit der Restrelation und bestimmt die Depotprojektion. Dieser binäre Schritt ist verlustfrei. Absender, Pakete, Statusmeldungen und Zustellversuche werden danach mit ihren jeweiligen Determinanten abgetrennt.

Der Export ist als vollständiger Verbund der genannten Projektionen definiert. Treffen zu einem Elternsatz mehrere unabhängige Kindmengen zusammen, enthält der Export jede zugehörige Kombination. Diese Join-Abhängigkeit ist für das verlustfreie Zurückverbinden der Kindprojektionen nötig und folgt nicht allein aus den funktionalen Abhängigkeiten.

Der Beweis gilt für vorhandene Scannerexportzeilen. Ein Paket ohne Statusmeldung oder ein Depot ohne Paket kommt im Export nicht vor und kann daraus nicht rekonstruiert werden. Das A2-Modell speichert solche Zeilen unabhängig. Statusmeldungen ohne Depotscan werden über nullable `depot_id` und einen LEFT JOIN dargestellt.

Nach der Zerlegung ist bei jeder nicht trivialen Abhängigkeit der Determinant ein Kandidaten- oder Superschlüssel. Damit erfüllen alle Relationen 3NF. Das Ergebnis entspricht `relations.sql`.

Jeder Determinant ist in seiner entstandenen Relation ein Kandidaten- oder Superschlüssel. Das Modell erfüllt damit auch BCNF.
