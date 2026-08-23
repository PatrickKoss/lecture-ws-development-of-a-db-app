# 2. Normalisierung

> Zeitbox: 40 Minuten

## Ausgangstabelle

Die Mitarbeitenden führen je Station ein Ausleihlogbuch. Nach einer Rückgabe schreiben sie die Angaben der Klebezettel am Rad ab. Eine Zeile steht für einen Wartungseintrag des ausgeliehenen Rads. Deshalb kann dieselbe Ausleihe mehrfach vorkommen.

| Ausleihe | Kundennr. | Kunde | E-Mail | Radnr. | Modellcode | Modell | Kategorie | Intervall | Startstation | Ende | Startzeit | Endzeit | Preis | Tarif | Gültig ab | Wartung | Wartungsdatum | Fehler |
| --- | --- | --- | --- | --- | --- | --- | --- | ---: | --- | --- | --- | --- | ---: | --- | --- | ---: | --- | --- |
| R-26001 | K-1001 | Anna Krüger | anna.krueger@example.org | MS-1001 | BM-01 | Gazelle Arroyo C7+ | City | 120 | Domplatz | Hafen | 2026-01-05 08:10 | 2026-01-05 08:42 | 420 | Pendler | 2026-01-01 | 1 | 2026-01-06 | Licht ohne Funktion |
| R-26001 | K-1001 | Anna Krüger | anna.krueger@example.org | MS-1001 | BM-01 | Gazelle Arroyo C7+ | City | 120 | Domplatz | Hafen | 2026-01-05 08:10 | 2026-01-05 08:42 | 420 | Pendler | 2026-01-01 | 2 | 2026-02-14 | Kette springt |
| R-26002 | K-1002 | Bilal Demir | bilal.demir@example.org | MS-1002 | BM-02 | Kalkhoff Endeavour 1.B Move | E-Bike | 90 | Hafen | Aasee | 2026-01-08 17:20 | 2026-01-08 18:05 | 690 |  |  | 1 | 2026-01-09 | Bremse schleift |
| R-26003 | K-1003 | Clara Hoffmann | clara.hoffmann@example.org | MS-1003 | BM-03 | Cube Touring Hybrid ONE 500 | E-Bike | 90 | Hauptbahnhof | Domplatz | 2026-01-12 07:48 | 2026-01-12 08:16 | 510 | Komfort | 2026-01-01 | 1 | 2026-01-13 | Akku lädt nicht |
| R-26003 | K-1003 | Clara Hoffmann | clara.hoffmann@example.org | MS-1003 | BM-03 | Cube Touring Hybrid ONE 500 | E-Bike | 90 | Hauptbahnhof | Domplatz | 2026-01-12 07:48 | 2026-01-12 08:16 | 510 | Komfort | 2026-01-01 | 2 | 2026-03-02 | Motor setzt aus |
| R-26004 | K-1004 | Daniel Yilmaz | daniel.yilmaz@example.org | MS-1004 | BM-04 | Stevens City Flight | City | 120 | Aasee | Coesfelder Kreuz | 2026-01-17 11:05 | 2026-01-17 11:39 | 480 | Basis | 2026-01-01 | 1 | 2026-01-20 | Schutzblech locker |
| R-26005 | K-1005 | Elif Schneider | elif.schneider@example.org | MS-1005 | BM-05 | Diamant 247 | City | 120 | Coesfelder Kreuz | Hafen | 2026-01-22 14:30 | 2026-01-22 15:14 | 570 | Pendler | 2026-02-01 | 1 | 2026-01-23 | Reifen platt |
| R-26006 | K-1006 | Felix Wagner | felix.wagner@example.org | MS-1006 | BM-06 | Riese & Müller Charger4 | E-Bike | 90 | Hafen | Hauptbahnhof | 2026-02-02 09:12 | 2026-02-02 09:50 | 620 |  |  | 1 | 2026-02-03 | Display ausgefallen |
| R-26006 | K-1006 | Felix Wagner | felix.wagner@example.org | MS-1006 | BM-06 | Riese & Müller Charger4 | E-Bike | 90 | Hafen | Hauptbahnhof | 2026-02-02 09:12 | 2026-02-02 09:50 | 620 |  |  | 2 | 2026-03-10 | Akkuhalter locker |
| R-26007 | K-1001 | Anna Krüger | anna.krueger@example.org | MS-1007 | BM-07 | Trek FX 2 Disc | Trekking | 150 | Domplatz | Gievenbeck | 2026-02-09 16:03 | 2026-02-09 16:51 | 560 | Pendler | 2026-01-01 | 1 | 2026-02-10 | Schaltung verstellt |
| R-26008 | K-1007 | Greta Özdemir | greta.oezdemir@example.org | MS-1008 | BM-08 | Brompton C Line Explore | Faltrad | 180 | Gievenbeck | Domplatz | 2026-02-14 10:20 | 2026-02-14 11:02 | 590 | Wochenende | 2026-02-01 | 1 | 2026-02-16 | Faltgelenk schwergängig |
| R-26009 | K-1008 | Hasan Kaya | hasan.kaya@example.org | MS-1009 | BM-09 | Babboe City Mountain | Lastenrad | 60 | Hauptbahnhof | Hafen | 2026-02-19 13:15 | 2026-02-19 14:10 | 850 | Familie | 2026-01-15 | 1 | 2026-02-20 | Ständer gebrochen |
| R-26009 | K-1008 | Hasan Kaya | hasan.kaya@example.org | MS-1009 | BM-09 | Babboe City Mountain | Lastenrad | 60 | Hauptbahnhof | Hafen | 2026-02-19 13:15 | 2026-02-19 14:10 | 850 | Familie | 2026-01-15 | 2 | 2026-03-18 | Bremsbeläge verschlissen |
| R-26010 | K-1009 | Ida Becker | ida.becker@example.org | MS-1010 | BM-10 | Cannondale Quick 4 | Trekking | 150 | Aasee |  | 2026-03-05 18:40 |  |  |  |  | 1 | 2026-02-01 | Klingel fehlt |

Für die Modellliste gelten zwei zusätzliche Regeln. Jede Kategorie hat genau ein Wartungsintervall in Tagen. Außerdem identifizieren Modellname und Wartungsintervall zusammen einen Modelleintrag, auch wenn der Modellcode ebenfalls eindeutig ist.

## Beobachtungen

- Wie kann der Verleih ein neues Rad und sein Modell erfassen, bevor jemand das Rad ausleiht?
- Welche Zeilen müssen Mitarbeitende ändern, wenn Anna Krüger eine neue E-Mail-Adresse meldet?
- Welche Angaben über das Modell "Cannondale Quick 4" und Rad MS-1010 verschwinden, wenn die einzige Ausleihe R-26010 gelöscht wird?

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
