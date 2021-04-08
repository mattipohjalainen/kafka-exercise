package personal.matti.kafkaexercise.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import personal.matti.kafkaexercise.entity.AlarmMessage
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

//@EnableKafka
//@Configuration
class AlarmMessageConfig {
    //@Bean
    fun objectMapper(): ObjectMapper =
        ObjectMapper()
            .registerModule(JavaTimeModule())
            .registerModule(ParameterNamesModule())
            .registerModule(Jdk8Module())
            .registerModule(KotlinModule())

//    @Bean
//    fun myMessageListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, AlarmMessage> {
//        val factory: ConcurrentKafkaListenerContainerFactory<String, AlarmMessage> =
//            ConcurrentKafkaListenerContainerFactory()
//        factory.consumerFactory = consumerFactory()
//        return factory
//    }

    private fun consumerFactory(): ConsumerFactory<String, AlarmMessage> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.GROUP_ID_CONFIG] = "alarm-message-consumer"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        return DefaultKafkaConsumerFactory(
            props, StringDeserializer(), JsonDeserializer<AlarmMessage>(
                AlarmMessage::class.java
            )
        )
    }
}
