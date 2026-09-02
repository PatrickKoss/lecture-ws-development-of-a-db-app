# A1: ER-Modell

Zeitbox: etwa 35 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

Diese Phase bearbeitet ihr in zwei Teilteams. Für Thema A übernehmt ihr Begriffe und Annahme aus `../a0-domain/domain-notes.txt`. Für Thema B startet ihr mit der Domänengeschichte in `../README.md`. `er.mmd` enthält jeweils die markierte Hauptentität.

Starttest:

```bash
rg -n 'erDiagram|BOOK' er.mmd
```

## Kernauftrag

Ergänzt vier bis sechs Entitäten, Primärschlüssel, Kardinalitäten und mindestens eine Beziehung mit eigenem Attribut. Lasst die Hauptentität im Mittelpunkt.

## Vertiefung

Ergänzt eine schwache Entität oder eine n:m-Beziehung und begründet ihren Schlüssel.

## Vorbereiteter Zwischenstand

Die Lehrperson kann nach 20 Minuten eine Kopie der Übersicht aus `../README.md` freigeben. Sie legt keine Kardinalitäten fest.

## Ausgang

`er.mmd` rendert und zeigt die Hauptentität, mindestens vier weitere Entitäten und alle Kardinalitäten.

Prüfbefehl:

```bash
npx --yes @mermaid-js/mermaid-cli -i er.mmd -o er.svg
```

## Auswertung

An welcher Beziehung wart ihr uneinig, und welche Geschäftsregel hat die Entscheidung geklärt?
