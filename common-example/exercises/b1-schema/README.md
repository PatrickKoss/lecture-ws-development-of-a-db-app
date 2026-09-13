# B1: Schema und Seed-Daten

Zeitbox: etwa 20 Minuten vor der Gruppenphase.

## Eingang

Übertragt das Relationenmodell aus A2 und die Constraints aus A0 nach `starter/schema.sql`. Ergänzt danach `starter/seed.sql`.

Der Startcheck scheitert absichtlich, solange Tabellen und Daten fehlen:

```sh
python3 check.py
```

## Gemeinsame Arbeit

1. Erstellt die fünf Tabellen in Abhängigkeitsreihenfolge.
2. Setzt Primärschlüssel, Fremdschlüssel, `NOT NULL` und die fachlichen `UNIQUE`-Regeln.
3. Prüft positive Credit Points, den Notenbereich und ISO-Datumswerte mit `CHECK`.
4. Fügt kleine, zusammenhängende Seed-Daten ein.
5. Aktiviert SQLite-Fremdschlüssel und führt den Check erneut aus.

## Checkpoint

`python3 check.py` lädt den Starter in eine leere In-Memory-Datenbank. Der Check erwartet alle Tabellen, mindestens einen Datensatz je Tabelle, keine verwaisten Fremdschlüssel und vier abgewiesene Constraint-Verstöße.

## Lösung

Die vollständigen Dateien sind [schema.sql](../../sql/schema.sql) und [seed.sql](../../sql/seed.sql). Prüft die Lösung mit demselben Programm:

```sh
python3 check.py ../../sql/schema.sql ../../sql/seed.sql
```

Die Lösung erstellt zuerst Fachbereiche, danach Lehrende, Studierende, Kurse und zuletzt Belegungen. So zeigen alle Fremdschlüssel beim Einfügen bereits auf vorhandene Zeilen. Künstliche IDs bilden die Primärschlüssel. Die fachlichen Schlüssel bleiben mit `UNIQUE` geschützt. SQLite speichert die beiden Datumswerte als ISO-Text; die `CHECK`-Ausdrücke weisen andere Formate ab.

## Transfer zur eigenen Domäne

Bearbeitet vom Repository-Root `exercises/<domain>/b1-schema/`. Übernehmt die Reihenfolge und die Art der Checks. Tabellen- und Feldnamen kommen aus eurem eigenen Relationenmodell.
