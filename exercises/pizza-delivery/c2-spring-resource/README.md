# C2: Spring-Ressource mit JPA

Zeitbox: etwa 50 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

`starter/` ist ein eigenständiges Spring-Boot-Projekt. Flyway enthält das vollständige Schema aus B1 und die Seed-Daten aus B2. Hibernate prüft das Mapping, erzeugt aber kein Schema, weil `ddl-auto=none` gesetzt ist.

Die fachliche Klasse `Pizza` bleibt ein unveränderlicher Java-Record. `PizzaJpaEntity` bildet die Tabelle für JPA ab. `SpringDataPizzaRepository` erweitert `JpaRepository` und ist fertig. Offen sind `findAll` und `findById` im Adapter `JpaPizzaRepository`. Das vorbereitete `JpaLookupRepository` liest `toppings`.

Starttest:

```bash
cd starter && ./gradlew test
```

`OpenApiStarterTest` läuft bereits. `JpaRepositoryExerciseTest` ist deaktiviert, bis ihr die beiden Lesemethoden implementiert habt.

## Kernauftrag

1. Implementiert `findAll` in `JpaPizzaRepository`. Nutzt das Spring-Data-Repository und mappt jede `PizzaJpaEntity` auf `Pizza`.
2. Implementiert `findById(long)` mit `Optional`, ohne einen unbekannten Datensatz durch `null` darzustellen.
3. Aktiviert `JpaRepositoryExerciseTest` und führt alle Tests aus.
4. Startet die Anwendung und prüft GET-Liste sowie GET nach ID über Swagger UI. Der Controller liefert `PizzaResponse`, keine JPA-Entity. Eine unbekannte ID ergibt 404.

## Vertiefung

Verfolgt die vorbereitete Lookup-Kette von `JpaLookupRepository` bis zur Lookup-Tabelle. Verschärft `JpaRepositoryExerciseTest`: Prüft die Seed-Labels mit `containsExactly` und damit auch die Sortierung nach ID.

## Vorbereiteter Zwischenstand

Übernehmt eure B1-Dateien als `V1__schema.sql` und `V2__seed.sql`. Falls dieser Stand nicht läuft, gibt die Lehrperson die Referenzmigrationen frei. Flyway bleibt die einzige Schemaquelle. Hikari aktiviert SQLite-Fremdschlüssel für jede Verbindung.

## Ausgang

Die Anwendung startet. Beide GET-Endpunkte liefern die Seed-Daten über das Response-DTO. ID 1 enthält `P-01`. `JpaRepositoryExerciseTest` und `OpenApiStarterTest` laufen.

Prüfbefehl:

```bash
cd starter && ./gradlew test
```

Danach könnt ihr die Anwendung separat starten:

```bash
SERVER_PORT=18081 ./gradlew bootRun
```

## Auswertung

Welche Aufgabe hat `PizzaJpaEntity`? Warum gibt der Controller trotzdem `PizzaResponse` zurück? Wo endet Spring Data und wo beginnt euer Adapter?
