"""Helper methods."""
from typing import Tuple, Optional

from rest_framework import status
from rest_framework.response import Response


def validate_request_body(
    request,
) -> Tuple[Optional[Response], Optional[dict]]:
    """
    Validate the json body of a request.

    :param request: django request
    :return: None or error response, None or json dict
    """
    try:
        return None, request.data
    except Exception:
        return (
            Response(
                data={"message": "Sent data is not valid"},
                status=status.HTTP_400_BAD_REQUEST,
            ),
            None,
        )
