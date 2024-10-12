# Use the official OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file to the container
COPY target/battelShip-0.0.1-SNAPSHOT.jar battleship-game.jar


# Specify the command to run the JAR file
ENTRYPOINT ["java", "-jar", "battleship-game.jar"]
