# Wo stehen wir auf der roten Linie

Deck 08 nutzt dieses Projekt, um den wiederholten JDBC-Code aus Deck 07 hinter einem Repository zu bündeln. `AbstractRepository<T>` liest Tabellen- und Spaltennamen aus Annotationen und setzt Datenbankzeilen per Reflection in Java-Objekte um. Als Nächstes übernimmt Spring Data diese Arbeit für die REST API.

## Starten

```sh
./gradlew run
```
