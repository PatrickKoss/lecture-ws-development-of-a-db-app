DROP INDEX IF EXISTS idx_books_subject_area;
EXPLAIN QUERY PLAN SELECT id FROM books WHERE subject_area = 'Roman';
CREATE INDEX idx_books_subject_area ON books(subject_area);
EXPLAIN QUERY PLAN SELECT id FROM books WHERE subject_area = 'Roman';
-- Vorher: SCAN books. Nachher: SEARCH books USING COVERING INDEX idx_books_subject_area.
