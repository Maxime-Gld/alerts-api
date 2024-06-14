# Utilisation des profils Spring Boot avec Makefile

.PHONY: all debug noTests surefire javadoc force

# lancement de l'application par defaut
all: build

# compilation et lancement
build:
	mvn clean package
	mvn surefire-report:report-only
	mvn spring-boot:run

# lancement en mode debug
debug:
	mvn spring-boot:run -Dspring-boot.run.profiles=debug

# lancement sans tests
noTests: 
	mvn spring-boot:run -DskipTests

# lancement des tests + rapport
surefire: 
	mvn surefire-report:report

# lancement du javadoc
javadoc: 
	mvn javadoc:javadoc

