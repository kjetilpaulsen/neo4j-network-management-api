# syntax=docker/dockerfile:1

# ---------- Build stage ----------
FROM gradle:8.7-jdk17 AS build
WORKDIR /workspace

# Copy only app build files first (better caching)
COPY app/build.gradle.kts app/settings.gradle.kts* app/gradle.properties* ./
COPY app/gradle ./gradle
COPY app/gradlew ./gradlew

# Pre-fetch dependencies
RUN ./gradlew --no-daemon dependencies || true

# Copy source
COPY app/src ./src

# Build distribution
RUN ./gradlew --no-daemon clean installDist


# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /workspace/build/install /app

# Replace "app" if your project name differs
CMD ["/app/app/bin/app"]
