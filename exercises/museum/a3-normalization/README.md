# A3: Normalisierung

Zeitbox: etwa 35 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

Diese Phase bearbeitet ihr wieder in zwei Teilteams. Thema A nutzt `../a2-relational-model/relations.sql`. Thema B startet mit der Exportrelation in `functional-dependencies.txt` und dem ER-Modell aus A1. Die Datei hält eure Begründung fest.

Starttest:

```bash
rg -n 'Ausgangsrelation|Funktionale Abhängigkeiten|3NF' functional-dependencies.txt
```

## Kernauftrag

Wählt eine breite Exportrelation. Benennt die bestimmenden Attributmengen, zeigt eine konkrete Änderungsanomalie und zerlegt verlustfrei bis 3NF. Vergleicht das Ergebnis mit A2.

## Vertiefung

Prüft eine Relation auf BCNF. Für BCNF muss jeder Determinant ein Superschlüssel sein. Die Pflichtaufgabe endet bei 3NF.

## Vorbereiteter Zwischenstand

Wenn die Gruppe festhängt, übernimmt sie die Hauptrelation aus A2 und untersucht nur ein wiederholtes Nachschlageattribut.

## Ausgang

`functional-dependencies.txt` nennt Abhängigkeiten, Anomalie und jede Zerlegungsentscheidung.

Prüfbefehl:

```bash
rg -n '->|3NF|Anomalie' functional-dependencies.txt
```

## Auswertung

Welche Redundanz verschwindet durch eure wichtigste Zerlegung?
