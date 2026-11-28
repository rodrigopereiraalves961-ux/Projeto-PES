# Atalhos para os comandos mais usados (para pessoas e para o agente).

.PHONY: help verify build run docker clean

# Escrever só "make" mostra esta lista.
.DEFAULT_GOAL := help

## Mostra os comandos disponíveis
help:
	@awk '/^## /{desc=substr($$0,4); next} /^[a-z]+:/{if(desc){split($$0,t,":"); printf "  %-8s %s\n", t[1], desc}} {desc=""}' $(MAKEFILE_LIST)

## Corre o pipeline completo
verify:
	cd harness && mvn -B verify

## Gera o harness.jar sem correr os testes
build:
	cd harness && mvn -B -q package -DskipTests

## Corre a harness (ex.: make run ARGS=help)
run: build
	java -jar harness/target/harness.jar $(ARGS)

## Constrói a imagem Docker
docker:
	docker build -t harness harness

## Apaga a pasta target
clean:
	cd harness && mvn -B -q clean