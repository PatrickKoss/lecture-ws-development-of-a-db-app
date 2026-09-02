# Technische Voraussetzungen

Für das gemeinsame Beispiel werden Java 21, SQLite 3 und Node.js benötigt. Die Gradle-Projekte bringen ihren Wrapper mit. Eine globale Gradle-Installation ist nicht nötig.

Prüfe die Werkzeuge:

```sh
java -version
sqlite3 --version
node --version
npm --version
```

Das Spring-Backend läuft auf Port 8081:

```sh
cd backend
./gradlew bootRun
```

Das Frontend läuft auf Port 3000:

```sh
cd frontend
npm install
NEXT_PUBLIC_AUTH_ENABLED=false npm run dev
```

Die Java- und Frontend-Befehle stehen gesammelt in `Makefile`.
