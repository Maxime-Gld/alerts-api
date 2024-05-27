#!/bin/bash

mvn clean package
java -jar target/alerts-api-0.0.1-SNAPSHOT.jar