# C3: Tests und Fehler

Zeitbox: 45 Minuten für POST und Fehlervertrag, danach 35 Minuten für die Tests. Die Vertiefung beginnt erst nach beiden Blöcken.

## Eingang

Arbeitet im Projekt `../c2-spring-resource/starter`. `requests.http` enthält GET- und POST-Requests. Die POST-Methode mit OpenAPI-Annotationen, Validierung und `Location` ist bereits im Controller vorhanden.

Starttest:

```bash
cd ../c2-spring-resource/starter && ./gradlew test
```

## Kernauftrag

Implementiert `insert`, `existsByCourseCode` und prüft die vorhandene POST-Methode. Aktiviert den Controller-Test. Prüft 201, einen Validierungsfehler mit 400 und den doppelten `course_code` mit 409. Der Fehlerkörper enthält `code`, `message`, `correlationId` und bei Validierung `fields`.

## Vertiefung

Testet PUT oder DELETE und einen Fremdschlüsselverstoß. Technische Ausnahmedetails dürfen nie im HTTP-Response stehen.

## Vorbereiteter Zwischenstand

Der globale Handler, die Konfliktausnahme und der Korrelationsfilter sind vorbereitet. Der Filter übernimmt oder erzeugt `X-Correlation-ID` und hält sie im MDC. Ihr ergänzt Repository-Logik und konkrete Testfälle. Vergleicht 201, 400 und 409 samt Response-Modellen mit `/v3/api-docs`. Prüft bei 201 auch den `Location`-Header.

## Ausgang

Alle Tests laufen. `requests.http` reproduziert 201, 400 und 409 gegen die gestartete Anwendung.

Prüfbefehl:

```bash
cd ../c2-spring-resource/starter && ./gradlew test
```

## Auswertung

Welche Information braucht ein Client zur Reaktion auf den Fehler, und welche bleibt nur im Server-Log?
