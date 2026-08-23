# 2. Normalisierung

> Zeitbox: 40 Minuten

## Ausgangstabelle

Die Helferin hat Papierkalender, Tierkarten und Rezeptblöcke in "Termine.xlsx" übertragen. Eine Zeile steht für ein verschriebenes Medikament. Deshalb kann derselbe Termin mehrfach vorkommen.

| Termin | Halternr. | Halter | E-Mail | Tier-Nr. | Tier | Tierart | Versicherung | Tierarzt-Nr. | Tierarzt | Fachgebiet | Raum | Termin am | Status | Behandlung | Diagnose | PZN | Medikament | Wirkstoff | Dosis | Dauer Tage |
| --- | --- | --- | --- | ---: | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | ---: |
| T-26001 | H-1001 | Anna Krüger | anna.krueger@example.org | 1 | Luna | Katze | AG-832910 | TA-4711 | Dr. Miriam Vogt | Innere Medizin | B-1 | 2026-01-08 09:00 | ABGESCHLOSSEN | B-26001 | Blasenentzündung | 09231122 | Synulox 50 mg | Amoxicillin/Clavulansäure | 1 Tablette morgens und abends | 7 |
| T-26001 | H-1001 | Anna Krüger | anna.krueger@example.org | 1 | Luna | Katze | AG-832910 | TA-4711 | Dr. Miriam Vogt | Innere Medizin | B-1 | 2026-01-08 09:00 | ABGESCHLOSSEN | B-26001 | Blasenentzündung | 01472114 | Metacam 0,5 mg/ml | Meloxicam | 2 ml einmal täglich | 3 |
| T-26002 | H-1002 | Mehmet Demir | mehmet.demir@example.org | 1 | Balu | Hund |  | TA-5822 | Dr. Lena Hartmann | Dermatologie | B-2 | 2026-01-10 10:30 | ABGESCHLOSSEN | B-26002 | Allergische Dermatitis | 10033456 | Apoquel 5,4 mg | Oclacitinib | 1 Tablette täglich | 14 |
| T-26003 | H-1003 | Sophie Lindner | sophie.lindner@example.org | 1 | Nala | Katze | UEL-441208 | TA-4711 | Dr. Miriam Vogt | Innere Medizin | B-1 | 2026-01-14 14:00 | ABGESCHLOSSEN | B-26003 | Niereninsuffizienz | 06199218 | Semintra 4 mg/ml | Telmisartan | 1 ml einmal täglich | 30 |
| T-26004 | H-1004 | Jonas Becker | jonas.becker@example.org | 1 | Rocky | Hund |  | TA-5822 | Dr. Lena Hartmann | Dermatologie | B-2 | 2026-01-17 11:00 | NICHT_ERSCHIENEN |  |  |  |  |  |  |  |
| T-26005 | H-1005 | Aylin Yilmaz | aylin.yilmaz@example.org | 1 | Coco | Kaninchen |  | TA-4711 | Dr. Miriam Vogt | Innere Medizin | B-1 | 2026-01-21 08:30 | ABGESCHLOSSEN | B-26005 | Zahnspitzen | 02493170 | Metacam 1,5 mg/ml | Meloxicam | 0,3 ml einmal täglich | 5 |
| T-26006 | H-1006 | Daniel Hoffmann | daniel.hoffmann@example.org | 1 | Milo | Katze | BARM-71234 | TA-5822 | Dr. Lena Hartmann | Dermatologie | B-2 | 2026-01-25 15:30 | ABGESCHLOSSEN | B-26006 | Hautpilz | 01377561 | Itrafungol 10 mg/ml | Itraconazol | 2 ml einmal täglich | 7 |
| T-26006 | H-1006 | Daniel Hoffmann | daniel.hoffmann@example.org | 1 | Milo | Katze | BARM-71234 | TA-5822 | Dr. Lena Hartmann | Dermatologie | B-2 | 2026-01-25 15:30 | ABGESCHLOSSEN | B-26006 | Hautpilz | 06999103 | Surolan | Miconazol/Polymyxin B/Prednisolon | 5 Tropfen morgens und abends | 10 |
| T-26007 | H-1007 | Emilia Rossi | emilia.rossi@example.org | 1 | Pepe | Wellensittich |  | TA-4711 | Dr. Miriam Vogt | Innere Medizin | B-1 | 2026-02-02 09:30 | ABGESCHLOSSEN | B-26007 | Atemwegsinfekt | 09231122 | Synulox 50 mg | Amoxicillin/Clavulansäure | 1/4 Tablette morgens und abends | 7 |
| T-26008 | H-1002 | Mehmet Demir | mehmet.demir@example.org | 2 | Kira | Hund | AG-933104 | TA-5822 | Dr. Lena Hartmann | Dermatologie | B-2 | 2026-02-05 13:00 | ABGESCHLOSSEN | B-26008 | Otitis externa | 06999103 | Surolan | Miconazol/Polymyxin B/Prednisolon | 5 Tropfen morgens und abends | 10 |
| T-26009 | H-1008 | Noah Schneider | noah.schneider@example.org | 1 | Flocke | Meerschweinchen |  | TA-4711 | Dr. Miriam Vogt | Innere Medizin | B-1 | 2026-02-09 16:00 | ABGESCHLOSSEN | B-26009 | Augenentzündung | 03464231 | Floxal Augentropfen | Ofloxacin | 1 Tropfen dreimal täglich | 7 |
| T-26010 | H-1009 | Fatma Kaya | fatma.kaya@example.org | 1 | Sammy | Hund | RPV-553120 | TA-5822 | Dr. Lena Hartmann | Dermatologie | B-2 | 2026-02-11 10:00 | ABGESCHLOSSEN | B-26010 | Arthrose | 02493170 | Metacam 1,5 mg/ml | Meloxicam | 1,2 ml einmal täglich | 14 |
| T-26011 | H-1003 | Sophie Lindner | sophie.lindner@example.org | 2 | Simba | Katze |  | TA-4711 | Dr. Miriam Vogt | Innere Medizin | B-1 | 2026-02-14 12:30 | ABGESCHLOSSEN | B-26011 | Wundinfektion | 09231122 | Synulox 50 mg | Amoxicillin/Clavulansäure | 1 Tablette morgens und abends | 7 |
| T-26012 | H-1010 | Leonie Wagner | leonie.wagner@example.org | 1 | Bruno | Hund |  | TA-5822 | Dr. Lena Hartmann | Dermatologie | B-2 | 2026-02-18 09:00 | ABGESCHLOSSEN | B-26012 | Juckreiz | 10033456 | Apoquel 5,4 mg | Oclacitinib | 1 Tablette täglich | 14 |

Für die Tierärzte gelten zwei zusätzliche Regeln. Jedes Fachgebiet ist genau einem Behandlungsraum zugeordnet. Außerdem identifizieren der eingetragene Tierarztname und der Behandlungsraum zusammen einen Tierarzt, obwohl die Approbationsnummer ebenfalls eindeutig ist.

## Beobachtungen

- Wie kann die Praxis Bruno und seine Versicherungsnummer erfassen, bevor ein Termin für ihn vereinbart wird?
- Welche Zeilen muss die Helferin ändern, wenn Mehmet Demir eine neue E-Mail-Adresse meldet?
- Welche Angaben über Rocky und seinen Halter verschwinden, wenn der ausgefallene Termin T-26004 gelöscht wird?

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
