package ddi.tarea6.ui

import android.os.Bundle
import android.view.Gravity
import android.view.MenuInflater
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListPopupWindow
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import ddi.tarea6.ui.presentation.AlarmsFragment
import ddi.tarea6.ui.presentation.StopwatchFragment
import ddi.tarea6.ui.presentation.TimerFragment
import ddi.tarea6.ui.presentation.WorldClockFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAddAlarm: MaterialButton = findViewById(R.id.button_add_alarm)
        buttonAddAlarm.setOnClickListener {
            Toast.makeText(this, "Añadir alarma pulsado", Toast.LENGTH_SHORT).show()
        }

        val buttonShowMore: MaterialButton = findViewById(R.id.button_show_more)
        buttonShowMore.setOnClickListener {

            val popupMenu = PopupMenu(this, buttonShowMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu_more, popupMenu.menu)

            val menuItems = mutableListOf<String>()
            for (i in 0 until popupMenu.menu.size()) {
                val menuItem = popupMenu.menu.getItem(i)
                menuItems.add(menuItem.title.toString())
            }
            val popupView = layoutInflater.inflate(R.layout.popup_menu_layout, null)
            val listView: ListView = popupView.findViewById(R.id.popup_list_view)

            // Configura el adaptador con los títulos extraídos
            val adapter = ArrayAdapter(this, R.layout.popup_menu_items, menuItems)
            listView.adapter = adapter

            listView.setOnItemClickListener { _, _, position, _ ->
                val selectedItem = popupMenu.menu.getItem(position)
                when (selectedItem.itemId) {
                    R.id.action_edit -> {
                        Toast.makeText(this, "Editar pulsado", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.action_settings -> {
                        Toast.makeText(this, "Ajustes pulsado", Toast.LENGTH_SHORT).show()
                        true
                    }
                }
            }

            val popupWindow = PopupWindow(
                popupView,
                300,//LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            //popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.popup_menu_background))
            popupWindow.isFocusable = true
            popupWindow.elevation = 10f
            popupWindow.showAsDropDown(buttonShowMore, 0, 10)
            //popupWindow.showAtLocation(buttonShowMore, Gravity.NO_GRAVITY, 600, 250)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_alarms -> AlarmsFragment()
                R.id.nav_world_clock -> WorldClockFragment()
                R.id.nav_stopwatch -> StopwatchFragment()
                R.id.nav_timer -> TimerFragment()
                else -> AlarmsFragment()
            }
            loadFragment(fragment)
            true
        }

        loadFragment(AlarmsFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}