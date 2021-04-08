package personal.matti.kafkaexercise.dao

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.stereotype.Component
import personal.matti.kafkaexercise.entity.AlarmMessage
import java.time.Duration

@Component
class AlarmReader {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val topicPartition: TopicPartition = TopicPartition(TOPIC_NAME, 0)

    private val kafkaConsumer: Consumer<String, AlarmMessage> = consumerFactory().createConsumer().apply {
        val partitions = this.partitionsFor(TOPIC_NAME).map { partitionInfo ->
            TopicPartition(TOPIC_NAME, partitionInfo.partition())
        }
        this.assign(partitions)
    }

    fun readAlarms(amount: Int): List<AlarmMessage> {
        kafkaConsumer.seekToEnd(kafkaConsumer.assignment())
        val endPosition = kafkaConsumer.position(topicPartition)
        val seekPosition = endPosition - amount
        val recentMessagesStartPosition: Long = if (seekPosition >= 0) seekPosition else 0
        kafkaConsumer.seek(topicPartition, recentMessagesStartPosition)

        val alarms = kafkaConsumer.poll(Duration.ofSeconds(2))

        logger.info("Got ${alarms.count()} alarms")

        return alarms.records(topicPartition).map { it.value() }
    }

    private fun consumerFactory(): ConsumerFactory<String, AlarmMessage> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.GROUP_ID_CONFIG] = "alarm-message-reader"
        props["bootstrap.servers"] = "localhost:9092"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        return DefaultKafkaConsumerFactory(
            props, StringDeserializer(), JsonDeserializer<AlarmMessage>(
                AlarmMessage::class.java
            )
        )
    }

    companion object {
        const val TOPIC_NAME = "alarmTopic"
    }
}
