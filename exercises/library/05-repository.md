# 5. Repository-Pattern

> Zeitbox: 30 Minuten

## Ausgangspunkt

Verwendet `Book` und `BookRepository` aus der JDBC-Aufgabe. Ergänzt `Optional<Book> findByIsbn(String isbn)`. Der Aufruf mit `9783446269234` soll das Buch "Datenbanksysteme" liefern, eine unbekannte ISBN ergibt `Optional.empty()`.

Als zweite Entität kommt `Copy` hinzu. Ein Exemplar verweist über `bookId` auf `Book`; `bookId` und `copyNumber` identifizieren es gemeinsam. Speichert außerdem den eindeutigen Barcode, den Zweigstellencode und das Anschaffungsdatum.

## Aufgabe

1. Ergänzt im Book-Repository die Methode `findByIsbn`.
2. Implementiert die Abfrage mit `PreparedStatement` und `Optional<Book>` als Rückgabetyp.
3. Ergänzt `Copy` mit Tabelle, Model und Repository.
4. Setzt den Foreign Key auf `books.id` und zeigt im CLI zu jedem gefundenen Buch seine Exemplare mit Zweigstelle an.
5. Haltet in zwei Sätzen fest, welcher wiederholte JDBC-Code durch das Repository gebündelt wird.

## Abgabe

- beide Repository-Implementierungen und Models
- SQL zum Anlegen beider Tabellen einschließlich Foreign Key
- Beispielaufruf und Ausgabe von `findByIsbn`
