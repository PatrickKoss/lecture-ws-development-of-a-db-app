# Musterlösung für C1

Die OpenAPI-Beschreibung entsteht aus `MusicCourseController`,
`CreateMusicCourseRequest` und `MusicCourseResponse` unter `../spring/src/`.
`@Operation` und `@ApiResponse` beschreiben die Endpunkte und Statuscodes.
`@Schema` ergänzt Feldbeschreibungen und Beispiele. Bean Validation liefert
die Grenzen und Pflichtfelder, die SpringDoc aus dem Code ableiten kann.

Der Request enthält die vom Client gesetzten Felder `courseCode`, `title` und
`fee`. Die Response ergänzt die vom Server erzeugte `id`. Die Trennung
verhindert, dass ein Client beim Anlegen eine ID vorgibt.

Ein erfolgreicher POST antwortet mit `201 Created`, dem angelegten Datensatz und
einem `Location`-Header. Ein leerer Kurscode oder eine negative Gebühr ist
ungültige Eingabe und ergibt 400. Ein syntaktisch gültiger, aber bereits
belegter Kurscode kollidiert mit dem Datenbestand und ergibt 409.

Der Fehlerkörper hat einen stabilen maschinenlesbaren `code`. `message` ist für
Menschen gedacht. `correlationId` verbindet die Antwort mit dem Server-Log.
Technische Exception-Texte gehören nicht in die Response.

Zum Prüfen zuerst die Musterlösung in den C2-Starter kopieren:

```bash
cp -R ../instructor-solutions/music-school/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew test
SERVER_PORT=18081 ./gradlew bootRun
```

Danach zeigt `http://localhost:18081/swagger-ui.html` die Dokumentation. Der
JSON-Export liegt unter `http://localhost:18081/v3/api-docs`, der YAML-Export
unter `/v3/api-docs.yaml`. Beide werden von der laufenden Anwendung erzeugt
und nicht von Hand bearbeitet.
