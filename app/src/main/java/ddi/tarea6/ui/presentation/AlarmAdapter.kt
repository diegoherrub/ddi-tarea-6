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
    private val alarms: List<Alarm>,
    private val onToggle: (Alarm) -> Unit // Función para manejar activaciones/desactivaciones
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

        holder.alarmSwitch.setOnCheckedChangeListener(null)
        holder.alarmTime.text = alarm.hour
        holder.alarmSwitch.isChecked = alarm.isActive
        holder.alarmSwitch.setOnCheckedChangeListener { _, isChecked ->
            onToggle(alarm.copy(isActive = isChecked))
        }
    }

    override fun getItemCount(): Int = alarms.size
}
