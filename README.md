[![Build Status](https://travis-ci.org/garethahealy/quota-limits-generator.svg?branch=master)](https://travis-ci.org/garethahealy/quota-limits-generator)
[![Release Version](https://img.shields.io/maven-central/v/com.garethahealy.quota-limits-generator/quota-limits-generator.svg?maxAge=2592000)](https://mvnrepository.com/artifact/com.garethahealy.quota-limits-generator/quota-limits-generator-parent)
[![License](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)]()

# quota-limits-generator
A tool to generate YAML files for OCP quotas and limits based of pre-defined inputs.

## Build
mvn clean install

## Run
java -jar target/quota-limits-generator-parent-*-jar-with-dependencies.jar \
    --instance-type-csv file:$(PWD)/data/instancetypes.csv --instance-type small \
    --node-cores 4 --node-memory 8 --node-reserved-cores 1 --node-reserved-memory 2 \
    --output ${PWD}/output
