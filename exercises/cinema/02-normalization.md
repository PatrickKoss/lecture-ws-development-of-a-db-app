# 2. Normalisierung

> Zeitbox: 40 Minuten

## Ausgangstabelle

Die alte Kasse exportiert "ticketverkauf.csv". Eine Zeile steht für einen verkauften Sitz. Deshalb kommt dieselbe Vorstellung für jeden verkauften Sitz erneut vor.

| Ticket | Vorstellung | Datum | Beginn | Film-Nr. | Film | Laufzeit | FSK | Mindestalter | Saal-Nr. | Saal | Reihe | Sitz | Kategorie | Preis | Verkauft am | Kunden-Nr. | Kunde | E-Mail |
| --- | --- | --- | --- | --- | --- | ---: | --- | ---: | --- | --- | --- | ---: | --- | ---: | --- | --- | --- | --- |
| T-260001 | V-26001 | 2026-03-05 | 18:00 | F-101 | In die Sonne schauen | 149 | FSK 16 | 16 | S-01 | Gloria | A | 1 | Parkett | 11,50 | 2026-02-18 14:12 | K-1001 | Miriam Koch | miriam.koch@example.org |
| T-260002 | V-26001 | 2026-03-05 | 18:00 | F-101 | In die Sonne schauen | 149 | FSK 16 | 16 | S-01 | Gloria | A | 2 | Parkett | 11,50 | 2026-02-18 14:15 |  |  |  |
| T-260003 | V-26001 | 2026-03-05 | 18:00 | F-101 | In die Sonne schauen | 149 | FSK 16 | 16 | S-01 | Gloria | B | 1 | Loge | 13,00 | 2026-02-19 09:04 | K-1002 | Deniz Yilmaz | deniz.yilmaz@example.org |
| T-260004 | V-26001 | 2026-03-05 | 18:00 | F-101 | In die Sonne schauen | 149 | FSK 16 | 16 | S-01 | Gloria | B | 2 | Loge | 13,00 | 2026-02-20 20:31 |  |  |  |
| T-260005 | V-26002 | 2026-03-06 | 20:15 | F-102 | Flow | 85 | FSK 6 | 6 | S-02 | Atelier | A | 1 | Parkett | 10,00 | 2026-02-21 11:22 | K-1003 | Lea Winter | lea.winter@example.org |
| T-260006 | V-26002 | 2026-03-06 | 20:15 | F-102 | Flow | 85 | FSK 6 | 6 | S-02 | Atelier | A | 2 | Parkett | 10,00 | 2026-02-21 11:25 | K-1003 | Lea Winter | lea.winter@example.org |
| T-260007 | V-26002 | 2026-03-06 | 20:15 | F-102 | Flow | 85 | FSK 6 | 6 | S-02 | Atelier | B | 1 | Loge | 11,50 | 2026-02-23 16:40 |  |  |  |
| T-260008 | V-26003 | 2026-03-07 | 17:30 | F-101 | In die Sonne schauen | 149 | FSK 16 | 16 | S-03 | Panorama | A | 1 | Parkett | 11,50 | 2026-02-24 10:01 | K-1004 | Stefan Reuter | stefan.reuter@example.org |
| T-260009 | V-26003 | 2026-03-07 | 17:30 | F-101 | In die Sonne schauen | 149 | FSK 16 | 16 | S-03 | Panorama | A | 2 | Parkett | 11,50 | 2026-02-24 10:03 | K-1005 | Aylin Demir | aylin.demir@example.org |
| T-260010 | V-26004 | 2026-03-12 | 19:00 | F-103 | Perfect Days | 123 | FSK 0 | 0 | S-01 | Gloria | A | 1 | Parkett | 9,50 | 2026-03-01 18:52 |  |  |  |
| T-260011 | V-26005 | 2026-03-14 | 20:30 | F-104 | Konklave | 120 | FSK 6 | 6 | S-04 | Studio | A | 1 | Parkett | 12,00 | 2026-03-02 12:17 | K-1006 | Nora Schmitz | nora.schmitz@example.org |
| T-260012 | V-26005 | 2026-03-14 | 20:30 | F-104 | Konklave | 120 | FSK 6 | 6 | S-04 | Studio | A | 2 | Parkett | 12,00 | 2026-03-02 12:20 | K-1007 | Jonas Falk | jonas.falk@example.org |
| T-260013 | V-26005 | 2026-03-14 | 20:30 | F-104 | Konklave | 120 | FSK 6 | 6 | S-04 | Studio | B | 1 | Loge | 13,50 | 2026-03-03 08:42 |  |  |  |
| T-260014 | V-26006 | 2026-03-20 | 18:45 | F-105 | Anatomie eines Falls | 152 | FSK 12 | 12 | S-05 | Luna | A | 1 | Parkett | 10,50 | 2026-03-05 17:11 | K-1008 | Hanna Berg | hanna.berg@example.org |

Für den Filmkatalog gelten zwei zusätzliche Regeln. Jede FSK-Freigabe steht genau für ein Mindestalter. Außerdem identifizieren Filmtitel und Mindestalter zusammen einen Filmeintrag, obwohl die Filmnummer ebenfalls eindeutig ist.

## Beobachtungen

- Wie kann das Kino die Vorstellung V-26007 planen, bevor das erste Ticket verkauft wird?
- Welche Zeilen müssen Mitarbeitende ändern, wenn die Laufzeit von "In die Sonne schauen" korrigiert wird?
- Welche Angaben über V-26006, "Anatomie eines Falls" und den Sitz A1 verschwinden, wenn das einzige Ticket T-260014 gelöscht wird?

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
