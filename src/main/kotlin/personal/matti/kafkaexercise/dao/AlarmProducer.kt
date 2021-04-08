package personal.matti.kafkaexercise.dao

import org.slf4j.LoggerFactory
import personal.matti.kafkaexercise.entity.AlarmMessage
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class AlarmProducer(private val kafkaTemplate: KafkaTemplate<String, AlarmMessage>) {
  private val logger = LoggerFactory.getLogger(javaClass)

  fun send(message: AlarmMessage) {
    kafkaTemplate.sendDefault(message)
    logger.info("sent $message to kafka")
  }
}
