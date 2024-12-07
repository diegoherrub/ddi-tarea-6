package ddi.tarea6.ui.presentation

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ddi.tarea6.ui.R
import ddi.tarea6.ui.domain.Alarm

class AlarmAdapter(
    private val alarms: MutableList<Alarm>,
    private val onToggle: (Alarm) -> Unit
) : RecyclerView.Adapter<AlarmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]

        holder.alarmSwitch.setOnCheckedChangeListener(null)

        holder.alarmTime.text = alarm.hour
        holder.alarmsDescription.text = alarm.description
        holder.alarmSwitch.isChecked = alarm.isActive

        updateCardViewAttributes(holder, alarm.isActive)

        holder.alarmSwitch.setOnCheckedChangeListener { _, isChecked ->
            val updatedAlarm = alarm.copy(isActive = isChecked)
            alarms[position] = updatedAlarm
            notifyItemChanged(position)
            onToggle(updatedAlarm)

            updateCardViewAttributes(holder, isChecked)
        }
    }

    private fun updateCardViewAttributes(holder: AlarmViewHolder, isActive: Boolean) {
        val context = holder.cardView.context

        //holder.cardView.setCardBackgroundColor (
        //    if (isActive) context.getColor(R.color.active_card_background)
        //    else context.getColor(R.color.inactive_card_background)
        //)

        holder.alarmTime.setTextColor(
            if (isActive) context.getColor(R.color.md_theme_tertiary)
            else context.getColor(R.color.md_theme_outlineVariant_mediumContrast)
        )


        holder.alarmSwitch.trackTintList = ColorStateList.valueOf(
            if (isActive) context.getColor(R.color.md_theme_tertiary)
            else context.getColor(android.R.color.darker_gray)
        )
    }

    override fun getItemCount(): Int = alarms.size
}
