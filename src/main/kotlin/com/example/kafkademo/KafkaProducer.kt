package com.example.kafkademo

import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Controller
import org.springframework.util.concurrent.ListenableFutureCallback
import kotlin.coroutines.suspendCoroutine

@Controller
class KafkaProducer @Autowired constructor(private val kafkaTemplate: KafkaTemplate<String, Data>) {

    suspend fun sendMessage(id: Int, name: String) {
        val message = Data(id, name)
        kafkaTemplate.dispatch(ProducerRecord(TOPIC_NAME, "$id", message))
        println("sent $message")
    }

}

suspend inline fun <reified K : Any, reified V : Any> KafkaTemplate<K, V>.dispatch(record: ProducerRecord<K, V>) =
        suspendCoroutine<SendResult<K, V>> { continuation ->
            val callback = object : ListenableFutureCallback<SendResult<K, V>> {

                override fun onSuccess(result: SendResult<K, V>?) {
                    result?.let { continuation.resumeWith(Result.success(result)) }
                }

                override fun onFailure(ex: Throwable) {
                    continuation.resumeWith(Result.failure(ex))
                }

            }
            this.send(record).addCallback(callback)
        }
