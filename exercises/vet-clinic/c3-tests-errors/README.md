# C3: Schreiben, Fehler und Tests

Zeitbox: 45 Minuten für POST und Fehlervertrag, danach 35 Minuten für die Tests. Die Vertiefung beginnt erst nach beiden Blöcken.

## Eingang

Arbeitet weiter in `../c2-spring-resource/starter`. `requests.http` enthält GET- und POST-Requests. Controller, Bean Validation, Service, globaler Fehlerhandler und Korrelationsfilter sind vorbereitet.

Aus C2 funktionieren `findAll` und `findById` im JPA-Adapter. Offen sind `save` und `existsByPzn` in `JpaMedicationRepository`. `SQLiteConstraintTranslator` übersetzt einen UNIQUE-Verstoß, der trotz vorheriger Konfliktprüfung beim Speichern auftreten kann.

Starttest:

```bash
cd ../c2-spring-resource/starter && ./gradlew test
```

## Kernauftrag

1. Implementiert `existsByPzn(String)` über die vorbereitete abgeleitete Methode in `SpringDataMedicationRepository`.
2. Implementiert `save(Medication)`. Mappt auf `MedicationJpaEntity` und verwendet `saveAndFlush`, damit ein Datenbankfehler innerhalb des Repository-Aufrufs auftritt. Übergibt die Ausnahme an `SQLiteConstraintTranslator`.
3. Aktiviert `MedicationApiExerciseTest`.
4. Prüft 201 samt `Location`-Header, 400 samt Feldfehlern und 409 für ein doppeltes `pzn`. Der Fehlerkörper enthält `code`, `message`, `correlationId` und bei Validierungsfehlern `fields`.
5. Führt `requests.http` gegen die gestartete Anwendung aus und vergleicht die Antworten mit `/v3/api-docs`.

## Vertiefung

Implementiert PUT mit `existsByPznAndIdNot(String, long)`. Für ein idempotentes DELETE verwendet ihr `deleteById(long)` und `flush`. Ergänzt für jede neue Operation einen API-Test und den OpenAPI-Vertrag.

## Vorbereiteter Zwischenstand

Der Service prüft den fachlichen Schlüssel vor dem Speichern. Der Übersetzer fängt zusätzlich den UNIQUE-Verstoß der Datenbank ab. Der Korrelationsfilter übernimmt eine eingehende `X-Correlation-ID` oder erzeugt eine neue und schreibt sie in Response und MDC.

## Ausgang

Alle aktivierten Tests laufen. `requests.http` reproduziert 201, 400 und 409. Weder JPA-Entity noch technische Ausnahmedetails erscheinen im HTTP-Response.

Prüfbefehl:

```bash
cd ../c2-spring-resource/starter && ./gradlew test
```

## Auswertung

Warum reicht die Abfrage mit `existsByPzn` allein nicht als Schutz vor Duplikaten? Welche Information braucht ein Client, und welche gehört nur ins Server-Log?
