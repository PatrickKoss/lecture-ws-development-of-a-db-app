# Essens-Lieferplattform

> Zeitbox: drei Kurstage

Die regionale Plattform "RuhrLiefert" vermittelt Bestellungen von 30 Restaurants an Kuriere. Bestellungen laufen heute über WhatsApp-Gruppen, die Abrechnung steckt in einer Excel-Datei. Die Gründer wollen Bestellungen, Kuriere und Bewertungen in einem System verwalten und die Provision jedes Restaurants berechnen.

## Aufgabe

Entwickelt eure Domäne über drei Tage von der Beschreibung bis zur REST API. Haltet Entscheidungen und offene Annahmen in den jeweiligen Aufgabendateien fest.

Als erste Orientierung kommen Restaurant, Gericht, Kunde, Bestellung, Bestellposition, Kurier und Bewertung infrage. Prüft diese Auswahl gegen eure fachlichen Entscheidungen. Die Bestellposition setzt die Beziehung zwischen Bestellung und Gericht um und zählt im Referenzmodell nicht als zusätzliche eigenständige Entität.

## Abgabe

### Tag 1

- Foto oder SVG des ER-Diagramms
- Liste der normalisierten Tabellen mit Primary Keys und Foreign Keys

### Tag 2

- `sql/schema.sql`, `sql/seed.sql` und `sql/queries.sql`
- das bearbeitete JDBC-Projekt

### Tag 3

- das bearbeitete Spring-Projekt
- Screenshot der eigenen API in Swagger UI

Öffnet `solutions/` erst nach der gemeinsamen Auswertung.
