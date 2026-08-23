# 2. Normalisierung

> Zeitbox: 40 Minuten

## Ausgangstabelle

Der Empfang überträgt die Zettel aus den Kursräumen in "Kursanmeldungen.xlsx". Eine Zeile steht für die Buchung eines Mitglieds. Deshalb kommt derselbe Kurstermin für jedes angemeldete Mitglied erneut vor.

| Buchung | Mitgliedsnr. | Mitglied | E-Mail | Tarifcode | Tarif | Beitrag | Mitglied seit | Mitglied bis | Kurscode | Kurs | Niveau | Dauer | Raum | Raumname | Trainer-Nr. | Trainer | Kurstag | Beginn | Gebucht am | Teilnahme |
| --- | --- | --- | --- | --- | --- | ---: | --- | --- | --- | --- | --- | ---: | --- | --- | --- | --- | --- | --- | --- | --- |
| B-26001 | M-2001 | Lena Fischer | lena.fischer@example.org | FLEX | Flex 12 | 49,90 | 2026-01-05 |  | C-101 | Functional Basics | Einsteiger | 45 | KR-1 | Kursraum 1 | T-01 | Sarah König | 2026-09-01 | 18:00 | 2026-08-20 | ja |
| B-26002 | M-2002 | Mehmet Kaya | mehmet.kaya@example.org | PREMIUM | Premium Plus | 79,90 | 2026-02-01 |  | C-101 | Functional Basics | Einsteiger | 45 | KR-1 | Kursraum 1 | T-01 | Sarah König | 2026-09-01 | 18:00 | 2026-08-21 | ja |
| B-26003 | M-2003 | Sophie Wagner | sophie.wagner@example.org | STUDENT | Campus Fit | 34,90 | 2026-03-01 |  | C-101 | Functional Basics | Einsteiger | 45 | KR-1 | Kursraum 1 | T-01 | Sarah König | 2026-09-01 | 18:00 | 2026-08-22 | nein |
| B-26004 | M-2001 | Lena Fischer | lena.fischer@example.org | FLEX | Flex 12 | 49,90 | 2026-01-05 |  | C-102 | Yoga Flow | Einsteiger | 45 | YOGA | Yogaraum | T-02 | Miriam Scholz | 2026-09-03 | 17:30 | 2026-08-24 | ja |
| B-26005 | M-2004 | Jonas Becker | jonas.becker@example.org | BASIC | Basis | 29,90 | 2026-01-12 |  | C-102 | Yoga Flow | Einsteiger | 45 | YOGA | Yogaraum | T-02 | Miriam Scholz | 2026-09-03 | 17:30 | 2026-08-25 | ja |
| B-26006 | M-2002 | Mehmet Kaya | mehmet.kaya@example.org | PREMIUM | Premium Plus | 79,90 | 2026-02-01 |  | C-103 | Indoor Cycling | Mittel | 60 | BIKE | Cyclingraum | T-03 | Daniel Krüger | 2026-09-05 | 10:00 | 2026-08-26 | ja |
| B-26007 | M-2005 | Aylin Demir | aylin.demir@example.org | MORNING | Frühstarter | 39,90 | 2026-04-01 |  | C-103 | Indoor Cycling | Mittel | 60 | BIKE | Cyclingraum | T-03 | Daniel Krüger | 2026-09-05 | 10:00 | 2026-08-27 | ja |
| B-26008 | M-2006 | Felix Schulte | felix.schulte@example.org | FLEX | Flex 12 | 49,90 | 2026-02-15 |  | C-103 | Indoor Cycling | Mittel | 60 | BIKE | Cyclingraum | T-03 | Daniel Krüger | 2026-09-05 | 10:00 | 2026-08-28 | nein |
| B-26009 | M-2007 | Nina Roth | nina.roth@example.org | WEEKEND | Wochenende | 32,90 | 2026-05-01 |  | C-103 | Indoor Cycling | Mittel | 60 | BIKE | Cyclingraum | T-03 | Daniel Krüger | 2026-09-05 | 10:00 | 2026-08-29 | ja |
| B-26010 | M-2003 | Sophie Wagner | sophie.wagner@example.org | STUDENT | Campus Fit | 34,90 | 2026-03-01 |  | C-104 | HIIT Express | Mittel | 60 | KR-1 | Kursraum 1 | T-04 | Patrick Wolf | 2026-09-07 | 19:00 | 2026-08-30 | ja |
| B-26011 | M-2008 | David Nguyen | david.nguyen@example.org | CORPORATE | Firmenfitness | 44,90 | 2026-06-01 |  | C-104 | HIIT Express | Mittel | 60 | KR-1 | Kursraum 1 | T-04 | Patrick Wolf | 2026-09-07 | 19:00 | 2026-08-31 | ja |
| B-26012 | M-2009 | Laura Yilmaz | laura.yilmaz@example.org | FAMILY | Familie | 69,90 | 2026-01-20 |  | C-104 | HIIT Express | Mittel | 60 | KR-1 | Kursraum 1 | T-04 | Patrick Wolf | 2026-09-07 | 19:00 | 2026-09-01 | nein |
| B-26013 | M-2004 | Jonas Becker | jonas.becker@example.org | BASIC | Basis | 29,90 | 2026-01-12 |  | C-105 | Rückenfit | Einsteiger | 45 | YOGA | Yogaraum | T-05 | Anna Lorenz | 2026-09-09 | 09:00 | 2026-09-02 | ja |
| B-26014 | M-2007 | Nina Roth | nina.roth@example.org | WEEKEND | Wochenende | 32,90 | 2026-05-01 |  | C-107 | Langhantel Pro | Fortgeschritten | 75 | KRAFT | Kraftzone | T-07 | Robert Seidel | 2026-09-12 | 11:00 | 2026-09-04 | ja |

Für den Kurskatalog gelten zwei zusätzliche Regeln. Jedes Niveau legt genau eine Dauer fest. Außerdem identifizieren Kurstitel und Dauer zusammen einen Katalogeintrag, auch wenn der Kurscode ebenfalls eindeutig ist.

## Beobachtungen

- Wie kann das Studio "Mobility Online" und seinen ersten Termin erfassen, bevor sich jemand dafür anmeldet?
- Welche Zeilen muss der Empfang ändern, wenn Lena Fischer eine neue E-Mail-Adresse meldet?
- Welche Angaben über "Langhantel Pro", Robert Seidel und die Kraftzone verschwinden, wenn die einzige Buchung B-26014 gelöscht wird?

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
