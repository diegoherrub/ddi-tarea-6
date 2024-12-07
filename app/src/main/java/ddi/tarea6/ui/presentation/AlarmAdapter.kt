package ddi.tarea6.ui.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ddi.tarea6.ui.R
import ddi.tarea6.ui.domain.Alarm

class AlarmAdapter(
    private val alarms: MutableList<Alarm>, // MutableList para permitir actualizaciones
    private val onToggle: (Alarm) -> Unit
) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    inner class AlarmViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val alarmTime: TextView = view.findViewById(R.id.text_view_hour)
        val alarmsDescription: TextView = view.findViewById(R.id.text_view_description)
        val alarmSwitch: Switch = view.findViewById(R.id.switch_alarm)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]

        // Evitar duplicación de listeners
        holder.alarmSwitch.setOnCheckedChangeListener(null)

        // Configurar datos
        holder.alarmTime.text = alarm.hour
        holder.alarmsDescription.text = alarm.description
        holder.alarmSwitch.isChecked = alarm.isActive

        // Listener del Switch
        holder.alarmSwitch.setOnCheckedChangeListener { _, isChecked ->
            val updatedAlarm = alarm.copy(isActive = isChecked)

            // Actualizar datos internos
            alarms[position] = updatedAlarm

            // Notificar cambio
            notifyItemChanged(position)

            // Callback para actualizar datos externos
            onToggle(updatedAlarm)
        }
    }

    override fun getItemCount(): Int = alarms.size
}
