# A1: ER-Modell

Zeitbox: etwa 20 Minuten vor der Gruppenphase.

## Eingang

Nutzt die Begriffe aus A0. `starter/er.mmd` enthält nur `STUDENT`.

## Gemeinsame Arbeit

1. Ergänzt Fachbereich, Lehrperson, Kurs und Belegung als Entitäten.
2. Entscheidet für jede Beziehung Minimum und Maximum auf beiden Seiten.
3. Modelliert Student zu Kurs als n:m-Beziehung über `ENROLLMENT`.
4. Ordnet `enrolled_on` und `grade` der Belegung zu.
5. Markiert Primärschlüssel, Fremdschlüssel und fachlich eindeutige Attribute.

## Checkpoint

Das Diagramm hat fünf Entitäten und vier Beziehungen. `ENROLLMENT` verbindet genau einen Studenten mit genau einem Kurs.

```sh
rg -n 'DEPARTMENT|LECTURER|STUDENT|COURSE|ENROLLMENT' starter/er.mmd
npx --yes @mermaid-js/mermaid-cli -i starter/er.mmd -o starter/er.svg
```

## Lösung

Öffnet [das vollständige ER-Diagramm](../../design/er.mmd) und begründet jede Kardinalität mit einer Geschäftsregel aus [A0](../../design/domain.md). Ein bloßer Vergleich der Linien reicht nicht.

## Transfer zur eigenen Domäne

Bearbeitet vom Repository-Root `exercises/<domain>/a1-er-model/`. Sucht dort ebenfalls nach einer n:m-Beziehung oder einer Beziehung mit eigenen Attributen.
