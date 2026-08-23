# 2. Normalisierung

> Zeitbox: 40 Minuten

## Ausgangstabelle

Die Mitarbeitenden übertragen die Durchschlagformulare am Monatsende in "Auftraege.xlsx". Eine Zeile steht für eine Teileposition. Deshalb kann derselbe Arbeitsauftrag mehrfach vorkommen.

| Auftrag | Kundennr. | Kunde | E-Mail | Kennzeichen | Fahrzeug | Teilenr. | Ersatzteil | Kategorie | Lagerfach | Pos. | Menge | Einzelpreis | Personalnr. | Mechaniker | Stunden | Eingang | Abschluss | Rechnung |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | ---: | ---: | ---: | --- | --- | ---: | --- | --- | --- |
| A-26001 | K-1001 | Julia Neumann | julia.neumann@example.org | GE-AB 123 | VW Golf VII | P-1001 | Ölfilter MANN W 712/95 | Filter | F-03 | 1 | 1 | 11,90 | ME-01 | Thomas Becker | 2,5 | 2026-01-08 | 2026-01-08 | R-26001 |
| A-26001 | K-1001 | Julia Neumann | julia.neumann@example.org | GE-AB 123 | VW Golf VII | P-1003 | Motoröl Castrol EDGE 5W-30 1 l | Betriebsstoff | B-01 | 2 | 5 | 13,50 | ME-01 | Thomas Becker | 2,5 | 2026-01-08 | 2026-01-08 | R-26001 |
| A-26002 | K-1002 | Cem Aydin | cem.aydin@example.org | GE-CD 456 | Opel Corsa E | P-1002 | Bremsbelagsatz Bosch 0 986 494 596 | Bremse | BR-02 | 1 | 1 | 64,90 | ME-02 | Nadine Scholz | 1,5 | 2026-01-10 | 2026-01-10 | R-26002 |
| A-26003 | K-1001 | Julia Neumann | julia.neumann@example.org | GE-AB 123 | VW Golf VII | P-1003 | Motoröl Castrol EDGE 5W-30 1 l | Betriebsstoff | B-01 | 1 | 5 | 13,50 | ME-03 | Mehmet Yilmaz | 1,0 | 2026-01-20 | 2026-01-20 | R-26003 |
| A-26003 | K-1001 | Julia Neumann | julia.neumann@example.org | GE-AB 123 | VW Golf VII | P-1004 | Zündkerze NGK 97153 | Zündung | Z-04 | 2 | 4 | 12,40 | ME-03 | Mehmet Yilmaz | 1,0 | 2026-01-20 | 2026-01-20 | R-26003 |
| A-26004 | K-1003 | Hannah Berger | hannah.berger@example.org | GE-EF 789 | Ford Focus | P-1001 | Ölfilter MANN W 712/95 | Filter | F-03 | 1 | 1 | 11,90 | ME-04 | Stefan Roth | 3,0 | 2026-02-01 | 2026-02-02 | R-26004 |
| A-26004 | K-1003 | Hannah Berger | hannah.berger@example.org | GE-EF 789 | Ford Focus | P-1005 | Luftfilter MAHLE LX 2046 | Filter | F-03 | 2 | 1 | 22,80 | ME-04 | Stefan Roth | 3,0 | 2026-02-01 | 2026-02-02 | R-26004 |
| A-26005 | K-1004 | David Özdemir | david.oezdemir@example.org | GE-GH 321 | BMW 320d | P-1006 | Starterbatterie Varta E11 | Elektrik | E-02 | 1 | 1 | 129,00 | ME-05 | Aylin Demir | 2,0 | 2026-02-05 |  |  |
| A-26006 | K-1005 | Lea Hoffmann | lea.hoffmann@example.org | GE-JK 654 | Mercedes-Benz A 180 | P-1004 | Zündkerze NGK 97153 | Zündung | Z-04 | 1 | 4 | 12,40 | ME-06 | Frank Nowak | 2,2 | 2026-02-09 | 2026-02-09 | R-26006 |
| A-26006 | K-1005 | Lea Hoffmann | lea.hoffmann@example.org | GE-JK 654 | Mercedes-Benz A 180 | P-1007 | Innenraumfilter Bosch 1 987 432 543 | Filter | F-03 | 2 | 1 | 18,70 | ME-06 | Frank Nowak | 2,2 | 2026-02-09 | 2026-02-09 | R-26006 |
| A-26007 | K-1001 | Julia Neumann | julia.neumann@example.org | GE-AB 123 | VW Golf VII | P-1002 | Bremsbelagsatz Bosch 0 986 494 596 | Bremse | BR-02 | 1 | 1 | 64,90 | ME-07 | Laura König | 4,0 | 2026-02-15 | 2026-02-16 | R-26007 |
| A-26008 | K-1006 | Murat Yilmaz | murat.yilmaz@example.org | GE-LM 987 | Skoda Octavia | P-1001 | Ölfilter MANN W 712/95 | Filter | F-03 | 1 | 1 | 11,90 | ME-08 | Daniel Krüger | 2,8 | 2026-02-18 | 2026-02-18 | R-26008 |
| A-26008 | K-1006 | Murat Yilmaz | murat.yilmaz@example.org | GE-LM 987 | Skoda Octavia | P-1008 | Keilrippenriemen CONTITECH 6PK1054 | Antrieb | A-05 | 2 | 1 | 24,60 | ME-08 | Daniel Krüger | 2,8 | 2026-02-18 | 2026-02-18 | R-26008 |
| A-26009 | K-1007 | Sofia Rossi | sofia.rossi@example.org | GE-NP 147 | Renault Clio | P-1009 | Wischerblatt Bosch Aerotwin A863S | Sicht | S-01 | 1 | 1 | 31,50 | ME-09 | Sarah Winkler | 0,7 | 2026-02-21 | 2026-02-21 | R-26009 |

Für den Teilekatalog gelten zwei zusätzliche Regeln. Jede Teilekategorie liegt genau in einem Lagerfach. Außerdem identifizieren Teilebezeichnung und Lagerfach zusammen einen Katalogeintrag, obwohl die Teilenummer ebenfalls eindeutig ist.

## Beobachtungen

- Wie kann die Werkstatt die Starterbatterie erfassen, bevor sie in einem Auftrag verbaut wird?
- Welche Zeilen müssen Mitarbeitende ändern, wenn Julia Neumann eine neue E-Mail-Adresse meldet?
- Welche Angaben über den Renault Clio verschwinden, wenn der einzige Auftrag A-26009 gelöscht wird?

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
