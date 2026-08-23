# 2. Normalisierung

> Zeitbox: 40 Minuten

## Ausgangstabelle

Die Mitarbeitenden führen "Ausleihen.xlsx" an beiden Theken und übertragen Angaben von der Papierliste für Vormerkungen nachträglich. Eine Zeile steht für einen Autor eines ausgeliehenen Buchs. Deshalb kann dieselbe Ausleihe mehrfach vorkommen.

| Ausleihe | Mitgliedsnr. | Mitglied | E-Mail | ISBN | Titel | Sachgebiet | Regal | Exemplar | Zweigstelle | Autor-Nr. | Autor | Ausleihe am | Fällig am | Rückgabe am | Vormerkung |
| --- | --- | --- | --- | --- | --- | --- | --- | ---: | --- | --- | --- | --- | --- | --- | --- |
| L-26001 | M-1001 | Julia Neumann | julia.neumann@example.org | 978-3-446-27989-9 | Tschick | Roman | R-12 | 1 | Nordstadt | A-01 | Wolfgang Herrndorf | 2026-01-08 | 2026-02-05 | 2026-01-29 | V-2601 |
| L-26002 | M-1002 | Cem Aydin | cem.aydin@example.org | 978-3-442-76161-5 | Momo | Kinderbuch | K-04 | 1 | Südstadt | A-02 | Michael Ende | 2026-01-10 | 2026-02-07 | 2026-02-09 |  |
| L-26003 | M-1001 | Julia Neumann | julia.neumann@example.org | 978-3-462-05081-5 | Herkunft | Roman | R-12 | 1 | Südstadt | A-03 | Saša Stanišić | 2026-01-14 | 2026-02-11 |  | V-2602 |
| L-26004 | M-1003 | Hannah Berger | h.berger@example.org | 978-3-404-17121-1 | Der Schwarm | Thriller | T-07 | 1 | Nordstadt | A-04 | Frank Schätzing | 2026-01-17 | 2026-02-14 | 2026-02-12 |  |
| L-26005 | M-1004 | David Özdemir | david.oezdemir@example.org | 978-3-550-08164-8 | QualityLand | Satire | S-03 | 1 | Nordstadt | A-05 | Marc-Uwe Kling | 2026-01-21 | 2026-02-18 |  | V-2603 |
| L-26006 | M-1005 | Lea Hoffmann | lea.hoffmann@example.org | 978-3-446-19313-3 | Die Vermessung der Welt | Roman | R-12 | 2 | Südstadt | A-06 | Daniel Kehlmann | 2026-01-25 | 2026-02-22 | 2026-02-20 |  |
| L-26007 | M-1006 | Murat Yilmaz | murat.yilmaz@example.org | 978-3-423-21418-5 | Corpus Delicti | Roman | R-12 | 1 | Nordstadt | A-07 | Juli Zeh | 2026-02-02 | 2026-03-02 |  |  |
| L-26008 | M-1002 | Cem Aydin | cem.aydin@example.org | 978-3-446-26923-4 | Datenbanksysteme | Fachbuch | F-09 | 1 | Nordstadt | A-08 | Alfons Kemper | 2026-02-05 | 2026-03-05 |  | V-2604 |
| L-26008 | M-1002 | Cem Aydin | cem.aydin@example.org | 978-3-446-26923-4 | Datenbanksysteme | Fachbuch | F-09 | 1 | Nordstadt | A-09 | André Eickler | 2026-02-05 | 2026-03-05 |  | V-2604 |
| L-26009 | M-1007 | Sofia Rossi | sofia.rossi@example.org | 978-3-257-23000-9 | Das Parfum | Roman | R-12 | 1 | Südstadt | A-10 | Patrick Süskind | 2026-02-09 | 2026-03-09 |  |  |
| L-26010 | M-1008 | Noah Schneider | noah.schneider@example.org | 978-3-522-20280-2 | Die unendliche Geschichte | Kinderbuch | K-04 | 1 | Nordstadt | A-02 | Michael Ende | 2026-02-11 | 2026-03-11 | 2026-03-01 |  |
| L-26011 | M-1003 | Hannah Berger | h.berger@example.org | 978-3-446-27989-9 | Tschick | Roman | R-12 | 2 | Südstadt | A-01 | Wolfgang Herrndorf | 2026-02-14 | 2026-03-14 |  | V-2605 |
| L-26012 | M-1009 | Aylin Kaya | aylin.kaya@example.org | 978-3-499-26770-9 | Unterleuten | Roman | R-12 | 1 | Nordstadt | A-07 | Juli Zeh | 2026-02-18 | 2026-03-18 |  |  |
| L-26013 | M-1004 | David Özdemir | david.oezdemir@example.org | 978-3-442-71918-0 | Die Känguru-Chroniken | Satire | S-03 | 1 | Südstadt | A-05 | Marc-Uwe Kling | 2026-02-20 | 2026-03-20 |  | V-2606 |

Für den Katalog gelten zwei zusätzliche Regeln. Jedes Sachgebiet liegt genau in einem Regalcode. Außerdem identifizieren Titel und Regalcode zusammen einen Katalogeintrag, auch wenn die ISBN ebenfalls eindeutig ist.

## Beobachtungen

- Wie kann die Bibliothek "Momo" und sein erstes Exemplar erfassen, bevor jemand das Buch ausleiht?
- Welche Zeilen müssen Mitarbeitende ändern, wenn Julia Neumann eine neue E-Mail-Adresse meldet?
- Welche Angaben über "Die unendliche Geschichte" und ihr einziges Exemplar verschwinden, wenn die einzige Ausleihe L-26010 gelöscht wird?

## Aufgabe

1. Bestimmt einen sinnvollen Schlüssel der Ausgangstabelle.
2. Notiert die funktionalen Abhängigkeiten.
3. Zerlegt die Tabelle schrittweise in 1NF, 2NF und 3NF.
4. Prüft anschließend BCNF und beschreibt die gefundene Falle.
5. Vergleicht das Ergebnis mit eurem ER-Modell und korrigiert begründete Abweichungen.

## Abgabe

- Liste der normalisierten Tabellen mit Attributen
- Primary Keys und Foreign Keys jeder Tabelle
- funktionale Abhängigkeiten und ein kurzer Satz zur BCNF-Falle
