import os

BACKUP_BUCKET_NAME = os.environ.get("BACKUP_BUCKET_NAME", "backup-test")
ENDPOINT_URL = os.environ.get("ENDPOINT_URL", "http://localhost:9000")
ACCESS_KEY = os.environ.get("ACCESS_KEY", "")
ACCESS_KEY_SECRET = os.environ.get("ACCESS_KEY_SECRET", "")
S3_BACKUP_FOLDER = "postgres-backups"
POSTGRES_CUSTOM_DB = os.environ.get("POSTGRES_CUSTOM_DB", "db_first")
POSTGRES_CUSTOM_USER = os.environ.get("POSTGRES_CUSTOM_USER", "db_first")
POSTGRES_CUSTOM_HOST = os.environ.get("POSTGRES_CUSTOM_HOST", "localhost")
POSTGRES_CUSTOM_PORT = os.environ.get("POSTGRES_CUSTOM_PORT", "5432")
POSTGRES_CUSTOM_PASSWORD = os.environ.get("POSTGRES_CUSTOM_PASSWORD", "")
POSTGRES_CUSTOM_DB_RESTORE = f"{POSTGRES_CUSTOM_DB}_restore"
RETENTION_TIME = os.environ.get("RETENTION_TIME", "14d")
