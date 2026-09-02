# A2: Relationenmodell

Zeitbox: etwa 25 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

Nutzt euer ER-Modell aus `../a1-er-model/er.mmd` und die vorbereitete Relationszeile in `relations.sql`.

Starttest:

```bash
rg -n 'PK|UK|parcels' relations.sql
```

## Kernauftrag

Überführt jede Entität und Beziehung in eine Relation. Markiert PK, FK, UK und optionale Fremdschlüssel. Prüft jede n:m-Beziehung auf eine eigene Relation.

## Vertiefung

Notiert zu einem zusammengesetzten Schlüssel, warum keine künstliche ID nötig ist.

## Vorbereiteter Zwischenstand

Die erste Relation ist vorbereitet. Sie hält die Hauptentität über die nächsten Phasen stabil.

## Ausgang

`relations.sql` enthält für jede Relation einen Schlüssel und für jede Beziehung den passenden Fremdschlüssel.

Prüfbefehl:

```bash
rg -n 'PK|FK|UK' relations.sql
```

## Auswertung

Welcher Fremdschlüssel darf NULL sein, und welche fachliche Situation bedeutet das?
