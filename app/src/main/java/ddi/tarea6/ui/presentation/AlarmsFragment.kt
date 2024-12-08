package ddi.tarea6.ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ddi.tarea6.ui.R
import ddi.tarea6.ui.data.AlarmsDataRepository
import ddi.tarea6.ui.data.local.AlarmsXmlLocalDataRepository
import ddi.tarea6.ui.domain.Alarm

class AlarmsFragment : Fragment() {

    private lateinit var alarmsDataRepository: AlarmsDataRepository
    private lateinit var alarmList: MutableList<Alarm>
    private lateinit var adapter: AlarmAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_alarms)

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

        adapter = AlarmAdapter(alarmList.toMutableList()) { updatedAlarm ->
            val index = alarmList.indexOfFirst { it.hour == updatedAlarm.hour }
            if (index != -1) {
                alarmList[index] = updatedAlarm
                adapter.notifyItemChanged(index)
                alarmsDataRepository.saveAlarms(alarmList)
            }
            Toast.makeText(
                requireContext(),
                "Alarma ${updatedAlarm.hour} ${if (updatedAlarm.isActive) "activada" else "desactivada"}",
                Toast.LENGTH_SHORT
            ).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

//        val motionLayout = requireActivity().findViewById<MotionLayout>(R.id.motion_layout_alarms)
//
//        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                val progress = (recyclerView.computeVerticalScrollOffset() / 500f).coerceIn(0f, 1f)
//                motionLayout.progress = progress
//            }
//        })
    }
}