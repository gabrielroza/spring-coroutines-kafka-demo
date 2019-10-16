package com.example.kafkademo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kafka")
class KafkaController @Autowired constructor(private val kafkaProducer: KafkaProducer) {

    @GetMapping("/{id}/{name}")
    suspend fun get(@PathVariable id: Int, @PathVariable name: String) {
        kafkaProducer.sendMessage(id, name)
    }

}