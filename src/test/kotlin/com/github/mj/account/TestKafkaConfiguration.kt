package com.github.mj.account

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class TestKafkaConfiguration {

    @Bean
    fun testProducerFactory(
        @Value("\${kafka.bootstrap-servers}") bootstrapServers: String,
        @Value("\${kafka.schema.registry.url}") schemaRegistryUrl: String
    ): ProducerFactory<String, AccountProducerTest.OutboundEvent> {
        val config: MutableMap<String, Any> = HashMap()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
//        config[KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = schemaRegistryUrl
//        config[KafkaAvroSerializerConfig.AUTO_REGISTER_SCHEMAS] = false
//        config["batch.size"] = 1
//        config["linger.ms"] = 5000
//        config["request.timeout.ms"] = 5000
//        config["delivery.timeout.ms"] = 10000
//        config["enable.idempotence"] = false
        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun testReceiver(): AccountProducerTest.KafkaTestListener {
        return AccountProducerTest.KafkaTestListener()
    }

    @Bean
    fun testKafkaTemplate(producerFactory: ProducerFactory<String, AccountProducerTest.OutboundEvent>): KafkaTemplate<String, AccountProducerTest.OutboundEvent> {
        return KafkaTemplate(producerFactory)
    }
}