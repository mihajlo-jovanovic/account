# Account Demo Service (Kotlin, SpringBoot, Confluent Kafka)
Demo Account service, which is a simple RESTful API that allows you to create, read, update and delete accounts. 
The service is written in Kotlin using the SpringBoot framework and uses an in-memory HashMap to store the account data.

Used to explore capabilities of the Confluent Kafka platform, around generic event publishing and schema evolution.

## Pre-requisites
For more info on how to setup local Confluent Kafka, see https://www.lydtechconsulting.com/blog-kafka-schema-registry-demo-part1.html 

## Running the service

```
mvn spring-boot:run
```