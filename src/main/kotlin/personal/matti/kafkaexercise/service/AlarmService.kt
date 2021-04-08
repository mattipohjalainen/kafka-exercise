package personal.matti.kafkaexercise.service

import org.springframework.stereotype.Service
import personal.matti.kafkaexercise.dao.AlarmProducer
import personal.matti.kafkaexercise.dao.AlarmReader
import personal.matti.kafkaexercise.dto.AlarmDto
import personal.matti.kafkaexercise.entity.AlarmMessage
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@Service
class AlarmService(private val alarmProducer: AlarmProducer, private val alarmReader: AlarmReader) {
    fun sendAlarm(alarmingNode: String, alarmType: String, alarmTime: LocalDateTime) {
        val message = AlarmMessage(
            alarmingNode = alarmingNode,
            alarmType = alarmType,
            alarmTime = toDate(alarmTime)
        )
        alarmProducer.send(message)
    }

    fun getAlarms(amount: Int): List<AlarmDto> {
        return alarmReader.readAlarms(amount).map {
                alarm -> AlarmDto(alarm.alarmingNode, alarm.alarmType, toLocalDate(alarm.alarmTime))
        }
    }

    private fun toDate(localDateTime: LocalDateTime) =
        Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())

    private fun toLocalDate(date: Date) = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
}
