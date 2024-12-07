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

class AlarmsFragment : Fragment() {

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

        // Lista de alarmas de prueba
        val alarmList = listOf("07:00 AM", "08:30 AM", "09:45 AM", "11:00 AM", "12:30 PM")

        // Configurar RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = AlarmAdapter(alarmList) { alarm, isActive ->
            val message = if (isActive) {
                "Alarma $alarm activada"
            } else {
                "Alarma $alarm desactivada"
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

}