FROM maven:3.8.1-openjdk-17-slim
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests -q
CMD java -jar target/techcorp-game-1.0.0.jar
