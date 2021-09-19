"""Main."""
import argparse
import logging
from typing import Optional

from backup_restore.scripts.postgres_backup import backup_postgres_db
from backup_restore.scripts.postgres_restore import (
    complete_postgres_restore,
)
from backup_restore.scripts.s3 import (
    list_available_backup,
)


def main() -> Optional[str]:
    """Set up main program."""
    # init logger
    logger = logging.getLogger("Logger main")
    logger.setLevel(logging.INFO)
    handler = logging.StreamHandler()
    formatter = logging.Formatter(
        "%(asctime)s - %(name)s - %(levelname)s - %(message)s"
    )
    handler.setFormatter(formatter)
    logger.addHandler(handler)

    # init args parser
    args_parser = argparse.ArgumentParser(description="Postgres database management")
    args_parser.add_argument(
        "--action",
        metavar="action",
        choices=["list", "restore", "backup"],
        required=True,
    )
    args_parser.add_argument(
        "--date",
        metavar="dd-MM-YYYYThh:mm:ss",
        help="Date to use for restore (show with --action list)",
    )
    args = args_parser.parse_args()

    # list task
    if args.action == "list":
        logger.info("Listing all backup buckets")
        s3_backup_objects = list_available_backup()
        for key in s3_backup_objects:
            logger.info(f"Key : {key}")

    # backup task
    elif args.action == "backup":
        logger.info("Start backing up database")
        error, _ = backup_postgres_db()
        if error is not None:
            logger.error(error)
            return error
        logger.info("Backup complete")

    # restore task
    elif args.action == "restore":
        if not args.date:
            logger.error(
                'No date was chosen for restore. Run again with the "list" '
                "action to see available restore dates"
            )
        else:
            error = complete_postgres_restore(args)
            if error is not None:
                logger.error(error)
                return error
            logger.info("Database restored and active.")
    else:
        logger.info("No valid argument was given.")
        logger.info(args)
        return "No valid argument was given."

    return None


if __name__ == "__main__":
    main()
