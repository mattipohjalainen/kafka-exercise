package personal.matti.kafkaexercise.entity

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

data class AlarmMessage(val alarmingNode: String, val alarmType: String, val alarmTime: Date) {
    constructor() : this(
        "Node?",
        "Type?",
        Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
    )
}
