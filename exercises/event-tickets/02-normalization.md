# 2. Normalisierung

> Zeitbox: 40 Minuten

## Ausgangstabelle

Die Abendkasse pflegt "Ticketverkauf.xlsx" und überträgt Kategorien vom Whiteboard. Eine Zeile steht für ein Ticket einer Bestellung. Deshalb kann dieselbe Bestellung mehrfach vorkommen.

| Bestellung | Kundennr. | Käufer | E-Mail | Veranstaltung | Titel | Art | Einlasscode | Spielort-Nr. | Spielort | Stadt | Datum | Kategorie | Listenpreis | Ticket | Bezahlt | Check-in |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | ---: | ---: | ---: | --- |
| B-26001 | K-1001 | Aylin Demir | aylin.demir@example.org | E-2601 | Nachtschicht am Kanal | Konzert | K | V-01 | Zeche Carl | Essen | 2026-03-14 | Standard | 34,00 | 1 | 34,00 | 2026-03-14T18:47:00 |
| B-26001 | K-1001 | Aylin Demir | aylin.demir@example.org | E-2601 | Nachtschicht am Kanal | Konzert | K | V-01 | Zeche Carl | Essen | 2026-03-14 | Standard | 34,00 | 2 | 34,00 | 2026-03-14T18:51:00 |
| B-26002 | K-1002 | Jonas Feld | jonas.feld@example.org | E-2601 | Nachtschicht am Kanal | Konzert | K | V-01 | Zeche Carl | Essen | 2026-03-14 | Premium | 52,00 | 1 | 49,00 | 2026-03-14T18:43:00 |
| B-26002 | K-1002 | Jonas Feld | jonas.feld@example.org | E-2601 | Nachtschicht am Kanal | Konzert | K | V-01 | Zeche Carl | Essen | 2026-03-14 | Premium | 52,00 | 2 | 52,00 |  |
| B-26003 | K-1003 | Miriam Scholz | miriam.scholz@example.org | E-2602 | Mord im Revier | Lesung | L | V-02 | Bahnhof Langendreer | Bochum | 2026-04-18 | Freie Platzwahl | 24,00 | 1 | 24,00 | 2026-04-18T18:18:00 |
| B-26004 | K-1004 | Cem Karaca | cem.karaca@example.org | E-2602 | Mord im Revier | Lesung | L | V-02 | Bahnhof Langendreer | Bochum | 2026-04-18 | Freie Platzwahl | 24,00 | 1 | 22,00 | 2026-04-18T18:22:00 |
| B-26004 | K-1004 | Cem Karaca | cem.karaca@example.org | E-2602 | Mord im Revier | Lesung | L | V-02 | Bahnhof Langendreer | Bochum | 2026-04-18 | Freie Platzwahl | 24,00 | 2 | 22,00 | 2026-04-18T18:24:00 |
| B-26005 | K-1005 | Nele Braun | nele.braun@example.org | E-2603 | Seitenwechsel | Lesung | L | V-03 | Ringlokschuppen | Mülheim | 2026-05-09 | Standard | 20,00 | 1 | 20,00 |  |
| B-26005 | K-1005 | Nele Braun | nele.braun@example.org | E-2603 | Seitenwechsel | Lesung | L | V-03 | Ringlokschuppen | Mülheim | 2026-05-09 | Premium | 32,00 | 2 | 30,00 |  |
| B-26006 | K-1006 | Luca Winter | luca.winter@example.org | E-2604 | Stromaufwärts | Konzert | K | V-01 | Zeche Carl | Essen | 2026-06-06 | Stehplatz | 29,00 | 1 | 29,00 |  |
| B-26006 | K-1006 | Luca Winter | luca.winter@example.org | E-2604 | Stromaufwärts | Konzert | K | V-01 | Zeche Carl | Essen | 2026-06-06 | Stehplatz | 29,00 | 2 | 29,00 |  |
| B-26006 | K-1006 | Luca Winter | luca.winter@example.org | E-2604 | Stromaufwärts | Konzert | K | V-01 | Zeche Carl | Essen | 2026-06-06 | Stehplatz | 29,00 | 3 | 27,00 |  |
| B-26007 | K-1007 | Sofia Nguyen | sofia.nguyen@example.org | E-2605 | Geschichten aus Stahl | Lesung | L | V-02 | Bahnhof Langendreer | Bochum | 2026-06-20 | Standard | 23,00 | 1 | 23,00 |  |
| B-26008 | K-1008 | David Mertens | david.mertens@example.org | E-2606 | Echo der Fördertürme | Konzert | K | V-03 | Ringlokschuppen | Mülheim | 2026-07-04 | Freie Platzwahl | 31,00 | 1 | 31,00 |  |

Für Veranstaltungen gelten zwei zusätzliche Regeln. Jede Veranstaltungsart hat genau einen Einlasscode. Außerdem identifizieren Titel und Einlasscode zusammen eine Veranstaltung, obwohl die Veranstaltungsnummer ebenfalls eindeutig ist.

## Beobachtungen

- Wie kann das Team "Stimmen im Maschinenhaus" mit seiner Kategorie "Standard" erfassen, bevor eine Bestellung eingeht?
- Welche Zeilen müssen Mitarbeitende ändern, wenn Aylin Demir eine neue E-Mail-Adresse meldet?
- Welche Angaben über "Echo der Fördertürme" und seine einzige verkaufte Karte verschwinden, wenn B-26008 gelöscht wird?

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
