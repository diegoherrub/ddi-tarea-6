package ddi.tarea6.ui.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ddi.tarea6.ui.R
import ddi.tarea6.ui.domain.Alarm
import ddi.tarea6.ui.domain.AlarmsRepository

class AlarmsXmlLocalDataRepository(
    private val context: Context
) : AlarmsRepository {

    private val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.alarms_file), Context.MODE_PRIVATE
    )

    private val gson = Gson()
    private val alarmType = object : TypeToken<List<Alarm>>() {}.type

    override fun saveAlarm(alarm: Alarm) {
        val json = gson.toJson(alarm)
        sharedPreferences.edit().putString("alarm_${alarm.hour}", json).apply()
    }

    override fun getAlarms(): List<Alarm> {
        val json = sharedPreferences.getString("alarms", null) ?: return emptyList()
        return gson.fromJson(json, alarmType)
    }

    override fun saveAlarms(alarms: List<Alarm>) {
        val json = gson.toJson(alarms)
        sharedPreferences.edit().putString("alarms", json).apply()
    }
}
