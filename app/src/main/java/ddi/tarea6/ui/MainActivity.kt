package ddi.tarea6.ui

import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        val buttonShowMore: MaterialButton = findViewById(R.id.button_show_more)
        buttonShowMore.setOnClickListener {

            val popupMenu = PopupMenu(this, it, 0, 0, R.style.CustomPopupMenu)
            popupMenu.menuInflater.inflate(R.menu.popup_menu_more, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_edit -> {
                        Toast.makeText(this, "Editar pulsado", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.action_settings -> {
                        Toast.makeText(this, "Ajustes pulsado", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
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