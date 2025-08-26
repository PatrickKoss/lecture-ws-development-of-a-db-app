.PHONY: run build test clean bootrun

run: build
	SERVER_PORT=8081 ./gradlew bootRun

build:
	./gradlew build

test:
	./gradlew test

clean:
	./gradlew clean

bootrun:
	SERVER_PORT=8081 ./gradlew bootRun
