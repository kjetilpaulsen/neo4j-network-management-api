.PHONY: build run test clean

build:
	./gradlew :app:build

run:
	./gradlew :app:run

test:
	./gradlew :app:test

clean:
	./gradlew clean
