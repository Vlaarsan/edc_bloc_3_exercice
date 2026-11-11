# ============================================================================
# Dockerfile - Project Management Tool Backend
# ============================================================================

# --- Build stage ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Cache des dépendances Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B -q

# Compilation et packaging
COPY src ./src
RUN mvn package -DskipTests -B -q

# --- Run stage ---
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copie du jar généré
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
