package com.example.kafkademo

import org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

const val TOPIC_NAME = "topic"

@Configuration
class KafkaTopicConfig {

    @Value(value = "\${kafka.bootstrapAddress}")
    private val bootstrapAddress: String? = null

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        return KafkaAdmin(mutableMapOf<String, Any?>(BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress))
    }

    @Bean
    fun topic1(): NewTopic {
        return NewTopic(TOPIC_NAME, 1, 1.toShort())
    }
}