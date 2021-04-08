package personal.matti.kafkaexercise.listener

import personal.matti.kafkaexercise.entity.AlarmMessage
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class AlarmConsumer {

  private val logger = LoggerFactory.getLogger(javaClass)

  @KafkaListener(
    topics = ["\${spring.kafka.template.default-topic}"],
    autoStartup = "\${custom-configs.auto-start:true}"
  )
  fun processMessage(message: AlarmMessage) {
    logger.info("got message: {}", message)
  }
}
