package com.example.kafkademo

import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer


@EnableKafka
@Configuration
class KafkaConsumerConfig  @Autowired constructor(
        @Value(value = "\${kafka.bootstrapAddress}")
        private val bootstrapAddress: String
) {

    @Bean
    fun greetingConsumerFactory(): ConsumerFactory<String, Data> {

        val props =  mapOf<String, Any>(
                BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress,
                KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java
        )

        return DefaultKafkaConsumerFactory(props, StringDeserializer(), JsonDeserializer(Data::class.java))
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Data> {
        return ConcurrentKafkaListenerContainerFactory<String, Data>().apply {
            consumerFactory = greetingConsumerFactory()
        }
    }

}