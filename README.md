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

## Learnings Notes

Can't use JsonSerializer for GenericRecords in EmbeddedKafka tests, as it apparently is not an array:

```
schema: {"type":"record","name":"AccountCreated","namespace":"demo.kafka.event","fields":[{"name":"account_id","type":"string"},{"name":"description","type":["null","string"],"default":null},{"name":"currency","type":"string"},{"name":"to_account","type":"string"},{"name":"from_account","type":"string"}],"version":"1"}

org.apache.kafka.common.errors.SerializationException: Can't serialize data [{"account_id": "1234", "description": "My fav account", "currency": "USD", "to_account": "80834", "from_account": "71116"}] for topic [account-state-changes]
	at org.springframework.kafka.support.serializer.JsonSerializer.serialize(JsonSerializer.java:216)
	at org.springframework.kafka.support.serializer.JsonSerializer.serialize(JsonSerializer.java:203)

```