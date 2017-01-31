[![Build Status](https://travis-ci.org/garethahealy/quota-limits-generator.svg?branch=master)](https://travis-ci.org/garethahealy/quota-limits-generator)
[![Release Version](https://img.shields.io/maven-central/v/com.garethahealy.quota-limits-generator/quota-limits-generator-parent.svg?maxAge=2592000)](https://mvnrepository.com/artifact/com.garethahealy.quota-limits-generator/quota-limits-generator-parent)
[![License](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)]()

# quota-limits-generator
A tool to generate YAML files for OCP quotas and limits based of pre-defined inputs.

---

## From Source
### Build
```Bash
mvn clean install
```

### Run
```Bash
java -jar target/quota-limits-generator-parent-*-jar-with-dependencies.jar \
    --instance-type-csv file:$(PWD)/data/instancetypes.csv --quality-of-service besteffort --instance-type small \
    --node-worker-count 2 --is-team-namespace true --request-ratio 3 \
    --output ${PWD}/output
```

## From Release
### Run
```Bash
    curl -L http://central.maven.org/maven2/com/garethahealy/quota-limits-generator/quota-limits-generator-parent/1.0.1/quota-limits-generator-parent-1.0.1-jar-with-dependencies.jar -o quota-limits-generator.jar
```
```Bash
java -jar quota-limits-generator.jar \
    --instance-type-csv file:$(PWD)/data/instancetypes.csv --quality-of-service besteffort --instance-type small \
    --node-worker-count 2 --is-team-namespace true --request-ratio 3 \
    --output ${PWD}/output
```

## Options explained
Path to instance type CSV

```Bash
--instance-type-csv file:$(PWD)/data/instancetypes.csv 
```

Quality of service required for the project
* besteffort = no requests or limits required to be set.
* burstable = forces the developer to use requests and limits

```Bash
--quality-of-service besteffort
```
How much CPU and memory to use
```Bash
--instance-type small
```
Number of ndoes in the cluster
```Bash
--node-worker-count 2 
```
Whether the limits/quotas apply to a group of OCP projects (true) or just a single project (false)
```Bash
--is-team-namespace true
```
How much the limit can be above the request, i.e.: 
* request = 10mb
* limit = 30mb MAX

In the above example, if you requested a limit of 31mb, the pod would fail.
```Bash
--request-ratio 3
```
Output to where the YAML files will be created
```Bash
--output ${PWD}/output
```
