package ddi.tarea6.ui.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ddi.tarea6.ui.R

class AlarmAdapter(
    private val alarms: List<String>, // Lista de alarmas
    private val onToggle: (String, Boolean) -> Unit // Función para manejar activaciones/desactivaciones
) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    // ViewHolder para cada ítem
    inner class AlarmViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val alarmTime: TextView = view.findViewById(R.id.tv_alarm_time)
        val alarmSwitch: Switch = view.findViewById(R.id.switch_alarm)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]
        holder.alarmTime.text = alarm

        // Manejar cambios en el interruptor
        holder.alarmSwitch.setOnCheckedChangeListener { _, isChecked ->
            onToggle(alarm, isChecked)
        }
    }

    override fun getItemCount(): Int = alarms.size
}
