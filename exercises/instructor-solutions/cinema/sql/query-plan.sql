DROP INDEX IF EXISTS idx_movies_release_year;
EXPLAIN QUERY PLAN SELECT id FROM movies WHERE release_year >= 2024;
CREATE INDEX idx_movies_release_year ON movies(release_year);
EXPLAIN QUERY PLAN SELECT id FROM movies WHERE release_year >= 2024;
-- Vorher: SCAN movies. Nachher: SEARCH movies USING COVERING INDEX idx_movies_release_year.
