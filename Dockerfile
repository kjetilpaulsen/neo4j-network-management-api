# syntax=docker/dockerfile:1

FROM gradle:8.7-jdk17 AS build
WORKDIR /workspace

# Gradle wrapper at repo root
COPY gradlew ./
COPY gradle ./gradle

# Root settings (must exist in this case)
COPY settings.gradle.kts ./

# App module
COPY app/build.gradle.kts ./app/
COPY app/src ./app/src

# Build the app module (':app' must match your module name)
RUN ./gradlew --no-daemon :app:clean :app:installDist


FROM eclipse-temurin:17-jre
WORKDIR /app


# installDist output for module 'app'
COPY --from=build /workspace/app/build/install /app


# Adjust if the generated folder isn't named 'app'
CMD ["/app/app/bin/app"]
