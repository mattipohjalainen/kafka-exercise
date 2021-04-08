package personal.matti.kafkaexercise.dto

import java.time.LocalDateTime

data class AlarmDto(val alarmingNode: String, val alarmType: String, val alarmTime: LocalDateTime?)


