package ddi.tarea6.ui.presentation

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.materialswitch.MaterialSwitch
import ddi.tarea6.ui.R

class AlarmViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    val cardView: CardView = view.findViewById(R.id.card_view_alarm)
    val alarmTime: TextView = view.findViewById(R.id.text_view_hour)
    val alarmsDescription: TextView = view.findViewById(R.id.text_view_description)
    val alarmSwitch: MaterialSwitch = view.findViewById(R.id.switch_alarm)
}