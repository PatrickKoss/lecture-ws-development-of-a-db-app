import traceback

import sqlalchemy.exc
from starlette.middleware.base import BaseHTTPMiddleware
from starlette.requests import Request
from starlette.responses import JSONResponse
from services.exceptions import NotFoundException


class ExceptionHandlingMiddleware(BaseHTTPMiddleware):
    async def dispatch(self, request: Request, call_next):
        try:
            response = await call_next(request)
            return response
        except ValueError as e:
            return JSONResponse(
                status_code=400,
                content={"detail": str(e)},
            )
        except sqlalchemy.exc.IntegrityError as e:
            return JSONResponse(
                status_code=400,
                content={"detail": str(e)},
            )
        except NotFoundException as e:
            return JSONResponse(
                status_code=404,
                content={"detail": str(e)},
            )
        except Exception as exc:
            traceback.print_exc()
            return JSONResponse(
                status_code=500,
                content={"detail": "An unexpected error occurred."},
            )
