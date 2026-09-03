# Musterlösung für C1

Der Request enthält die vom Client gesetzten Felder `courseCode`, `title` und
`fee`. Die Response ergänzt die serverseitig erzeugte `id`. Die Trennung
verhindert, dass ein Client beim Anlegen eine ID vorgibt.

Ein erfolgreicher POST antwortet mit `201 Created`, dem angelegten Datensatz und
einem `Location`-Header. Ein leerer Kurscode oder eine negative Gebühr ist
ungültige Eingabe und ergibt 400. Ein syntaktisch gültiger, aber bereits
belegter Kurscode kollidiert mit dem Datenbestand und ergibt 409.

Der Fehlerkörper hat einen stabilen maschinenlesbaren `code`. `message` ist für
Menschen gedacht. `correlationId` verbindet die Antwort mit dem Server-Log.
Technische Exception-Texte gehören nicht in die Response.

Prüfen:

```bash
npx --yes @redocly/cli lint openapi.yaml
```
