# B1: Schema und Seed-Daten

Zeitbox: etwa 35 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

Übertragt A2 und A3 nach `starter/schema.sql`. `starter/seed.sql` enthält nur einen technischen Checkpoint.

Starttest:

```bash
sqlite3 --version && ./check.sh
```

## Kernauftrag

Erstellt die Haupttabelle und die für eure Beziehungen nötigen Tabellen. Setzt PK, FK, NOT NULL, UNIQUE und zwei fachliche CHECK-Constraints. Fügt zunächst drei Datensätze pro Tabelle ein.

## Vertiefung

Testet je einen Verstoß gegen UNIQUE und FOREIGN KEY und notiert die Fehlermeldung.

## Vorbereiteter Zwischenstand

Falls der Kern nach 20 Minuten nicht läuft, gibt die Lehrperson `instructor-solutions/music-school/sql/schema.sql` frei. Übernehmt nur die Tabellen, die ihr für `Kursangebot` braucht.

## Ausgang

`starter/schema.sql` und `starter/seed.sql` laufen in einer leeren SQLite-Datenbank. `PRAGMA foreign_key_check` bleibt leer.

Prüfbefehl:

```bash
./check.sh
```

## Auswertung

Welches Constraint verhindert einen fachlich falschen Datensatz, den ein Datentyp allein erlauben würde?
