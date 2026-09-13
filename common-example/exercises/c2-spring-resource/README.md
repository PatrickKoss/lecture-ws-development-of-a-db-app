# C2: Studierende mit Spring und JPA lesen

Gemeinsame Vorbereitung im Vortragsblock; danach folgt die Gruppenaufgabe.

## Eingang

Arbeitet im Unterverzeichnis [`starter`](starter). Setzt den in C1 ergänzten und mit `OpenApiContractExerciseTest` geprüften DTO- und Vertragsstand fort. Die vollständige Umsetzung zum Vergleichen liegt in [`../../backend`](../../backend).

## Gemeinsame Arbeit

Implementiert die Lesekette in dieser Reihenfolge:

1. Bildet `Student` in `StudentResponse.from` ab.
2. Implementiert `JpaStudentRepository.findAll` sortiert nach ID.
3. Implementiert `StudentService.findAll` und die Listenmethode im Controller.
4. Aktiviert `JpaRepositoryExerciseTest.readsSortedSeedRows` und `ReadApiExerciseTest.readsSeedData`.
5. Implementiert `findById` im Repository und Service. Eine unbekannte ID löst `ResourceNotFoundException` aus.
6. Implementiert die Controller-Methode für eine ID und aktiviert `readsKnownIdAndReportsMissingId`.

Spring Data übernimmt die einfachen Datenbankoperationen. Der JPA-Adapter übersetzt zwischen `StudentJpaEntity` und dem Domainobjekt. Der Service entscheidet, dass eine leere `Optional`-Antwort fachlich ein 404 ist. Der Controller übersetzt nur zwischen HTTP-Typen und dem Service.

## Checkpoint

Führt im Starter `./gradlew build` aus. Entfernt jeweils nur das `@Disabled` der gerade fertiggestellten Methode.

Mit laufender Anwendung prüft `curl -i http://localhost:18081/api/students` die Liste und `curl -i http://localhost:18081/api/students/1` die Einzelressource. Ruft auch eine unbekannte ID manuell auf. Den automatisierten 404-Test schreibt ihr in C3.

## Musterlösung und Transfer

Vergleicht danach Adapter, Service und Controller mit [`../../backend`](../../backend). Implementiert dieselbe Reihenfolge in eurer Domäne, zuerst die Liste, danach die Einzelressource.
