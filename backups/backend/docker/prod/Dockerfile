FROM python:3.9.6-slim

RUN apt-get update && apt-get -y install gcc && apt-get install -y libpq-dev

WORKDIR /app

COPY requirements/base.txt ./
COPY requirements/prod.txt ./
RUN pip install --no-cache-dir -r prod.txt
COPY . .

ENV PYTHONPATH=/app
ENV PYTHONUNBUFFERED 1
ENV PYTHONDONTWRITEBYTECODE 1

EXPOSE 8000
CMD [ "python", "manage.py", "runserver", "0.0.0.0:8000"]