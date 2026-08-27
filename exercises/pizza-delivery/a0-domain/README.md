# A0: Domänenidee

Zeitbox: etwa 15 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

Startet mit der Domänengeschichte im Gruppen-README und `domain-notes.txt`.

Starttest:

```bash
test -f domain-notes.txt
```

## Kernauftrag

Einigt euch auf fünf bis acht Fachbegriffe. Beschreibt `Pizza` in einem Satz und notiert eine offene Annahme, die euer Modell verändert.

## Vertiefung

Formuliert zwei Geschäftsregeln mit konkreten Mengen oder Zuständen.

## Vorbereiteter Zwischenstand

Die Datei enthält bereits feste Felder. Wenn ihr stockt, füllt nur Hauptentität, Problem und offene Annahme aus.

## Ausgang

`domain-notes.txt` enthält eure Begriffe und genau eine markierte Annahme.

Prüfbefehl:

```bash
rg -n 'Hauptentität|Offene Annahme|Begriffe' domain-notes.txt
```

## Auswertung

Welche Annahme würde bei einer anderen Entscheidung euer Datenmodell ändern?
