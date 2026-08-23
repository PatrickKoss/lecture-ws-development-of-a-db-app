# Review Deck 12

| Folie (h/v/f) | Datei (PNG name) | Befund | Schwere (blockiert / sollte / nit) |
|---|---|---|---|
| 12/1/0 | `slide-207-fragment-00.png` | Die Texte in den acht Prozessboxen sind mit 24 px gesetzt und liegen damit unter der geforderten Mindestgröße von 28 px für Fließtext. | sollte |
| 12/4/2 | `slide-210-fragment-02.png` | Die JSON-Pfade prüfen `name`, obwohl der Kursvertrag `firstName` verwendet. Der Hinweis benennt die Abweichung, das gezeigte Testbeispiel passt trotzdem nicht zum vereinbarten Vertrag. | sollte |
| 12/4/2 | `slide-210-fragment-02.png` | Der Test umfasst nur fünf Codezeilen im oberen Teil des Felds. Die gesamte untere Hälfte der Folie bleibt leer. | sollte |
| 12/6/3 | `slide-212-fragment-03.png` | Der Mockito-Test ruft `result.getName()` statt `result.getFirstName()` auf und weicht damit vom vereinbarten Student-Modell ab. | sollte |
| 12/13/5 | `slide-219-fragment-05.png` | Die Linie vom hypothetischen Dockerfile endet oberhalb der Image-Box, die Linie vom Spring-JAR trifft die Box ohne Pfeilspitze. Nur die Linie der Java Runtime besitzt eine Pfeilspitze. | sollte |
| 12/17/0 | `slide-223-fragment-00.png` | Die Sprechernotiz behauptet, die "rote Linie" sei vollständig gebaut. Die Prozesslinie der Decks ist cyan und markiert den aktuellen Schritt gelb. | nit |

## Gesamteindruck

Deck 12 deckt Tests, Betrieb und Sicherheit in einer gut lesbaren Reihenfolge ab. Die meisten Codefolien nutzen die Bühne sinnvoll und bleiben ohne Scrollbalken. Zwei Testbeispiele widersprechen jedoch dem festgelegten Feld `firstName`, obwohl ihre Hinweise die Abweichung bereits nennen. Das Docker-Diagramm braucht klare und einheitliche Verbindungen zum Image.
