# Introduction
Simple backend for showing different approaches to read and write into a database

## Getting Started with Docker
Start the backend without building it:
````bash
$ docker-compose -f local.yml up backend
````
Start the backend and building it:
````bash
$ docker-compose -f local.yml up --build backend
````
Stop all container:
````bash
$ docker-compose -f local.yml down
````
Stop all container and delete volume (data):
````bash
$ docker-compose -f local.yml down -v
````

## Getting started locally
Install [anaconda](https://docs.anaconda.com/anaconda/install/mac-os/) for managing multiple environments. 
After that create an environment:
````bash
$ conda create -n lecture python=3.10
````
Activate the environment:
````bash
$ conda activate lecture
````
Install python packages:
````bash
$ pip install -r backend/requirements/local.txt
````
Start the postgres:
````bash
$ docker-compose -f local.yml up db
````
Start the app:
````bash
$ cd backend && python manage.py runserver
````
Optional: Set up the environment in pycharm:
````bash
$ Settings > Project: lecture_ws_2021 > Select env or add new one > 
In case of adding a new env > select conda env > existing > ~/Users/Anaconda3/envs/lecture_ws_2021/python.exe
````

## Run tests
In docker without and with coverage:
````bash
$ docker-compose -f local.yml exec backend python manage.py test
````
````bash
$ docker-compose -f local.yml exec backend coverage run manage.py test
````
````bash
$ docker-compose -f local.yml exec backend coverage report
````
Locally without and with coverage:
````bash
$ cd backend && python manage.py test
````
````bash
$ coverage run manage.py test
````
````bash
$ coverage report
````

## Run linting
In docker:
````bash
$ docker-compose -f local.yml exec backend mypy
````
````bash
$ docker-compose -f local.yml exec backend flake8
````
````bash
$ docker-compose -f local.yml exec backend pydocstyle
````
Locally:
````bash
$ cd backend && mypy
````
````bash
$ cd backend && flake8
````
````bash
$ cd backend && pydocstyle
````

## Documentation
The api is documented with swagger and redoc
-  http://localhost:8000/swagger/
-  http://localhost:8000/redoc/

## Admin view
The django admin view is available on 
-  http://localhost:8000/admin/   

To access it create a super user:
````bash
$ docker-compose -f local.yml exec backend python manage.py createsuperuser
````
````bash
$ cd backend && python manage.py createsuperuser
````

## Folder structure
-  [backend/urls.py](backend/backend/urls.py): Connect the controller/service to a route
-  [backend/settings.py](backend/backend/settings.py): Register installed apps 
(also own created ones), add middlewares
-  app/documentation: Everything for swagger should go here
-  app/serializer: All serializers (DTOs) belong here
-  app/tests: Tests for the app
-  app/models: Models (Database tables) go here
- app/views: The logic for the route should be here. Is like 
a controller and service in one
-  app/admin: Register the models (database tables) in the django admin view