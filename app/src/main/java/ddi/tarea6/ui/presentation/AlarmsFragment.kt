package ddi.tarea6.ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ddi.tarea6.ui.R
import ddi.tarea6.ui.data.AlarmsDataRepository
import ddi.tarea6.ui.data.local.AlarmsXmlLocalDataRepository
import ddi.tarea6.ui.domain.Alarm
import ddi.tarea6.ui.domain.AlarmsRepository

class AlarmsFragment : Fragment() {

    private lateinit var alarmsDataRepository: AlarmsDataRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_alarms)

        val xmlLocal = AlarmsXmlLocalDataRepository(requireContext())
        alarmsDataRepository = AlarmsDataRepository(xmlLocal)

        // Cargar lista de alarmas
        var alarmList = alarmsDataRepository.getAlarms()

        if (alarmList.isEmpty()) {
            alarmList = listOf(
                Alarm("07:00", false),
                Alarm("08:30", false),
                Alarm("09:45", false),
                Alarm("11:00", false),
                Alarm("12:30", false)
            )
            alarmsDataRepository.saveAlarms(alarmList)
        }

        // Configurar RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = AlarmAdapter(alarmList) { updatedAlarm ->
            // Actualizar lista y guardar cambios
            alarmList = alarmList.map { if (it.hour == updatedAlarm.hour) updatedAlarm else it }
            alarmsDataRepository.saveAlarms(alarmList)

            // Mostrar mensaje
            val message = if (updatedAlarm.isActive) {
                "Alarma ${updatedAlarm.hour} activada"
            } else {
                "Alarma ${updatedAlarm.hour} desactivada"
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}