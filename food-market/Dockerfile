FROM eclipse-temurin:17

LABEL mentainer="maulanamasiqbal@gmail.com"

WORKDIR /app

COPY target/food-market-0.0.1-SNAPSHOT.jar /app/food-market.jar

ENTRYPOINT ["java", "-jar", "food-market.jar"]