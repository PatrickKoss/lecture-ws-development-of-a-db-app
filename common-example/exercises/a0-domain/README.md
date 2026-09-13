# A0: Domäne und Hauptentität

Zeitbox: etwa 10 Minuten vor der Gruppenphase.

## Eingang

Startet mit dem Satz: "Studierende belegen Lehrveranstaltungen, die von Lehrenden angeboten werden." Öffnet `starter/domain-notes.md`.

## Gemeinsame Arbeit

1. Sammelt die Fachbegriffe, ohne sofort Tabellen zu zeichnen.
2. Wählt `Student` als Hauptentität für den späteren Programmierteil.
3. Trennt den Studienbeginn `enrollment_date` vom Datum einer Kursbelegung `enrolled_on`.
4. Formuliert eine Regel zur Eindeutigkeit und eine offene Modellentscheidung.

## Checkpoint

Die Notizen nennen mindestens fünf Fachbegriffe, die Hauptentität, beide Datumsbedeutungen und eine offene Entscheidung.

```sh
rg -n 'Student|enrollment_date|enrolled_on|Offene Entscheidung' starter/domain-notes.md
```

## Lösung

Vergleicht eure Begriffe und Annahmen mit [der ausgearbeiteten Domänenbeschreibung](../../design/domain.md). Die Lösung legt auch die Eindeutigkeitsregeln fest, die B1 später als Constraints umsetzt.

## Transfer zur eigenen Domäne

Öffnet jetzt vom Repository-Root `exercises/<domain>/a0-domain/`. Übertragt die Methode, nicht die Hochschulbegriffe: Hauptentität wählen, Fachbegriffe klären und eine Entscheidung markieren, die das Modell ändern kann.
