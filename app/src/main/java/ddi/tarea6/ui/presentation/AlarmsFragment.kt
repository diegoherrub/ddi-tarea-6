package ddi.tarea6.ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ddi.tarea6.ui.R
import ddi.tarea6.ui.data.AlarmsDataRepository
import ddi.tarea6.ui.data.local.AlarmsXmlLocalDataRepository
import ddi.tarea6.ui.domain.Alarm
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class AlarmsFragment : Fragment() {

    private lateinit var alarmsDataRepository: AlarmsDataRepository
    private lateinit var alarmList: MutableList<Alarm>
    private lateinit var adapter: AlarmAdapter
    private var isTimeRemainingHidden = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_alarms)
        val timeRemainingTextView = view.findViewById<TextView>(R.id.text_time_remaining)

        val xmlLocal = AlarmsXmlLocalDataRepository(requireContext())
        alarmsDataRepository = AlarmsDataRepository(xmlLocal)

        alarmList = alarmsDataRepository.getAlarms().toMutableList()
        if (alarmList.isEmpty()) {
            alarmList = mutableListOf(
                Alarm("00:15", "Despertar nocturno", false),
                Alarm("01:00", "Tarea pendiente", false),
                Alarm("02:00", "Recordatorio de medicación", false),
                Alarm("03:30", "Alarma de emergencia", false),
                Alarm("04:15", "Ejercicio temprano", false),
                Alarm("06:30", "Despertador principal", false),
                Alarm("06:45", "Tiempo de preparación", false),
                Alarm("07:00", "Inicio del día", false),
                Alarm("07:45", "Preparativos matutinos", false),
                Alarm("09:30", "Inicio de actividades", false),
                Alarm("10:00", "Pausa corta", false),
                Alarm("10:15", "Tarea programada", false),
                Alarm("10:45", "Reunión", false),
                Alarm("12:15", "Almuerzo", false),
                Alarm("16:00", "Descanso vespertino", false),
                Alarm("18:30", "Hora de salida", false),
                Alarm("18:45", "Tiempo libre", false),
                Alarm("20:45", "Cena", false),
                Alarm("22:45", "Preparativos para dormir", false),
                Alarm("23:15", "Última alarma del día", false)
            )
            alarmsDataRepository.saveAlarms(alarmList)
        }

        fun updateTimeRemaining() {
            if (isTimeRemainingHidden) return

            val currentTime = LocalTime.now()
            val activeAlarms = alarmList.filter { it.isActive }
            if (activeAlarms.isNotEmpty()) {
                val formatter = DateTimeFormatter.ofPattern("HH:mm")
                val closestAlarm = activeAlarms.minByOrNull {
                    val alarmTime = LocalTime.parse(it.hour, formatter)
                    ChronoUnit.MINUTES.between(currentTime, alarmTime).let { diff ->
                        if (diff < 0) diff + 24 * 60 else diff
                    }
                }

                closestAlarm?.let {
                    val alarmTime = LocalTime.parse(it.hour, formatter)
                    val minutesRemaining = ChronoUnit.MINUTES.between(currentTime, alarmTime).let { diff ->
                        if (diff < 0) diff + 24 * 60 else diff
                    }

                    val hours = minutesRemaining / 60
                    val minutes = minutesRemaining % 60
                    timeRemainingTextView.text = when {
                        hours > 0 -> "La alarma sonará en $hours horas y $minutes minutos"
                        else -> "La alarma sonará en $minutes minutos"
                    }
                }
            } else {
                timeRemainingTextView.text = "No hay alarmas activas"
            }
        }

        adapter = AlarmAdapter(alarmList.toMutableList()) { updatedAlarm ->
            val index = alarmList.indexOfFirst { it.hour == updatedAlarm.hour }
            if (index != -1) {
                alarmList[index] = updatedAlarm
                adapter.notifyItemChanged(index)
                alarmsDataRepository.saveAlarms(alarmList)
                updateTimeRemaining()
            }
            Toast.makeText(
                requireContext(),
                "Alarma ${updatedAlarm.hour} ${if (updatedAlarm.isActive) "activada" else "desactivada"}",
                Toast.LENGTH_SHORT
            ).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && !isTimeRemainingHidden) {
                    timeRemainingTextView.visibility = View.GONE
                    isTimeRemainingHidden = true
                } else if (dy < 0 && isTimeRemainingHidden) {
                    timeRemainingTextView.visibility = View.VISIBLE
                    isTimeRemainingHidden = false
                    updateTimeRemaining()
                }
            }
        })

        updateTimeRemaining()
    }
}
