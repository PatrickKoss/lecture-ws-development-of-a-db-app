# 5. Repository-Pattern

> Zeitbox: 30 Minuten

## Ausgangspunkt

Verwendet die Entität und das Repository, die ihr in der JDBC-Aufgabe ergänzt habt.

<!-- TODO(domain): Nenne ein fachlich sinnvolles Suchattribut der ersten Entität und eine zweite Entität, die über einen Foreign Key mit ihr verbunden ist. Formuliere ein konkretes Suchbeispiel. -->

## Aufgabe

1. Ergänzt im Repository eine Methode `findByX`, die nach dem vorgegebenen fachlichen Attribut sucht.
2. Implementiert die Abfrage mit `PreparedStatement` und einem klar definierten Rückgabetyp.
3. Ergänzt die zweite Entität mit Tabelle, Model und Repository.
4. Setzt den Foreign Key und zeigt im CLI mindestens eine Ausgabe, die beide Entitäten verbindet.
5. Haltet in zwei Sätzen fest, welcher wiederholte JDBC-Code durch das Repository gebündelt wird.

## Abgabe

- beide Repository-Implementierungen und Models
- SQL zum Anlegen beider Tabellen einschließlich Foreign Key
- Beispielaufruf und Ausgabe von `findByX`
