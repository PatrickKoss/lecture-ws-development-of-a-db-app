import logging
import os
import subprocess
from datetime import datetime, timedelta
from typing import List, Optional, Tuple

from pytimeparse.timeparse import timeparse

from backup_restore.scripts import settings
from backup_restore.scripts.s3 import upload_backup
from backup_restore.scripts.utils import compress_file
from .s3 import list_available_backup, delete_backup
from os import listdir

logger = logging.getLogger("backup logger")


def backup_postgres_db() -> Tuple[Optional[str], bytes]:
    """Backup postgres db to a file."""
    try:
        time_str = datetime.now().strftime("%d-%m-%YT%H:%M:%S")
        filename = f"backup_restore/backups/{time_str}-{settings.POSTGRES_CUSTOM_DB}.dump"
        backup_name = f"{time_str}-{settings.POSTGRES_CUSTOM_DB}.dump.gz"

        # create the dump with pg dump and terminal command
        pass

        # check if command worked
        pass

        # compress the dump
        compress_file(filename)

        # upload the zipped dump
        error = upload_backup(backup_name)
        if error is not None:
            return error, bytes()

        # remove created files
        # with temporary file it would be the cleaner way
        try:
            for file in listdir("backup_restore/backups"):
                if file != "__init__.py":
                    os.remove(f"backup_restore/backups/{file}")
            logger.info("Removed backup files")
        except Exception:
            logger.error("Could not remove backup files")

        return None, bytes()
    except Exception as e:
        logger.error(e)
        return "Backup failed", bytes()


def delete_old_backups() -> Optional[str]:
    """
    Delete old backups after the retention time.

    :return: error or None
    """
    error, backups = list_available_backup()
    if error is not None:
        return error

    backup_date: List[dict] = list()

    # if time parse of environment fails, use the default time which is 14d
    seconds = timeparse(settings.RETENTION_TIME)
    if seconds is None:
        seconds = 60 * 60 * 24 * 14

    # create data structure for checking thresholds
    threshold_date = datetime.now() - timedelta(seconds=seconds)
    for backup in backups:
        date_str = backup.split(f"{settings.S3_BACKUP_FOLDER}/")[1].split(
            f"-{settings.POSTGRES_CUSTOM_DB}"
        )[0]
        backup_date.append(
            {"date": datetime.strptime(date_str, "%d-%m-%YT%H:%M:%S"), "backup": backup}
        )
    # delete all backups before the threshold day
    error = None
    for backup in backup_date:
        if backup["date"] < threshold_date:
            error = delete_backup(backup["backup"])
            if error is not None:
                logger.error(error)

    if error is not None:
        return error
    return None


if __name__ == "__main__":
    delete_old_backups()
