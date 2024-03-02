package com.github.mj.account

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@EmbeddedKafka(
    controlledShutdown = true,
    partitions = 1,
    bootstrapServersProperty = "spring.kafka.bootstrap-servers",
    topics = ["account-state-changes"]
)
@DirtiesContext
@ActiveProfiles("test")
@Import(TestKafkaConfiguration::class)
abstract class AbstractKafkaTest {}