# C1: HTTP-Vertrag für Studierende

Gemeinsame Vorbereitung im Vortragsblock; danach folgt die Gruppenaufgabe.

## Eingang

Die vollständige Musterlösung liegt in [`../../backend`](../../backend). Zum eigenen Üben arbeitet ihr in [`../c2-spring-resource/starter`](../c2-spring-resource/starter). Der Starter wird von C1 bis C3 weiterverwendet.

## Gemeinsame Arbeit

Ergänzt zuerst `StudentResponse`: `Long id`, viermal `String` für `firstName`, `lastName`, `email` und `studentNumber` sowie `LocalDate enrollmentDate`. Ergänzt `CreateStudentRequest` mit den vier String-Feldern. Das Einschreibedatum gehört nicht in den Request. Der Server setzt es beim Anlegen. Markiert die Response-ID in OpenAPI als nur lesbar.

Dokumentiert danach `GET /api/students`, `GET /api/students/{id}` und `POST /api/students` mit OpenAPI. POST liefert bei Erfolg `201 Created`, einen `Location`-Header und die neue Darstellung. Dokumentiert auch 400, 404 und 409.

## Checkpoint

Wechselt zuerst mit `cd ../c2-spring-resource/starter` in das Projekt. `./gradlew build` muss von Beginn an laufen. `OpenApiStarterTest` ruft keine offene Methode auf. Aktiviert danach den vorbereiteten `OpenApiContractExerciseTest`.

Startet die Anwendung mit `SERVER_PORT=18081 ./gradlew bootRun` und öffnet `http://localhost:18081/swagger-ui.html`. Ein erster manueller Aufruf ist `curl -i http://localhost:18081/api/students/health`.

In der Musterlösung könnt ihr nachvollziehen, warum Request, Response, Domainobjekt und Command getrennte Typen sind. Der HTTP-Vertrag bestimmt nicht die Form der Datenbankklasse.

## Transfer in eure Domäne

Übertragt Feldtypen, Beispiele und Statuscodes auf eure Hauptressource. Prüft dabei ausdrücklich, welche Felder der Client senden darf und welche der Server vergibt.
