package com.example.kafkademo

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer {

    @KafkaListener(topics = [TOPIC_NAME], groupId = "foo")
    fun listen(greeting: Data) {
        println("Received $greeting")
    }

}