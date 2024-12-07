package ddi.tarea6.ui.data

import ddi.tarea6.ui.data.local.AlarmsXmlLocalDataRepository
import ddi.tarea6.ui.domain.Alarm
import ddi.tarea6.ui.domain.AlarmsRepository

class AlarmsDataRepository(
    private val localXml: AlarmsXmlLocalDataRepository
) : AlarmsRepository {

    override fun saveAlarm(alarm: Alarm) {
        val alarms = getAlarms().toMutableList()
        val index = alarms.indexOfFirst { it.hour == alarm.hour }
        if (index != -1) {
            alarms[index] = alarm
        } else {
            alarms.add(alarm)
        }
        localXml.saveAlarm(alarm)
    }

    override fun saveAlarms(alarms: List<Alarm>) {
        localXml.saveAlarms(alarms)
    }

    override fun getAlarms(): List<Alarm> {
        return localXml.getAlarms()
    }
}