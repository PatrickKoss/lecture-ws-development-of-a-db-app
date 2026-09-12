# Musterlösung für B4

`MusicCourseCatalog` hängt von `MusicCourseRepository` ab, nicht von
`JdbcMusicCourseRepository`. Der Catalog kennt weder SQL noch `Connection`,
`PreparedStatement` oder `ResultSet`.

Eine Umbenennung von `course_code` in der Datenbank betrifft damit die
JDBC-Implementierung und ihre Migration. Solange das Repository weiter ein
`MusicCourse` mit `courseCode` liefert, bleibt der Catalog unverändert. Auch ein
In-Memory-Repository kann dieselbe Schnittstelle implementieren.

Die vorhandene Schnittstelle gibt noch `SQLException` weiter. Das reicht für
die kurze Refactoring-Übung. Eine Anwendungsschicht könnte diese technische
Exception später in eine eigene Repository-Exception übersetzen.
