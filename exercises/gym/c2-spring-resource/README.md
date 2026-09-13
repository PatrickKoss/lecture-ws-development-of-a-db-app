# C2: GET vom Controller bis JPA

Zeitbox: etwa 50 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

Arbeitet mit eurem Stand aus C1 weiter. Die Request- und Response-Felder sowie der HTTP-Vertrag sind ergänzt; `OpenApiContractExerciseTest` ist aktiv.

`starter/` ist ein eigenständiges Spring-Boot-Projekt. Flyway enthält das Schema und die Seed-Daten. Hibernate erzeugt kein Schema, weil `ddl-auto=none` gesetzt ist.

`Course` ist der Domänen-Record. `CourseJpaEntity` bildet die Tabelle für JPA ab. `SpringDataCourseRepository` und die Infrastruktur sind vorbereitet. Der Controller, der Service und `JpaCourseRepository` enthalten die TODOs für beide GET-Wege. `JpaLookupRepository` liest `trainers`.

Starttest:

```bash
cd starter && ./gradlew test
```

Der Startstand baut grün. Die Lookup-Prüfung läuft bereits. Die Methoden in `JpaRepositoryExerciseTest` und `ReadApiExerciseTest` sind deaktiviert, bis der jeweilige Checkpoint fertig ist.

## Kernauftrag

Arbeitet zuerst die Liste vollständig durch. So könnt ihr einen Weg testen, bevor GET nach ID dazukommt.

1. Implementiert `CourseResponse.from(Course)`.
2. Implementiert `findAll` in `JpaCourseRepository`. Nutzt das Spring-Data-Repository, sortiert nach ID und mappt jede `CourseJpaEntity` auf `Course`.
3. Implementiert `findAll` im Service und danach im Controller. Der Controller mappt Domänenobjekte auf `CourseResponse`.
4. Aktiviert `readsSortedSeedRows` in `JpaRepositoryExerciseTest` und `readsSeedData` in `ReadApiExerciseTest`. Führt die Tests aus, startet die Anwendung und ruft die GET-Liste auf.
5. Implementiert `findById(long)` im Adapter mit `Optional`.
6. Implementiert den Service-Lookup. Eine unbekannte ID wird zur vorbereiteten Not-found-Ausnahme.
7. Implementiert GET nach ID im Controller. Aktiviert `readsKnownIdAndReportsMissingId` in `JpaRepositoryExerciseTest` und führt die Tests aus. Prüft eine bekannte und eine unbekannte ID zusätzlich über Swagger UI oder `requests.http`; den automatisierten HTTP-404-Test schreibt ihr in C3.

`containsExactly` prüft in `JpaRepositoryExerciseTest` bereits die Seed-Werte und ihre Reihenfolge. Ihr müsst diese Assertion nicht neu schreiben.

## Vorbereiteter Zwischenstand

Übernehmt eure B1-Dateien als `V1__schema.sql` und `V2__seed.sql`. Falls dieser Stand nicht läuft, gibt die Lehrperson die Referenzmigrationen frei. Flyway bleibt die einzige Schemaquelle. Hikari aktiviert SQLite-Fremdschlüssel für jede Verbindung.

## Ausgang

Beide GET-Endpunkte lesen über Service, Repository-Interface und JPA-Adapter. Der Controller gibt `CourseResponse` zurück. Eine unbekannte ID ergibt 404. Die aktivierten Repository- und API-Checkpoints laufen.

Prüfbefehl:

```bash
cd starter && ./gradlew test
```

Danach könnt ihr die Anwendung separat starten:

```bash
SERVER_PORT=18081 ./gradlew bootRun
```

## Auswertung

Wo wird zwischen JPA-Entity, Domänenobjekt und Response übersetzt? Welche Schicht entscheidet über 404? Warum implementiert ihr einen Endpunkt erst vollständig, bevor der zweite folgt?
