SHELL ?=/bin/bash

check-command = type ${1} >/dev/null 2>&1 || { echo "Please install ${1}"; exit 1; };

FZF_MULTI := --cycle --multi --bind="space:toggle"

MVN=mvn
GRADLE=./gradlew
JAVA=java
JAR=target/co2-calculator-1.0-SNAPSHOT.jar

.DEFAULT:
_recipe-list:
	@$(call check-command, fzf)
	@recipe=$$(grep -oE '^[a-z][a-zA-Z0-9-]+:' Makefile | tr -d ':' \
	| fzf --ignore-case --preview 'make --silent -n {} | head -n 10'); \
	[[ -n "$$recipe" ]] && make --silent $$recipe

all: _recipe-list

build:
	@echo "Building project with Maven..."
	$(MVN) clean package

test:
	@echo "Running tests..."
	$(MVN) test

run:
	@echo "Running application with user inputs..."
	@read -p "Enter starting city: " START_CITY; \
	read -p "Enter destination city: " END_CITY; \
	read -p "Enter transportation method: " TRANSPORT_METHOD; \
	$(JAVA) -jar $(JAR) --start "$$START_CITY" --end "$$END_CITY" --transportation-method "$$TRANSPORT_METHOD"

clean:
	@echo "Cleaning project..."
	$(MVN) clean

help:
	@echo "Available make commands:"
	@echo "  make build         - Build the project with Maven"
	@echo "  make test          - Run tests"
	@echo "  make run           - Run the application"
	@echo "  make clean         - Clean Maven build artifacts"
