package ddi.tarea6.ui.domain

interface AlarmsRepository {

    fun saveAlarm(alarm: Alarm)

    fun saveAlarms(alarms: List<Alarm>)

    fun getAlarms(): List<Alarm>
}