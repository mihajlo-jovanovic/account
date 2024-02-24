package com.github.mj.account.open

import org.apache.avro.Schema
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericRecord
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class AccountService(val repository: AccountRepository, val kafkaClient: KafkaClient? = null) {

    fun open(account: Account) {
        println("Opening account: $account")
        val parser: Schema.Parser = Schema.Parser()
        // this would normally be done by pulling the schema from a schema registry
        val schema =
            parser.parse("{\"type\":\"record\",\"name\":\"AccountCreated\",\"namespace\":\"demo.kafka.event\",\"fields\":[{\"name\":\"account_id\",\"type\":\"string\"},{\"name\":\"description\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"currency\",\"type\":\"string\"},{\"name\":\"to_account\",\"type\":\"string\"},{\"name\":\"from_account\",\"type\":\"string\"}],\"version\":\"1\"}")
        println("schema: $schema")
        // build GenericRecord from schema
        val record: GenericRecord = GenericData.Record(schema)
        record.put("account_id", account.id)
//        record.put("amount", Double.MAX_VALUE)
        record.put("description", "My fav account")
        record.put("currency", "USD")
        record.put("from_account", Random.nextInt(10000, 99999).toString())
        record.put("to_account", Random.nextInt(10000, 99999).toString())
        println("record: $record")
        repository.persist(account)
        kafkaClient?.sendMessage(account.id, record)
    }

    fun getAccount(id: String): Account {
        return repository.getAccount(id)!!
    }
}