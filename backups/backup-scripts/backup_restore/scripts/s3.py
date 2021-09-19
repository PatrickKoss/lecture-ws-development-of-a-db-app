import logging
from typing import List, Optional, Tuple

import boto3
from botocore.client import Config

from backup_restore.scripts import settings

logger = logging.getLogger("s3 logger")


def download_backup(backup_name: str) -> Optional[str]:
    """
    Download the backup of postgres according to a file name.

    :return: error or None
    """
    try:
        s3 = get_s3_config()
        s3.Bucket(settings.BACKUP_BUCKET_NAME).download_file(
            f"{settings.S3_BACKUP_FOLDER}/{backup_name}",
            f"backup_restore/backups/{backup_name}",
        )
    except Exception as e:
        logger.error(e)
        return f"Download from s3 failed for backup {backup_name}"
    return None


def upload_backup(backup_name: str) -> Optional[str]:
    """
    Upload the backup of postgres according to a file name.

    :return: error or None
    """
    try:
        s3 = get_s3_config()

        # dirty way of ensuring the bucket is always existing
        try:
            s3.create_bucket(Bucket=settings.BACKUP_BUCKET_NAME)
        except Exception as e:
            pass
        s3.Bucket(settings.BACKUP_BUCKET_NAME).upload_file(
            f"backup_restore/backups/{backup_name}",
            f"{settings.S3_BACKUP_FOLDER}/{backup_name}",
        )
    except Exception as e:
        logger.error(e)
        return f"Upload to s3 failed for backup {backup_name}"
    return None


def list_available_backup() -> Tuple[Optional[str], List[str]]:
    """
    List available backups.

    :return: error or None, list of backups
    """
    key_list = []
    try:
        s3_client = boto3.client(
            "s3",
            endpoint_url=settings.ENDPOINT_URL,
            aws_access_key_id=settings.ACCESS_KEY,
            aws_secret_access_key=settings.ACCESS_KEY_SECRET,
            config=Config(signature_version="s3v4"),
            region_name="default",
        )
        s3_objects = s3_client.list_objects_v2(
            Bucket=settings.BACKUP_BUCKET_NAME, Prefix=f"{settings.S3_BACKUP_FOLDER}/"
        )
    except Exception as e:
        logger.error(e)
        return f"List of available buckets failed", list()
    if "Contents" not in s3_objects:
        return None, list()
    for key in s3_objects["Contents"]:
        key_list.append(key["Key"])
    return None, key_list


def get_s3_config() -> boto3.resource:
    """
    Get the boto3 s3 resource.

    :return: boto3 s3 resource (config)
    """
    return boto3.resource(
        "s3",
        endpoint_url=settings.ENDPOINT_URL,
        aws_access_key_id=settings.ACCESS_KEY,
        aws_secret_access_key=settings.ACCESS_KEY_SECRET,
        config=Config(signature_version="s3v4"),
        region_name="default",
    )


def delete_backup(backup_name: str) -> Optional[str]:
    """Delete backup from bucket."""
    try:
        s3 = get_s3_config()
        s3.Object(settings.BACKUP_BUCKET_NAME, backup_name).delete()
    except Exception as e:
        logger.error(e)
        error_msg = f"Delete of backup {backup_name} failed."
        logger.error(error_msg)
        return error_msg
    return None


def get_bucket_from_time_str(time_str: str) -> Optional[str]:
    """Get backup from a time str.

    :return backup name or None
    """
    error, all_backup_keys = list_available_backup()
    if error is not None:
        logger.error(error)
        return error
    return next((backup for backup in all_backup_keys if time_str in backup), None)


if __name__ == "__main__":
    download_backup("backup.out")
