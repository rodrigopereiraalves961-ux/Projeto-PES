# syntax=docker/dockerfile:1

# Build e verificação de qualidade
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app

# Copia só o pom.xml para aproveitar a cache de camadas do Docker:
# as dependências só voltam a ser descarregadas quando o pom.xml muda.
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

# Copia o código e corre o pipeline completo (Checkstyle, testes, JaCoCo, SpotBugs).
# Se alguma falhar, a imagem não é criada.
COPY src ./src
RUN mvn -B verify

# Imagem final, só com o necessário para executar
FROM eclipse-temurin:21.0.13.1_2-jre-noble
WORKDIR /app

# Cria um utilizador sem privilégios e corre a aplicação com ele (não como root).
RUN useradd --system --no-create-home harness
USER harness

# Copia só o .jar gerado na build.
COPY --from=build /app/target/harness.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]