package com.example.kafkademo

import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
class KafkaProducerConfig @Autowired constructor(
        @Value(value = "\${kafka.bootstrapAddress}")
        private val bootstrapAddress: String
) {

    @Bean
    fun greetingProducerFactory(): ProducerFactory<String, Data> {
        return mapOf<String, Any>(
                BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress,
                KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        ).let(::DefaultKafkaProducerFactory)
    }

    @Bean
    fun greetingKafkaTemplate(): KafkaTemplate<String, Data> = KafkaTemplate(greetingProducerFactory())

}