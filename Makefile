# Utilisation des profils Spring Boot avec Makefile

.PHONY: build run debug noTests

# lancement de l'application par defaut
all: build run

# compilation
build:
	mvn clean package

# lancement
run:
    mvn spring-boot:run 

# lancement en mode debug
debug:
    mvn spring-boot:run -Dspring-boot.run.profiles=debug

# lancement sans tests
noTests:
	mvn spring-boot:run -DskipTests


