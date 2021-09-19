import argparse
import logging
import os
import subprocess
import tempfile
from datetime import datetime
from os import listdir
from typing import Optional, Tuple

import psycopg2
from psycopg2.extensions import ISOLATION_LEVEL_AUTOCOMMIT

from backup_restore.scripts import settings
from backup_restore.scripts.s3 import get_bucket_from_time_str, download_backup
from backup_restore.scripts.utils import extract_file


def restore_postgres_db(
    backup_file: str, postgres_db: str
) -> Tuple[Optional[str], bytes]:
    """Restore postgres db from a file."""
    try:
        # restore postgres with pg_restore and terminal
        pass

        # check if command succeeded

        return None, bytes()
    except Exception as e:
        return f"Issue with the db restore : {e}", bytes()


def create_db(database: str) -> Optional[str]:
    """
    Create database.

    :return: database or None
    """
    logger = logging.getLogger("restore logger")
    try:
        con = psycopg2.connect(
            dbname="postgres",
            port=settings.POSTGRES_CUSTOM_PORT,
            user=settings.POSTGRES_CUSTOM_USER,
            host=settings.POSTGRES_CUSTOM_HOST,
            password=settings.POSTGRES_CUSTOM_PASSWORD,
        )
    except Exception as e:
        logger.error(e)
        return None

    con.set_isolation_level(ISOLATION_LEVEL_AUTOCOMMIT)
    cur = con.cursor()
    try:
        # drop temp database if it already exists. Disconnect all clients from temp database
        pass
    except Exception:
        logger.info(f"DB {database} does not exist, nothing to drop")
    # create temp database with template 0 and grant all privileges on temp database to user
    pass
    return database


def swap_restore(restore_database: str) -> Optional[str]:
    """Swap the restored database with the active database."""
    logger = logging.getLogger("restore logger")
    try:
        con = psycopg2.connect(
            dbname="postgres",
            port=settings.POSTGRES_CUSTOM_PORT,
            user=settings.POSTGRES_CUSTOM_USER,
            host=settings.POSTGRES_CUSTOM_HOST,
            password=settings.POSTGRES_CUSTOM_PASSWORD,
        )
        con.set_isolation_level(ISOLATION_LEVEL_AUTOCOMMIT)
        cur = con.cursor()
        # disconnect all clients from the database that should be restored, drop active db and alter temp
        # database to active
        pass
        return None
    except Exception as e:
        logger.error(e)
        return "swap failed"


def complete_postgres_restore(args: argparse.Namespace) -> Optional[str]:
    """
    Restore complete postgres.

    :return: error or None
    """
    logger = logging.getLogger("restore logger")
    # init date and file name
    try:
        date_obj = datetime.strptime(args.date, "%d-%m-%YT%H:%M:%S")
        time_str = date_obj.strftime("%d-%m-%YT%H:%M:%S")
        backup_name = f"{time_str}-{settings.POSTGRES_CUSTOM_DB}.dump.gz"
        restore_filename = f"backup_restore/backups/{backup_name}"
        restore_filename_decompressed = (
            f"backup_restore/backups/{time_str}-{settings.POSTGRES_CUSTOM_DB}.dump"
        )
    except Exception as e:
        logger.error(e)
        return "Could not convert passed date to object"

    # get backup to time str
    pass

    # download backup
    # logger.info(f"Downloading {backup} from S3")
    pass

    # extract downloaded file
    extract_file(restore_filename)

    logger.info(f"Creating temp database for restore : {settings.POSTGRES_CUSTOM_DB_RESTORE}")

    # create temp restore database
    pass
    # logger.info(f"Created temp database for restore : {tmp_database}")

    # restore backup to temp database
    pass
    logger.info("Restore complete")

    # swap the temp restored database with the currently active database
    # to set the state to the restored database
    logger.info("Switching restored database")
    error = swap_restore(settings.POSTGRES_CUSTOM_DB_RESTORE)
    if error is not None:
        return error

    # remove created files
    try:
        for file in listdir("backup_restore/backups"):
            if file != "__init__.py":
                os.remove(f"backup_restore/backups/{file}")
        logger.info("Removed backup files")
    except Exception:
        logger.error("Could not remove backup files")

    return None


if __name__ == "__main__":
    pass
