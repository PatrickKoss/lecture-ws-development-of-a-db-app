# C3: POST, Fehler und eigene Tests

Zeitbox: 45 Minuten für POST und Fehlervertrag, danach 35 Minuten für die Tests. Die Vertiefung beginnt erst nach beiden Blöcken.

## Eingang

Arbeitet weiter in `../c2-spring-resource/starter`. Aus C2 funktionieren beide GET-Wege. Der globale Fehlerhandler, der Korrelationsfilter und `SQLiteConstraintTranslator` sind vorbereitet. `requests.http` enthält Beispielaufrufe.

Starttest:

```bash
cd ../c2-spring-resource/starter && ./gradlew test
```

## Regeln für den Request

- partnerNumber: Text, 1 bis 40 Zeichen, nicht leer
- Name und street: Text, 1 bis 100 Zeichen, nicht leer
- postalCode: genau fünf Ziffern
- city: Text, 1 bis 60 Zeichen, nicht leer
- commissionRate: Dezimalzahl von 0 bis 100
- active: Wahrheitswert, erforderlich

Alle Request-Komponenten sind erforderlich. Texte dürfen nicht leer sein. Setzt die genannten Grenzen und Formate mit Bean Validation um. `@Schema` beschreibt die Felder, prüft sie aber nicht zur Laufzeit.

## Kernauftrag: POST und Fehlervertrag

1. Ergänzt die Validierungsannotationen in `CreateRestaurantRequest` und implementiert `toCommand()`. Die ID gehört nicht in den Request.
2. Implementiert `existsByPartnerNumber(String)` und `save(Restaurant)` in `JpaRestaurantRepository`. Nutzt die vorbereitete Spring-Data-Methode. Mappt beim Speichern auf `RestaurantJpaEntity`, verwendet `saveAndFlush` und reicht Datenbankfehler an `SQLiteConstraintTranslator` weiter.
3. Implementiert `create` im Service. Prüft `partnerNumber` vor dem Speichern und werft bei einem Duplikat die vorbereitete Konfliktausnahme.
4. Implementiert POST im Controller. Validiert den Request, ruft den Service auf und liefert 201 mit dem gespeicherten `RestaurantResponse` sowie einem `Location`-Header auf die neue Ressource.
5. Aktiviert in `CreateApiExampleTest` die bereitgestellten Fälle `createsResource` und `reportsValidationWithCorrelationId`. Sie zeigen einen erfolgreichen POST sowie den 400-Fehlervertrag. Führt danach alle Tests aus.

## Kernauftrag: zwei eigene Tests

Schreibt die Assertions selbst. Kopiert nicht nur den Beispieltest.

1. Implementiert und aktiviert in `ReadApiExerciseTest` den TODO-Test `reportsUnknownId` für eine unbekannte ID. Prüft Status 404 sowie `code`, `message` und `correlationId`.
2. Implementiert und aktiviert in `CreateApiExampleTest` den TODO-Test `rejectsDuplicateBusinessKey` für ein doppeltes `partnerNumber`. Legt den Konflikt über zwei Requests oder mit den Seed-Daten an. Prüft Status 409 und den Fehlerkörper.
3. Prüft zusätzlich mit `requests.http` einen ungültigen Request. Erwartet 400 und mindestens einen Eintrag unter `fields`.

## Vertiefung

Implementiert PUT oder DELETE vollständig über DTO, Controller, Service, Repository-Interface, JPA-Adapter und Spring-Data-Repository. Ergänzt den OpenAPI-Vertrag und mindestens einen API-Test. Für PUT braucht ihr eine Duplikatprüfung, die die aktuelle ID ausnimmt. Für DELETE legt ihr fest und testet, wie ein wiederholter Aufruf antwortet.

## Ausgang

GET und POST laufen durch alle Schichten. POST liefert 201 mit `Location`. Validation liefert 400, ein doppeltes `partnerNumber` liefert 409 und eine unbekannte ID 404. Die beiden bereitgestellten Beispiele und eure beiden Tests laufen.

Prüfbefehl:

```bash
cd ../c2-spring-resource/starter && ./gradlew test
```

## Auswertung

Warum reicht die vorherige `existsByPartnerNumber`-Abfrage nicht als alleiniger Schutz vor Duplikaten? Welche Fehlerdetails braucht ein Client, und welche gehören nur ins Server-Log?
