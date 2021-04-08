package personal.matti.kafkaexercise.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import personal.matti.kafkaexercise.dto.AlarmDto
import personal.matti.kafkaexercise.service.AlarmService
import java.time.LocalDateTime

@RestController
@RequestMapping("/api")
class MessageApi(private val kafkaService: AlarmService) {
  @PostMapping("/message")
  fun publish(@RequestBody input: AlarmDto) {
    kafkaService.sendAlarm(
      input.alarmingNode,
      input.alarmType,
      input.alarmTime?.let { it } ?: LocalDateTime.now()
    )
  }

  @GetMapping("get-alarms")
  fun getAlarms(@RequestParam(required = false) maxAmount: Int?): List<AlarmDto> {
    return kafkaService.getAlarms(maxAmount ?: DEFAULT_MAX_AMOUNT_OF_ALARMS)
  }

  companion object {
    const val DEFAULT_MAX_AMOUNT_OF_ALARMS = 20
  }
}
