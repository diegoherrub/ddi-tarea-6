package ddi.tarea6.ui.domain

class SwitchAlarmUseCase(
    private val alarmsRepository: AlarmsRepository
) {

    operator fun invoke(alarm: Alarm) {
        val alarms = alarmsRepository.getAlarms()
        alarms.find { it.hour == alarm.hour }
        val toggledAlarm = alarm.copy(isActive = !alarm.isActive)
        alarmsRepository.saveAlarm(toggledAlarm)
    }
}