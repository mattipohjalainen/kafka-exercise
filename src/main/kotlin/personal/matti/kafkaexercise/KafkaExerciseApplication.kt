package personal.matti.kafkaexercise

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka
import personal.matti.kafkaexercise.config.CustomConfigs


@SpringBootApplication
@EnableKafka
@EnableConfigurationProperties(CustomConfigs::class)
class KafkaExeciseApplication

fun main(args: Array<String>) {
	runApplication<KafkaExeciseApplication>(*args)
}
