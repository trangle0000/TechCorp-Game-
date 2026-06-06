#!/bin/bash
mvn clean package -DskipTests -q
java -jar target/techcorp-game-1.0.0.jar
