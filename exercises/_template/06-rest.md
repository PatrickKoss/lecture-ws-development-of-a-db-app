# 6. REST API

> Zeitbox: 125 Minuten, davon 25 Minuten für den API-Entwurf, 45 Minuten für den ersten laufenden Stand und 55 Minuten für CRUD und Fehlerfälle. Der Beziehungsendpunkt ist freiwillige Zusatzarbeit

## Ausgangspunkt

Arbeitet im Projekt `java/rest-simple-exercise`. Für den Unterricht bearbeitet ihr die Phasen 1 bis 3 aus dessen README. Phase 4 ist nicht Teil dieser Aufgabe. Die Beispiele verwenden `Student`; ihr setzt dieselben Schritte für eure eigene Domäne um.

<!-- TODO(domain): Nenne die Hauptressource mit Basis-URL und eine zweite Ressource für den Beziehungsendpunkt. Nenne außerdem die fachliche Richtung der Beziehung. -->

## Aufgabe

### API auf Papier

<!-- TODO(domain): Ergänze eine feste Tabelle mit den verlangten CRUD-Endpunkten der Hauptressource und einem Beziehungsendpunkt. Trage Methode und Pfad ein. Lass Request, Response und Statuscodes von der Gruppe bestimmen. -->

| Methode | Pfad | Request | Response | Statuscodes |
| --- | --- | --- | --- | --- |
| <!-- TODO(domain): GET --> | <!-- TODO(domain): Sammlungspfad --> |  |  |  |
| <!-- TODO(domain): GET --> | <!-- TODO(domain): Einzelressource --> |  |  |  |
| <!-- TODO(domain): POST --> | <!-- TODO(domain): Sammlungspfad --> |  |  |  |
| <!-- TODO(domain): PUT --> | <!-- TODO(domain): Einzelressource --> |  |  |  |
| <!-- TODO(domain): DELETE --> | <!-- TODO(domain): Einzelressource --> |  |  |  |
| <!-- TODO(domain): GET oder POST --> | <!-- TODO(domain): Beziehungspfad --> |  |  |  |

### Phasen 1 bis 3 umsetzen

1. Legt ein einfaches Model und ein Response-DTO für die Hauptressource an. Startet mit einer festen Liste und einem `GET`-Endpunkt.
2. Fügt eine Flyway-Migration, eine JPA-Entity und ein `JpaRepository` hinzu. Stellt vollständiges CRUD über Request- und Response-DTOs bereit.
3. Ergänzt Bean Validation, `@Valid`, eine fachliche Eindeutigkeitsregel und eine passende Exception.
4. Verschiebt die Fachlogik in einen `@Service` mit Transaktionsgrenzen. Der Controller behandelt nur HTTP-Belange.
5. Freiwillige Zusatzarbeit: implementiert den geplanten Beziehungsendpunkt.

## Vorgaben

<!-- TODO(domain): Nenne 3 bis 5 konkrete Validierungsregeln, darunter Pflichtfeld, Länge oder Wertebereich und ein eindeutiges fachliches Attribut. -->

<!-- TODO(domain): Nenne mindestens drei konkrete Fehlerfälle und die erwarteten HTTP-Statuscodes. Decke 400, 404 und 409 ab. -->

<!-- TODO(domain): Formuliere zwei Testideen, eine für einen erfolgreichen Aufruf und eine für Validierung oder Fehlerbehandlung. -->

## Abgabe

- das ausführbare Spring-Projekt mit den Phasen 1 bis 3 für die eigene Domäne
- Liste der Endpunkte mit Methoden und Statuscodes
- Screenshot der Endpunkte in Swagger UI
- Ergebnisse der zwei beschriebenen Tests
