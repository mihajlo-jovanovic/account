package com.github.mj.account

import org.apache.kafka.clients.admin.AdminClientConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@SpringBootApplication
class AccountApplication

fun main(args: Array<String>) {
    runApplication<AccountApplication>(*args)
}

@Bean
fun admin() = KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092"))

@Bean
fun topic1() =
    TopicBuilder.name("account-state-changes")
        .partitions(10)
        .replicas(3)
        .compact()
        .build()
