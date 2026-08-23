# 2. Normalisierung

> Zeitbox: 40 Minuten

## Ausgangstabelle

Die Gründer exportieren "Bestellungen.xlsx" aus ihrer Abrechnung. Eine Zeile steht für eine Position einer Bestellung. Deshalb kommen Bestell-, Kunden- und Restaurantdaten mehrfach vor.

| Bestellung | Kundennr. | Kunde | E-Mail | Partnernr. | Restaurant | Stadt | Provision | Bestellt am | Art | Kurier-Nr. | Kurier | Position | Gericht | Kategorie | MwSt. | Menge | Stückpreis | Bewertung |
| --- | --- | --- | --- | --- | --- | --- | ---: | --- | --- | --- | --- | ---: | --- | --- | ---: | ---: | ---: | ---: |
| O-26001 | K-1001 | Lena Vogt | lena.vogt@example.org | R-101 | Pott Pizza | Essen | 18 % | 2026-01-08 | Lieferung | C-201 | Mehmet Kaya | 1 | Margherita | Pizza | 7 % | 2 | 8,90 € | 5 |
| O-26001 | K-1001 | Lena Vogt | lena.vogt@example.org | R-101 | Pott Pizza | Essen | 18 % | 2026-01-08 | Lieferung | C-201 | Mehmet Kaya | 2 | Tiramisu im Glas | Dessert | 7 % | 1 | 5,20 € | 5 |
| O-26002 | K-1002 | Jonas Richter | jonas.richter@example.org | R-102 | Curry am Markt | Bochum | 16 % | 2026-01-10 | Abholung |  |  | 1 | Currywurst Ruhrpott | Hauptgericht | 7 % | 1 | 9,50 € |  |
| O-26003 | K-1003 | Aylin Demir | aylin.demir@example.org | R-103 | Levante Küche | Dortmund | 20 % | 2026-01-14 | Lieferung | C-202 | Sarah Krüger | 1 | Falafel-Teller | Hauptgericht | 7 % | 2 | 12,40 € | 4 |
| O-26003 | K-1003 | Aylin Demir | aylin.demir@example.org | R-103 | Levante Küche | Dortmund | 20 % | 2026-01-14 | Lieferung | C-202 | Sarah Krüger | 2 | Baklava | Dessert | 7 % | 2 | 4,80 € | 4 |
| O-26004 | K-1001 | Lena Vogt | lena.vogt@example.org | R-104 | Grünzeug | Essen | 15 % | 2026-01-17 | Lieferung | C-203 | Tobias Schulte | 1 | Kumpir Gemüse | Hauptgericht | 7 % | 1 | 10,90 € |  |
| O-26005 | K-1004 | David Özkan | david.oezkan@example.org | R-105 | Mamas Pasta | Duisburg | 19 % | 2026-01-21 | Lieferung | C-204 | Nina Becker | 1 | Tagliatelle Pilze | Hauptgericht | 7 % | 1 | 13,80 € | 3 |
| O-26005 | K-1004 | David Özkan | david.oezkan@example.org | R-105 | Mamas Pasta | Duisburg | 19 % | 2026-01-21 | Lieferung | C-204 | Nina Becker | 2 | Panna Cotta | Dessert | 7 % | 2 | 5,60 € | 3 |
| O-26006 | K-1005 | Marie Hoffmann | marie.hoffmann@example.org | R-106 | Hafen Burger | Duisburg | 17 % | 2026-01-25 | Abholung |  |  | 1 | Schimanski Burger | Hauptgericht | 7 % | 2 | 14,50 € |  |
| O-26007 | K-1006 | Cem Yildiz | cem.yildiz@example.org | R-107 | Sushibar Nord | Gelsenkirchen | 21 % | 2026-02-02 | Lieferung | C-205 | Luca Romano | 1 | Lachs Bento | Hauptgericht | 7 % | 1 | 16,90 € | 5 |
| O-26008 | K-1002 | Jonas Richter | jonas.richter@example.org | R-101 | Pott Pizza | Essen | 18 % | 2026-02-05 | Lieferung | C-201 | Mehmet Kaya | 1 | Margherita | Pizza | 7 % | 1 | 8,90 € |  |
| O-26008 | K-1002 | Jonas Richter | jonas.richter@example.org | R-101 | Pott Pizza | Essen | 18 % | 2026-02-05 | Lieferung | C-201 | Mehmet Kaya | 2 | Tiramisu im Glas | Dessert | 7 % | 2 | 5,20 € |  |
| O-26009 | K-1007 | Sophie Nowak | sophie.nowak@example.org | R-108 | Zeche Vegan | Bochum | 14 % | 2026-02-09 | Lieferung | C-206 | Fatma Acar | 1 | Seitan-Schnitzel | Hauptgericht | 7 % | 1 | 15,20 € |  |
| O-26010 | K-1008 | Noah Peters | noah.peters@example.org | R-109 | Tandoori West | Oberhausen | 20 % | 2026-02-11 | Lieferung | C-207 | Paul Neumann | 1 | Chicken Tikka | Hauptgericht | 7 % | 2 | 14,90 € | 4 |

Für die Speisekarte gelten zwei zusätzliche Regeln. Jede Gerichtskategorie hat genau einen Mehrwertsteuersatz. Außerdem identifizieren Gerichtsname und Mehrwertsteuersatz zusammen einen Menüeintrag, obwohl Restaurant und Gerichtsname ebenfalls eindeutig sind.

## Beobachtungen

- Wie kann RuhrLiefert das Restaurant "Kanal Falafel" und sein erstes Gericht erfassen, bevor dort jemand bestellt?
- Welche Zeilen müssen Mitarbeitende ändern, wenn Lena Vogt eine neue E-Mail-Adresse meldet?
- Welche Angaben über "Chicken Tikka" und das Restaurant "Tandoori West" verschwinden, wenn die einzige Bestellung O-26010 gelöscht wird?

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
