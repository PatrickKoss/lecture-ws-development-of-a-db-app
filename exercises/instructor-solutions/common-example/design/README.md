# Musterlösung für A0 bis A3

Diese Dateien lösen die Entwurfsaufgaben für die gemeinsame Hochschulverwaltung.

- `domain-notes.txt` hält Fachbegriffe, Geschäftsregeln und offene Entscheidungen fest.
- `er.mmd` modelliert Fachbereiche, Lehrende, Studierende, Kurse und Belegungen.
- `relations.sql` notiert Primärschlüssel, Fremdschlüssel, alternative Schlüssel und optionale Attribute.
- `normalization.md` zerlegt die breite Exportrelation nachvollziehbar bis zur 3NF.

Das ER-Diagramm lässt sich neu erzeugen mit:

```bash
npx --yes @mermaid-js/mermaid-cli -i er.mmd -o er.svg
```
