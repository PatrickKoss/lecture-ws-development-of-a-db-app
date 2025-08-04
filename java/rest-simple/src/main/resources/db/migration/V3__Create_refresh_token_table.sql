CREATE TABLE refresh_token (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    token VARCHAR(512) NOT NULL UNIQUE,
    admin_id INTEGER NOT NULL,
    expires_at TEXT NOT NULL,
    created_at TEXT NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (admin_id) REFERENCES admin(id) ON DELETE CASCADE
);

CREATE INDEX idx_refresh_token_token ON refresh_token(token);
CREATE INDEX idx_refresh_token_admin_id ON refresh_token(admin_id);
CREATE INDEX idx_refresh_token_expires_at ON refresh_token(expires_at);