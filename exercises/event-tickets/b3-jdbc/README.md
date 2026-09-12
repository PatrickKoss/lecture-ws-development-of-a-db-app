# B3: JDBC

Zeitbox: etwa 45 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

`starter/` ist ein eigenständiges Java-21-Projekt. V1 und V2 enthalten den vollständigen Referenzstand aus B1/B2, dazu `Venue` und ein fertiges Read-Repository für `organizers`.

Starttest:

```bash
cd starter && ./gradlew test
```

## Kernauftrag

Implementiert `findById`, `findAll` und das sichtbare Row-Mapping in `JdbcVenueRepository`. Aktiviert danach den deaktivierten Repository-Test.

## Vertiefung

Ergänzt `insert` mit PreparedStatement und prüft den UNIQUE-Fehler für einen doppelten Wert in `venue_code`.

Hebt euren lauffähigen B3-Stand für B4 auf. Die JDBC-Implementierung bleibt dort erhalten. In B4 bereinigt ihr die öffentliche Schnittstelle `VenueRepository`, damit die aufrufende Schicht keine geprüften JDBC-Ausnahmen mehr kennt.

## Vorbereiteter Zwischenstand

Sichtbare Übergabe aus B1: Ersetzt `V1__schema.sql` und `V2__seed.sql` durch eure eigenen B1-Dateien. Falls dieser Stand nicht läuft, gibt die Lehrperson den eingebauten Referenzstand frei. `Database.open()` aktiviert Fremdschlüssel für jede Verbindung.

## Ausgang

Der Test liest mindestens drei `Spielort`-Datensätze. ID 1 enthält den aus B2 bekannten Wert `V-01`. Eine unbekannte ID liefert `Optional.empty()`.

Prüfbefehl:

```bash
cd starter && ./gradlew test
```

## Auswertung

Welche Spalte würde ohne euer explizites Mapping still im falschen Java-Feld landen?
