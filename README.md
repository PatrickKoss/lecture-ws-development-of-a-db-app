# Entwicklung einer Datenbankanwendung

Der Kurs entwickelt eine Hochschulverwaltung als gemeinsames Beispiel. Studierende, Lehrende, Lehrveranstaltungen und Belegungen bleiben vom ersten Fachsatz bis zum Frontend erhalten. Die Gruppenübungen wenden dieselben Schritte auf eigene Themen an.

## Voraussetzungen

- Java 21
- SQLite 3
- Node.js und npm für die Folien
- GNU Make

## Kursmaterial

- [`slides/`](slides/): Reveal.js-Folien und PDF-Export
- [`exercises/`](exercises/): gruppenspezifische Aufgaben und Codegerüste
- [`exam/`](exam/): Prüfungsaufgabe mit Projektbericht, Präsentation und REST-API
- [`common-example/`](common-example/): gemeinsame Aufgaben A0 bis C3 mit vollständigen Lösungen, Java-Backend und Frontend
- [`docs/schedule.md`](docs/schedule.md): Ablauf und Phasenkennungen
- [`docs/instructor-guide.md`](docs/instructor-guide.md): Hinweise für die Lehrperson

## Start

Folien installieren und öffnen:

```sh
make -C slides install
make -C slides run DECK=day1
```

Eine Gruppe startet in ihrem Domänenordner. Beispiel:

```sh
cd exercises/library
```

Die Gruppenaufgaben heißen A0 bis C3. Jeder Aufgabenordner nennt den benötigten Eingang, den Kernauftrag, Erweiterungen und einen Prüfbefehl. Ihre vollständigen Lösungen gehören zum separaten Lehrendenpaket und sind in diesem Branch nicht enthalten.

B3 vermittelt JDBC mit PreparedStatements und Row Mapping. B4 trennt den
Datenzugriff hinter einem Repository-Interface. Die Spring-Übungen C1 bis C3
bauen eine Ressource schrittweise auf. C1 erstellt DTOs und HTTP-Vertrag. C2
implementiert beide GET-Wege durch Controller, Service und JPA-Repository. C3
ergänzt Validation, POST, Konfliktregel und eigene API-Tests. Flyway verwaltet
das Schema; SpringDoc erzeugt die OpenAPI-Spec. Alle 13 Domänen und
`_template` haben passende Starter.

Die [gemeinsamen Aufgaben](common-example/exercises/README.md) bereiten jede Gruppenphase an der Hochschulverwaltung vor. Ihre Musterlösungen sind enthalten und bleiben für Studierende zugänglich.

Vor dem Kurs können Lehrpersonen das gemeinsame Beispiel prüfen:

```sh
make -C common-example check-sql
make -C common-example build-core
make -C common-example build-starters
npm --prefix common-example/frontend ci
make -C common-example frontend-test
make -C slides lint
```

Tag 2 und Tag 3 lassen sich als gemeinsamer Implementierungsblock durchführen. Die Phasenkennungen und Übergabepunkte ändern sich dabei nicht.

Die Prüfung wird aus der Markdown-Datei als PDF gebaut:

```sh
make -C exam pdf
```
