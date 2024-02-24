package com.github.mj.account.open

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component

@Component
class KafkaClient {
    private val outboundTopic = "account-state-changes"

    @Autowired
    private val kafkaTemplate: KafkaTemplate<String, GenericRecord>? = null

    fun sendMessage(key: String, outboundEvent: GenericRecord): SendResult<*, *> {
        try {
            val record: ProducerRecord<String, GenericRecord> =
                ProducerRecord(outboundTopic, key, outboundEvent)
            val result = kafkaTemplate!!.send(record).get() as SendResult<*, *>
            val metadata = result.recordMetadata
            println("Sent record(key=${record.key()}, value=${record.value()}) meta(topic=${metadata.topic()}, partition=${metadata.partition()}, offset=${metadata.offset()})")
            return result
        } catch (e: Exception) {
            val message = "Error sending message to topic $outboundTopic"
            throw RuntimeException(message, e)
        }
    }
}