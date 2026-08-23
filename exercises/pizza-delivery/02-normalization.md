# 2. Normalisierung

> Zeitbox: 40 Minuten

## Ausgangstabelle

Der Chef überträgt seinen Bestellblock am Monatsende in "Bestellungen.xlsx". Eine Zeile steht für eine bestellte Pizzasorte. Deshalb kann dieselbe Bestellung mehrfach vorkommen.

| Bestellung | Kundennr. | Kunde | Telefon | Straße | PLZ | Ort | Bestellt am | Art | Fahrer-Nr. | Fahrer | Position | Pizza-Nr. | Pizza | Kategorie | Ofen | Menge | Einzelpreis |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | ---: | --- | --- | --- | --- | ---: | ---: |
| B-26001 | K-1001 | Julia Neumann | 0151 24031001 | Wittener Str. 110 | 44803 | Bochum | 2026-01-08 | Lieferung | F-01 | Marco Bianchi | 1 | P-01 | Margherita | Klassiker | OFEN-A | 2 | 9,50 |
| B-26001 | K-1001 | Julia Neumann | 0151 24031001 | Wittener Str. 110 | 44803 | Bochum | 2026-01-08 | Lieferung | F-01 | Marco Bianchi | 2 | P-04 | Diavola | Spezial | OFEN-C | 1 | 12,50 |
| B-26002 | K-1002 | Cem Aydin | 0151 24031002 |  |  |  | 2026-01-10 | Abholung |  |  | 1 | P-02 | Salami | Klassiker | OFEN-A | 1 | 8,50 |
| B-26003 | K-1001 | Julia Neumann | 0151 24031001 | Universitätsstr. 150 | 44801 | Bochum | 2026-01-14 | Lieferung | F-02 | Aylin Demir | 1 | P-03 | Funghi | Vegetarisch | OFEN-B | 1 | 10,00 |
| B-26003 | K-1001 | Julia Neumann | 0151 24031001 | Universitätsstr. 150 | 44801 | Bochum | 2026-01-14 | Lieferung | F-02 | Aylin Demir | 2 | P-09 | Prosciutto | Klassiker | OFEN-A | 1 | 11,50 |
| B-26004 | K-1003 | Hannah Berger | 0151 24031003 | Castroper Str. 207 | 44791 | Bochum | 2026-01-17 | Lieferung | F-01 | Marco Bianchi | 1 | P-04 | Diavola | Spezial | OFEN-C | 2 | 12,50 |
| B-26005 | K-1004 | David Özdemir | 0151 24031004 | Alleestr. 58 | 44793 | Bochum | 2026-01-21 | Lieferung | F-03 | Luca Romano | 1 | P-05 | Hawaii | Klassiker | OFEN-A | 1 | 11,00 |
| B-26006 | K-1005 | Lea Hoffmann | 0151 24031005 |  |  |  | 2026-01-25 | Abholung |  |  | 1 | P-06 | Tonno | Fisch | OFEN-D | 2 | 12,00 |
| B-26007 | K-1006 | Murat Yilmaz | 0151 24031006 | Hattinger Str. 218 | 44795 | Bochum | 2026-02-02 | Lieferung | F-04 | Sarah Klein | 1 | P-01 | Margherita | Klassiker | OFEN-A | 1 | 9,50 |
| B-26008 | K-1002 | Cem Aydin | 0151 24031002 | Kortumstr. 66 | 44787 | Bochum | 2026-02-05 | Lieferung | F-05 | Mehmet Arslan | 1 | P-08 | Quattro Formaggi | Spezial | OFEN-C | 1 | 13,00 |
| B-26008 | K-1002 | Cem Aydin | 0151 24031002 | Kortumstr. 66 | 44787 | Bochum | 2026-02-05 | Lieferung | F-05 | Mehmet Arslan | 2 | P-12 | Enzo | Spezial | OFEN-C | 1 | 12,50 |
| B-26009 | K-1007 | Sofia Rossi | 0151 24031007 |  |  |  | 2026-02-09 | Abholung |  |  | 1 | P-07 | Vegetaria | Vegetarisch | OFEN-B | 1 | 10,50 |
| B-26010 | K-1008 | Noah Schneider | 0151 24031008 | Wasserstr. 105 | 44803 | Bochum | 2026-02-11 | Lieferung |  |  | 1 | P-10 | Spinaci | Vegetarisch | OFEN-B | 2 | 12,00 |
| B-26011 | K-1003 | Hannah Berger | 0151 24031003 | Castroper Str. 207 | 44791 | Bochum | 2026-02-14 | Lieferung | F-06 | Nina Scholz | 1 | P-11 | Frutti di Mare | Fisch | OFEN-D | 1 | 9,00 |
| B-26012 | K-1009 | Aylin Kaya | 0151 24031009 | Dorstener Str. 184 | 44809 | Bochum | 2026-02-18 | Lieferung | F-07 | Jonas Weber | 1 | P-02 | Salami | Klassiker | OFEN-A | 3 | 8,50 |

Für die Speisekarte gelten zwei zusätzliche Regeln. Jede Pizzakategorie wird genau an einer Ofenstation vorbereitet. Außerdem identifizieren Pizzaname und Ofenstation zusammen einen Menüeintrag, obwohl die Pizzanummer ebenfalls eindeutig ist.

## Beobachtungen

- Wie kann die Pizzeria eine neue Pizza "Napoli" samt Grundpreis erfassen, bevor sie jemand bestellt?
- Welche Zeilen muss der Chef ändern, wenn Julia Neumann eine neue Telefonnummer meldet?
- Welche Angaben über "Frutti di Mare" verschwinden, wenn die einzige Bestellung B-26011 gelöscht wird?

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
