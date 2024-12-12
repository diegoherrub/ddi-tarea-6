package ddi.tarea6.ui.presentation

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.materialswitch.MaterialSwitch
import ddi.tarea6.ui.R

/**
 * ViewHolder for representing an alarm item in a RecyclerView.
 *
 * This class is responsible for holding references to the views in a single item layout,
 * allowing the RecyclerView.Adapter to bind data to these views efficiently.
 *
 * @param view The root view of the item layout.
 */
class AlarmViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    /**
     * The CardView container for the alarm item.
     * Provides a visual wrapper for the item's content.
     */
    val cardView: CardView = view.findViewById(R.id.card_view_alarm)

    /**
     * TextView for displaying the time of the alarm.
     * Example: "07:30 AM"
     */
    val alarmTime: TextView = view.findViewById(R.id.text_view_hour)

    /**
     * TextView for displaying the description or label of the alarm.
     * Example: "Morning Run Alarm"
     */
    val alarmsDescription: TextView = view.findViewById(R.id.text_view_description)

    /**
     * MaterialSwitch for toggling the alarm on or off.
     * Example: On - Alarm is active, Off - Alarm is inactive.
     */
    val alarmSwitch: MaterialSwitch = view.findViewById(R.id.switch_alarm)
}
