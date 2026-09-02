# C1: HTTP-Vertrag

Zeitbox: etwa 25 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

Nutzt das Modell `Exhibit` aus B3. `openapi.yaml` enthält Pfad und Statuscodes, aber noch keine Schemas.

Starttest:

```bash
test -f openapi.yaml && rg -n '/api/|201|400|409' openapi.yaml
```

## Kernauftrag

Definiert Request und Response für GET und POST. Legt Pflichtfelder, `Location`-Header und einen Konflikt fest: `inventory_code` darf nicht doppelt vorkommen.

## Vertiefung

Ergänzt PUT oder DELETE. Beschreibt, warum die Operation idempotent ist.

## Vorbereiteter Zwischenstand

Der vorbereitete Vertrag begrenzt C2 auf eine flache Ressource. Beziehungen bleiben in der Vertiefung.

## Ausgang

`openapi.yaml` beschreibt GET, POST, 200, 201, 400, 404 und 409 samt JSON-Beispielen.

Prüfbefehl:

```bash
npx --yes @redocly/cli lint openapi.yaml
```

## Auswertung

Welche ungültige Eingabe ist ein 400-Fehler, und welcher gültige, aber kollidierende Request führt zu 409?
