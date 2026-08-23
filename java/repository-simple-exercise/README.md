# Ex 6: JDBC und Repository

In Deck 07 greift ihr mit JDBC aus Java auf die Tabelle `students` zu. In Deck 08 bündelt ihr diesen Datenbankcode in einem Repository. Dieses Projekt ist das Skeleton für beide Schritte.

## Teil 1: StudentRepositoryImpl

Zeitbox: 45 Minuten

Gegeben sind das Model `Student`, das Interface `StudentRepository`, eine CLI mit vorbereiteten CRUD-Menüpunkten und das unvollständige `StudentRepositoryImpl`. Die Datenbankverbindung ist als `jdbc:sqlite:students.db` vorgegeben.

Die Tabelle muss genau diesem Schema aus `java/sql/university/schema.sql` entsprechen:

```sql
CREATE TABLE IF NOT EXISTS students (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    student_number TEXT NOT NULL UNIQUE,
    enrollment_date TEXT NOT NULL
        CHECK (
            date(enrollment_date) IS NOT NULL
            AND enrollment_date = date(enrollment_date)
        )
);
```

### Was ihr implementiert

- `initializeDatabase()` legt die Tabelle `students` an.
- `createStudent(Student)` speichert einen Datensatz und übernimmt die erzeugte ID.
- `findStudentById(int)` liefert den Datensatz oder `Optional.empty()`.
- `findAllStudents()` liefert alle Datensätze nach ID sortiert.
- `updateStudent(Student)` ändert einen vorhandenen Datensatz und meldet den Erfolg.
- `deleteStudent(int)` löscht einen Datensatz und meldet den Erfolg.
- `studentExists(int)` prüft, ob eine ID vorhanden ist.

Verwendet für alle Eingaben ein `PreparedStatement`. Schließt `Connection`, `PreparedStatement` und `ResultSet` mit `try-with-resources`. Behandelt `SQLException` sowie doppelte E-Mail-Adressen und Studierendennummern sinnvoll. Aktiviert danach in `Main.java` die Repository-Initialisierung und die vorbereiteten TODO-Blöcke.

### Starten und prüfen

```bash
cd java/repository-simple-exercise
./gradlew build
./verify-exercise.sh
./gradlew run
```

Prüft über die CLI Create, Read, Update und Delete. Legt außerdem zwei Datensätze mit derselben E-Mail-Adresse oder Studierendennummer an und kontrolliert die Fehlermeldung.

## Teil 2: eure eigene Domäne

Zeitbox: 30 Minuten

Wählt eine Tabelle aus der Domäne eurer Gruppe. Die konkrete Entität und ihre Felder stehen in `exercises/<domain>/04-jdbc.md`.

- Legt die Tabelle beim Start an.
- Erstellt ein eigenes Model und ein Repository-Interface.
- Implementiert das Repository mit `PreparedStatement` und `try-with-resources`.
- Ergänzt zwei CLI-Menüpunkte, einen zum Anlegen und einen zum Anzeigen der Datensätze.
- Testet beide Menüpunkte mit einer gültigen und einer fehlerhaften Eingabe.

## Häufige Fehler

- Eingaben werden in SQL-Strings eingesetzt, statt Parameter zu verwenden.
- Eine JDBC-Ressource bleibt außerhalb von `try-with-resources` offen.
- `LocalDate` wird nicht als ISO-Datum `YYYY-MM-DD` gespeichert oder gelesen.
- Leere Abfragen und verletzte `UNIQUE`-Constraints führen zu unklaren Fehlern.
- Die CLI-TODOs bleiben auskommentiert, obwohl das Repository fertig ist.

## Optionale Erweiterungen

- Ergänzt eine Suche nach Name oder E-Mail-Adresse.
- Validiert E-Mail-Adressen und Datumswerte vor dem Speichern.
- Fasst mehrere Schreiboperationen in einer Transaktion zusammen.
