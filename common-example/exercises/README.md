# Gemeinsam üben, danach übertragen

Diese Aufgaben bereiten die Gruppenübungen vor. Wir bearbeiten jede Phase zuerst an derselben Hochschulverwaltung. Danach setzt jede Gruppe den Schritt für ihre eigene Domäne um. `Student` bleibt unsere Hauptentität.

Alle Musterlösungen sind im [gemeinsamen Beispiel](../README.md) enthalten. Ihr könnt sie nach der gemeinsamen Bearbeitung wieder öffnen, ausführen und mit eurer eigenen Lösung vergleichen.

| Phase | Gemeinsamer Arbeitsauftrag | Vorbereitung auf die Gruppenarbeit |
| --- | --- | --- |
| A0 | [Domäne](a0-domain/README.md) | Begriffe, Annahmen und Hauptentität bestimmen |
| A1 | [ER-Modell](a1-er-model/README.md) | Beziehungen und Kardinalitäten begründen |
| A2 | [Relationenmodell](a2-relational-model/README.md) | Schlüssel und Fremdschlüssel festlegen |
| A3 | [Normalisierung](a3-normalization/README.md) | Abhängigkeiten erkennen und bis 3NF zerlegen |
| B1 | [Schema](b1-schema/README.md) | Regeln als ausführbares SQL ausdrücken |
| B2 | [SQL-Abfragen](b2-sql/README.md) | JOINs, fehlende Beziehungen und Aggregation prüfen |
| B3 | [JDBC](b3-jdbc/README.md) | Zeilen mit PreparedStatements lesen und explizit mappen |
| B4 | [Repository](b4-repository/README.md) | JDBC-Ausnahmen aus der aufrufenden Schicht entfernen |
| C1 | [HTTP-Vertrag](c1-http-contract/README.md) | Request und Response erstellen und dokumentieren |
| C2 | [GET](c2-spring-resource/README.md) | Liste und Einzelzugriff durch Repository, Service und Controller bauen |
| C3 | [POST und Tests](c3-tests-errors/README.md) | Validation, Speichern, Konfliktregel und Fehlerfälle umsetzen |

Die gemeinsamen Aufgaben gehören zu den Vortrags- und Demo-Blöcken im [Ablauf](../../docs/schedule.md). Ihre Zeitangaben sind Richtwerte innerhalb dieser Blöcke, keine zusätzlichen Gruppenphasen.

## Mit den Arbeitsständen umgehen

B3 und C2 enthalten eigenständige Gradle-Starter mit offenen Arbeitsstellen. B4 setzt euren JDBC-Stand aus B3 fort. C1, C2 und C3 verwenden gemeinsam den Spring-Starter in `c2-spring-resource/starter/`.

Die vollständigen Java-Lösungen liegen daneben in `../jdbc/`, `../repository-basic/` und `../backend/`. Bearbeitet die Starter oder eine eigene Kopie. So bleibt das funktionierende Beispiel zum Nachschlagen erhalten.

Für jede Phase gilt derselbe Ablauf: Aufgabe lesen, Ergebnis vorhersagen, gemeinsam umsetzen, Checkpoint ausführen und mit der Lösung vergleichen. Die Frage zum Übertragen benennt den Teil, den ihr anschließend für eure Gruppendomäne selbst entscheiden müsst.
