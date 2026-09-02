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
- [`common-example/`](common-example/): gemeinsames Hochschulbeispiel mit Java-Backend und Frontend
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

Die Aufgaben heißen A0 bis C3. Jeder Aufgabenordner nennt den benötigten Eingang, den Kernauftrag, Erweiterungen und einen Prüfbefehl. Die vollständigen Lösungen liegen getrennt unter `exercises/instructor-solutions/` und gehören nicht zur Teilnehmerausgabe.

Vor dem Kurs können Lehrpersonen das gemeinsame Beispiel prüfen:

```sh
make -C common-example build-all
make -C common-example test-all
make -C common-example frontend-test
make -C slides lint
```

Tag 2 und Tag 3 lassen sich als gemeinsamer Implementierungsblock durchführen. Die Phasenkennungen und Übergabepunkte ändern sich dabei nicht.
