package ddi.tarea6.ui.presentation

import android.animation.ValueAnimator
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ddi.tarea6.ui.R
import ddi.tarea6.ui.data.AlarmsDataRepository
import ddi.tarea6.ui.data.local.AlarmsXmlLocalDataRepository
import ddi.tarea6.ui.domain.Alarm
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * Fragment for displaying and managing a list of alarms. It handles interactions such as scrolling,
 * updating alarm states, and animating UI elements based on user actions.
 */
class AlarmsFragment : Fragment() {

    private lateinit var alarmsDataRepository: AlarmsDataRepository
    private lateinit var alarmList: MutableList<Alarm>
    private lateinit var adapter: AlarmAdapter
    private var isTimeRemainingHidden = false

    /**
     * Inflates the layout for the fragment.
     *
     * @param inflater The LayoutInflater object for inflating views.
     * @param container The parent ViewGroup (if non-null).
     * @param savedInstanceState Previously saved state, if any.
     * @return The root view of the inflated layout.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarms, container, false)
    }

    /**
     * Called after the view is created. Sets up RecyclerView, adapters, animations, and data handling.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState Previously saved state, if any.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_alarms)
        val timeRemainingTextView = view.findViewById<TextView>(R.id.text_time_remaining)
        val textAlarmsTextView = view.findViewById<TextView>(R.id.text_alarms)

        val xmlLocal = AlarmsXmlLocalDataRepository(requireContext())
        alarmsDataRepository = AlarmsDataRepository(xmlLocal)

        // Initialize the alarm list and populate it if empty
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

        /**
         * Animates the height and text size of a view for smooth transitions.
         *
         * @param view The view to animate.
         * @param fromHeight Initial height value.
         * @param toHeight Target height value.
         * @param textView The TextView whose text size will be animated.
         * @param fromSize Initial text size.
         * @param toSize Target text size.
         */
        fun animateHeightAndTextSize(
            view: View,
            fromHeight: Int,
            toHeight: Int,
            textView: TextView,
            fromSize: Float,
            toSize: Float
        ) {
            val heightAnimator = ValueAnimator.ofInt(fromHeight, toHeight)
            heightAnimator.addUpdateListener { animation ->
                val value = animation.animatedValue as Int
                val layoutParams = view.layoutParams
                layoutParams.height = value
                view.layoutParams = layoutParams
            }

            val textSizeAnimator = ValueAnimator.ofFloat(fromSize, toSize)
            textSizeAnimator.addUpdateListener { animation ->
                textView.textSize = animation.animatedValue as Float
            }

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(heightAnimator, textSizeAnimator)
            animatorSet.duration = 300
            animatorSet.start()
        }

        /**
         * Updates the time remaining text based on the next active alarm.
         */
        fun updateTimeRemaining() {
            // Avoid running unnecessary logic to update the timeRemainingTextView text
            // when this item is already hidden.
            if (isTimeRemainingHidden) return

            val currentTime = LocalTime.now()
            val activeAlarms = alarmList.filter { it.isActive }

            if (activeAlarms.isNotEmpty()) {
                val formatter = DateTimeFormatter.ofPattern("HH:mm")
                val closestAlarm = activeAlarms.minByOrNull {
                    val alarmTime = LocalTime.parse(it.hour, formatter)
                    ChronoUnit.MINUTES.between(currentTime, alarmTime).let { diff ->
                        if (diff < 0) diff + 24 * 60 else diff
                    }
                }

                closestAlarm?.let {
                    val alarmTime = LocalTime.parse(it.hour, formatter)
                    val minutesRemaining =
                        ChronoUnit.MINUTES.between(currentTime, alarmTime).let { diff ->
                            if (diff < 0) diff + 24 * 60 else diff
                        }

                    val hours = minutesRemaining / 60
                    val minutes = minutesRemaining % 60
                    timeRemainingTextView.text = when {
                        hours > 0 -> "Próxima alarma en $hours horas y $minutes minutos"
                        else -> "Próxima alarma en $minutes minutos"
                    }
                }
            } else {
                timeRemainingTextView.text = "No hay alarmas activas"
            }
        }

        // Set up the RecyclerView adapter and layout manager
        adapter = AlarmAdapter(alarmList.toMutableList()) { updatedAlarm ->
            val index = alarmList.indexOfFirst { it.hour == updatedAlarm.hour }
            if (index != -1) {
                alarmList[index] = updatedAlarm
                adapter.notifyItemChanged(index)
                alarmsDataRepository.saveAlarms(alarmList)
                updateTimeRemaining()
            }
            Toast.makeText(
                requireContext(),
                "Alarma ${updatedAlarm.hour} ${if (updatedAlarm.isActive) "activada" else "desactivada"}",
                Toast.LENGTH_SHORT
            ).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Handle scrolling animations for the time remaining text
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0 && !isTimeRemainingHidden) {
                    isTimeRemainingHidden = true
                    animateHeightAndTextSize(
                        timeRemainingTextView,
                        timeRemainingTextView.height,
                        0,
                        textAlarmsTextView,
                        24f,
                        16f
                    )
                } else if (dy < 0 && isTimeRemainingHidden) {
                    isTimeRemainingHidden = false
                    animateHeightAndTextSize(
                        timeRemainingTextView,
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        textAlarmsTextView,
                        16f,
                        24f
                    )
                    updateTimeRemaining()
                }
            }
        })

        updateTimeRemaining()
    }
}
