export PWD := $(shell pwd)
export PYTHONPATH := $(PWD)
VENV_PATH := ./venv

migrate: venv
	alembic upgrade head
	alembic revision --autogenerate -m "Initial migration"
	alembic upgrade head

run: venv
	uvicorn adapters.api.api:app --reload

.PHONY: setup-venv
setup-venv:
	if [ ! -d "$(VENV_PATH)" ]; then \
		python3 -m venv $(VENV_PATH); \
	fi

.PHONY: venv
venv: setup-venv
	$(VENV_PATH)/bin/pip install -r requirements.txt

test: venv
	$(VENV_PATH)/bin/pytest
