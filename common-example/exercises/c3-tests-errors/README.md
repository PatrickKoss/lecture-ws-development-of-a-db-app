# C3: Studierende anlegen und Fehler testen

Gemeinsame Vorbereitung im Vortragsblock; danach folgt die Gruppenaufgabe.

## Eingang

Verwendet weiterhin [`../c2-spring-resource/starter`](../c2-spring-resource/starter). Die Musterlösung liegt in [`../../backend`](../../backend).

## Gemeinsame Arbeit

Ergänzt Bean Validation und `CreateStudentRequest.toCommand`. Namen dürfen nicht leer sein und haben höchstens 100 Zeichen. `email` muss eine E-Mail-Adresse sein. `studentNumber` folgt `M[0-9]{7}`. Implementiert danach `existsByStudentNumber`, `existsByEmail` und `save` im JPA-Adapter. Verwendet `saveAndFlush`, damit SQLite-Verstöße innerhalb des Adapters auftreten und dort übersetzt werden.

Der Service prüft die Matrikelnummer und E-Mail-Adresse. Er setzt `enrollmentDate` auf das heutige Datum. Implementiert zuletzt POST im Controller mit `201 Created`, `Location` und Response-Body.

`CreateApiExampleTest.createsResource` ist das bearbeitete Testbeispiel. Aktiviert es nach der Schreibkette. Schreibt anschließend selbst:

- `ReadApiExerciseTest.reportsUnknownId` für 404,
- `CreateApiExampleTest.rejectsDuplicateBusinessKey` für 409.

Der vorbereitete `reportsValidationWithCorrelationId` prüft 400 mit leerem JSON und fester `X-Correlation-ID`.

## Checkpoint

Wechselt mit `cd ../c2-spring-resource/starter` in das Projekt und führt `./gradlew build` aus. Vergleicht erst danach mit der Lösung. Achtet dort besonders auf die Stelle, an der das Serverdatum entsteht, und auf die Constraint-Übersetzung im Repository.

POST lässt sich auch mit `curl -i -X POST http://localhost:18081/api/students -H 'Content-Type: application/json' -d '{"firstName":"Ada","lastName":"Lovelace","email":"ada@campus.example","studentNumber":"M2026999"}'` prüfen.

## Transfer in eure Domäne

Übertragt die Schreibkette und die beiden selbst geschriebenen Fehlertests auf eure Hauptressource. Der fachliche Schlüssel eurer Domäne ersetzt dabei die Matrikelnummer.
