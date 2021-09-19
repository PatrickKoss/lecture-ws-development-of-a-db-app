"""Helper methods."""
import gzip
import os


def compress_file(src_file: str) -> str:
    """
    Compress file to save space.

    :return: compressed file name
    """
    compressed_file = f"{src_file}.gz"
    with open(src_file, "rb") as f_in:
        with gzip.open(compressed_file, "wb") as f_out:
            for line in f_in:
                f_out.write(line)
    return compressed_file


def extract_file(src_file: str) -> str:
    """
    Extract compressed file.

    :return: extracted file name
    """
    extracted_file, extension = os.path.splitext(src_file)
    with gzip.open(src_file, "rb") as f_in:
        with open(extracted_file, "wb") as f_out:
            for line in f_in:
                f_out.write(line)
    return extracted_file
