# Utilisation des profils Spring Boot avec Makefile

.PHONY: all build run debug noTests force

# lancement de l'application par defaut
all: build

# compilation et lancement
build:
	mvn clean package
	mvn spring-boot:run

# lancement en mode debug
debug:
    mvn spring-boot:run -Dspring-boot.run.profiles=debug

# lancement sans tests
noTests:
	mvn spring-boot:run -DskipTests


