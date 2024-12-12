package ddi.tarea6.ui.presentation

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddi.tarea6.ui.R
import ddi.tarea6.ui.domain.Alarm

/**
 * Adapter for managing and displaying a list of alarms in a RecyclerView.
 *
 * @property alarms Mutable list of alarms to display.
 * @property onToggle Callback function triggered when an alarm's active state changes.
 */
class AlarmAdapter(
    private val alarms: MutableList<Alarm>,
    private val onToggle: (Alarm) -> Unit
) : RecyclerView.Adapter<AlarmViewHolder>() {

    /**
     * Inflates the layout for a single alarm item and returns the corresponding ViewHolder.
     *
     * @param parent The parent ViewGroup into which the new view will be added.
     * @param viewType The view type of the new view (not used here as all items are identical).
     * @return A new instance of AlarmViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(view)
    }

    /**
     * Binds the alarm data to the specified ViewHolder and sets up listeners for UI interactions.
     *
     * @param holder The ViewHolder that should be updated with new data.
     * @param position The position of the alarm in the list.
     */
    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]

        // Temporarily remove the listener to avoid unintended triggers during state updates
        holder.alarmSwitch.setOnCheckedChangeListener(null)

        // Update the UI elements with alarm data
        holder.alarmTime.text = alarm.hour
        holder.alarmsDescription.text = alarm.description
        holder.alarmSwitch.isChecked = alarm.isActive

        // Update the card view appearance based on the alarm's active state
        updateCardViewAttributes(holder, alarm.isActive)

        // Reattach the listener to handle user interactions
        holder.alarmSwitch.setOnCheckedChangeListener { _, isChecked ->
            val updatedAlarm = alarm.copy(isActive = isChecked)
            alarms[position] = updatedAlarm // Update the list with the new state
            notifyItemChanged(position) // Notify RecyclerView to refresh this item
            onToggle(updatedAlarm) // Trigger the callback with the updated alarm

            // Update the UI appearance for the new state
            updateCardViewAttributes(holder, isChecked)
        }
    }

    /**
     * Updates the visual attributes of the card view based on the alarm's active state.
     *
     * @param holder The ViewHolder containing the card view to update.
     * @param isActive The active state of the alarm (true if active, false otherwise).
     */
    private fun updateCardViewAttributes(holder: AlarmViewHolder, isActive: Boolean) {
        val context = holder.cardView.context

        // Set the text color for the description and time based on the active state
        holder.alarmsDescription.setTextColor(
            if (isActive) context.getColor(R.color.md_theme_tertiary)
            else context.getColor(R.color.md_theme_outlineVariant_mediumContrast)
        )

        holder.alarmTime.setTextColor(
            if (isActive) context.getColor(R.color.md_theme_tertiary)
            else context.getColor(R.color.md_theme_outlineVariant_mediumContrast)
        )

        // Set the track tint color of the switch based on the active state
        holder.alarmSwitch.trackTintList = ColorStateList.valueOf(
            if (isActive) context.getColor(R.color.md_theme_tertiary)
            else context.getColor(android.R.color.darker_gray)
        )
    }

    /**
     * Returns the total number of alarms in the list.
     *
     * @return The size of the alarm list.
     */
    override fun getItemCount(): Int = alarms.size
}
