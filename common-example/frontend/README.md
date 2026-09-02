# Frontend der Hochschulverwaltung

Das vorbereitete Next.js-Frontend verwendet denselben Student-Vertrag wie das Spring-Backend. Es liest `GET /api/students` und sendet neue Datensätze an `POST /api/students`.

## Start

```sh
npm install
npm run dev
```

Das Frontend läuft auf `http://localhost:3000`. Das Backend wird unter `http://localhost:8081` erwartet. Ein anderer Backend-Pfad kann über `NEXT_PUBLIC_API_BASE_URL` gesetzt werden.

## Vertrag

```json
{
  "id": 1,
  "firstName": "Lena",
  "lastName": "Hoffmann",
  "email": "lena.hoffmann@stud.example",
  "studentNumber": "M2023001",
  "enrollmentDate": "2023-10-01"
}
```

Die Datei `api/openapi.yaml` beschreibt GET, POST und das gemeinsame Fehlerformat. Authentifizierung, PUT und DELETE gehören nicht zum Kernpfad dieser Vorlesung.

## Prüfen

```sh
npm test -- --runInBand
npm run build
```
