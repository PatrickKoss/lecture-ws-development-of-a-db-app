# 2. Normalisierung

> Zeitbox: 40 Minuten

## Ausgangstabelle

Die Rezeption führt "Buchungen-2026.xlsx" und überträgt die Zimmerzettel am Monatsende. Eine Zeile steht für eine Zusatzleistung innerhalb einer Buchung, die Zimmerdaten werden dabei wiederholt. Deshalb kann dieselbe Buchung mehrfach vorkommen.

| Buchung | Gast-Nr. | Gast | E-Mail | Firma | Etage | Zimmer | Reinigungsbereich | Typ-Code | Zimmertyp | Anreise | Abreise | Leistung-Nr. | Leistung | Leistung am | Menge | Einzelpreis |
| --- | --- | --- | --- | --- | ---: | ---: | --- | --- | --- | --- | --- | --- | --- | --- | ---: | ---: |
| B-26001 | G-1001 | Julia Neumann | julia.neumann@example.org |  | 1 | 12 | Rhein | DZ | Doppelzimmer | 2026-01-12 | 2026-01-15 | S-01 | Frühstücksbuffet | 2026-01-13 | 2 | 18.00 |
| B-26001 | G-1001 | Julia Neumann | julia.neumann@example.org |  | 1 | 12 | Rhein | DZ | Doppelzimmer | 2026-01-12 | 2026-01-15 | S-02 | Tiefgaragenplatz | 2026-01-12 | 3 | 16.00 |
| B-26002 | G-1002 | Cem Aydin | cem.aydin@example.org | Rheinwerk AG | 2 | 12 | Hafen | EZ | Einzelzimmer | 2026-01-20 | 2026-01-22 | S-01 | Frühstücksbuffet | 2026-01-21 | 2 | 18.00 |
| B-26003 | G-1003 | Hannah Berger | hannah.berger@example.org |  | 3 | 18 | Medienhafen | JS | Junior Suite | 2026-02-03 | 2026-02-06 | S-03 | Spa-Tageskarte | 2026-02-04 | 2 | 35.00 |
| B-26003 | G-1003 | Hannah Berger | hannah.berger@example.org |  | 3 | 18 | Medienhafen | JS | Junior Suite | 2026-02-03 | 2026-02-06 | S-04 | Minibar Klassik | 2026-02-04 | 1 | 24.00 |
| B-26004 | G-1004 | David Özdemir | david.oezdemir@example.org | Messebau West GmbH | 1 | 15 | Rhein | DZ | Doppelzimmer | 2026-02-21 | 2026-02-25 | S-02 | Tiefgaragenplatz | 2026-02-21 | 4 | 16.00 |
| B-26005 | G-1005 | Lea Hoffmann | lea.hoffmann@example.org |  | 2 | 15 | Hafen | FA | Familienzimmer | 2026-03-06 | 2026-03-09 | S-01 | Frühstücksbuffet | 2026-03-07 | 4 | 18.00 |
| B-26005 | G-1005 | Lea Hoffmann | lea.hoffmann@example.org |  | 2 | 16 | Hafen | EZ | Einzelzimmer | 2026-03-07 | 2026-03-09 | S-01 | Frühstücksbuffet | 2026-03-08 | 2 | 18.00 |
| B-26006 | G-1006 | Murat Yilmaz | murat.yilmaz@example.org |  | 3 | 21 | Medienhafen | SU | Rhein Suite | 2026-03-18 | 2026-03-20 | S-05 | Late Check-out | 2026-03-20 | 1 | 30.00 |
| B-26007 | G-1002 | Cem Aydin | cem.aydin@example.org | Rheinwerk AG | 2 | 12 | Hafen | EZ | Einzelzimmer | 2026-04-13 | 2026-04-16 | S-01 | Frühstücksbuffet | 2026-04-14 | 3 | 19.00 |
| B-26007 | G-1002 | Cem Aydin | cem.aydin@example.org | Rheinwerk AG | 2 | 12 | Hafen | EZ | Einzelzimmer | 2026-04-13 | 2026-04-16 | S-06 | Wäscheservice | 2026-04-15 | 2 | 12.00 |
| B-26008 | G-1007 | Sofia Rossi | sofia.rossi@example.org |  | 1 | 18 | Rhein | DZ | Doppelzimmer | 2026-05-04 | 2026-05-08 | S-03 | Spa-Tageskarte | 2026-05-05 | 1 | 38.00 |
| B-26009 | G-1008 | Noah Schneider | noah.schneider@example.org | Klee Consulting | 3 | 15 | Medienhafen | JS | Junior Suite | 2026-06-15 | 2026-06-18 | S-02 | Tiefgaragenplatz | 2026-06-15 | 3 | 17.00 |
| B-26009 | G-1008 | Noah Schneider | noah.schneider@example.org | Klee Consulting | 3 | 15 | Medienhafen | JS | Junior Suite | 2026-06-15 | 2026-06-18 | S-01 | Frühstücksbuffet | 2026-06-16 | 3 | 19.00 |

Für die Zimmer gelten zwei zusätzliche Regeln. Jede Etage gehört genau zu einem Reinigungsbereich. Außerdem identifizieren Zimmernummer und Reinigungsbereich zusammen ein Zimmer, auch wenn Etage und Zimmernummer ebenfalls eindeutig sind.

## Beobachtungen

- Wie kann das Hotel Zimmer 22 auf Etage 3 erfassen, bevor die erste Buchung dafür eingeht?
- Welche Zeilen müssen Mitarbeitende ändern, wenn Cem Aydin eine neue E-Mail-Adresse meldet?
- Welche Angaben über die Junior Suite 315 verschwinden, wenn die einzige Buchung B-26009 gelöscht wird?

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
