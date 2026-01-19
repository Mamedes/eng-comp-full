# Usa uma imagem do Maven com Java 21
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

ENV LANG=en_US.UTF-8 \
    LANGUAGE=en_US.UTF-8 \
    LC_ALL=en_US.UTF-8 \
    TZ=America/Cuiaba

RUN apk add --no-cache tzdata \
    && ln -sf /usr/share/zoneinfo/${TZ} /etc/localtime \
    && echo "${TZ}" > /etc/timezone

# Define o diretório de trabalho para o build
WORKDIR /app

COPY .. .


# Executa o build na raiz do projeto
RUN mvn clean package -DskipTests

# Cria um usuário para rodar a aplicação
RUN addgroup -S seletivo && adduser -S -G seletivo seletivo

USER seletivo

# Cria a imagem final
FROM eclipse-temurin:21-jdk-alpine AS runtime

COPY ../infra/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app.jar"]