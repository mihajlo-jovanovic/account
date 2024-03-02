package com.github.mj.account

import org.awaitility.Awaitility
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.utils.ContainerTestUtils
import org.springframework.messaging.handler.annotation.Payload
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class AccountProducerTest : AbstractKafkaTest() {

    @Autowired
    lateinit var testKafkaTemplate: KafkaTemplate<String, OutboundEvent>

    @Autowired
    lateinit var registry: KafkaListenerEndpointRegistry

    @Autowired
    lateinit var embeddedKafkaBroker: EmbeddedKafkaBroker

    @Autowired
    lateinit var testReceiver: KafkaTestListener

    @BeforeEach
    fun setup() {
        // Wait until the partitions are assigned.
        registry.listenerContainers.stream().forEach {
            ContainerTestUtils.waitForAssignment(
                it, embeddedKafkaBroker.partitionsPerTopic
            )
        }
    }

    data class OutboundEvent(val sequenceNumber: Long)

    /**
     * Use this receiver to consume messages from the outbound topic.
     */
    class KafkaTestListener {
        var counter: AtomicInteger = AtomicInteger(0)

        @KafkaListener(
            groupId = "EndToEndIntegrationTest",
            topics = ["account-state-changes"],
            properties = ["bootstrap.servers:\${kafka.bootstrap-servers}", "value.deserializer:org.springframework.kafka.support.serializer.JsonDeserializer", "spring.json.value.default.type:com.github.mj.account.AccountProducerTest.OutboundEvent"],
            autoStartup = "true"
        )
        fun receive(@Payload payload: OutboundEvent) {
            println("KafkaTestListener - Received message: $payload")
            counter.incrementAndGet()
        }
    }

    @Test
    fun testConsumeAndProduceEvent() {
        val event = OutboundEvent(1)
        testKafkaTemplate.send("account-state-changes", event).get()

        Awaitility.await().atMost(60, TimeUnit.SECONDS).pollDelay(100, TimeUnit.MILLISECONDS)
            .until({ testReceiver.counter.get() }, Matchers.equalTo(1))
    }
}