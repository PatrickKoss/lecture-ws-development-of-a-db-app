# Vertiefung: Repository mit Reflection

Dieses zusätzliche Beispiel zeigt, wie ein generisches Repository aufgebaut sein kann. Der gemeinsame B4-Pflichtweg steht in [`../repository-basic/`](../repository-basic/README.md); den Arbeitsauftrag findet ihr in [`../exercises/b4-repository/`](../exercises/b4-repository/README.md). `AbstractRepository<T>` liest Tabellen- und Spaltennamen aus Annotationen und setzt Datenbankzeilen per Reflection in Java-Objekte um. Die Spring-Lösung verwendet Spring Data JPA. Für die Gruppenübungen müsst ihr kein eigenes Reflection-Mapping implementieren.

## Starten

```sh
./gradlew run
```
