package ddi.tarea6.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import ddi.tarea6.ui.presentation.AlarmsFragment
import ddi.tarea6.ui.presentation.StopwatchFragment
import ddi.tarea6.ui.presentation.TimerFragment
import ddi.tarea6.ui.presentation.WorldClockFragment

/**
 * MainActivity serves as the entry point of the application.
 *
 * It manages the BottomNavigationView, handles button interactions,
 * and displays the appropriate Fragment based on user selection.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Button to add a new alarm
        val buttonAddAlarm: MaterialButton = findViewById(R.id.button_add_alarm)
        buttonAddAlarm.setOnClickListener {
            Toast.makeText(this, "Add alarm clicked", Toast.LENGTH_SHORT).show()
        }

        // Button to show additional options in the popup menu
        val buttonShowMore: MaterialButton = findViewById(R.id.button_show_more)
        buttonShowMore.setOnClickListener {

            // Initialize a PopupMenu anchored to the "Show More" button
            val popupMenu = PopupMenu(this, buttonShowMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu_more, popupMenu.menu)

            // Extract menu item titles for display in a custom popup window
            val menuItems = mutableListOf<String>()
            for (i in 0 until popupMenu.menu.size()) {
                val menuItem = popupMenu.menu.getItem(i)
                menuItems.add(menuItem.title.toString())
            }

            // Inflate the custom layout for the popup window
            val popupView = layoutInflater.inflate(R.layout.popup_menu_layout, null)
            val listView: ListView = popupView.findViewById(R.id.popup_list_view)

            // Set up an ArrayAdapter to display the menu item titles in the ListView
            val adapter = ArrayAdapter(this, R.layout.popup_menu_items, menuItems)
            listView.adapter = adapter

            // Handle item click events in the ListView
            listView.setOnItemClickListener { _, _, position, _ ->
                val selectedItem = popupMenu.menu.getItem(position)
                when (selectedItem.itemId) {
                    R.id.action_edit -> {
                        Toast.makeText(this, "Edit clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.action_settings -> {
                        Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            // Create and configure a PopupWindow to display the custom popup layout
            val popupWindow = PopupWindow(
                popupView,
                300, // Width of the popup submenu
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            popupWindow.isFocusable = true // Allow interaction with the popup
            popupWindow.elevation = 10f // Add shadow for better visibility
            popupWindow.showAsDropDown(buttonShowMore, 0, 10) // Position the popup
        }

        // Set up the BottomNavigationView to switch between Fragments
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            // Determine which Fragment to load based on the selected menu item
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_alarms -> AlarmsFragment()
                R.id.nav_world_clock -> WorldClockFragment()
                R.id.nav_stopwatch -> StopwatchFragment()
                R.id.nav_timer -> TimerFragment()
                else -> AlarmsFragment()
            }
            loadFragment(fragment) // Load the selected Fragment
            true
        }

        // Load the default Fragment (AlarmsFragment) when the activity starts
        loadFragment(AlarmsFragment())
    }

    /**
     * Replaces the current Fragment in the container with the specified Fragment.
     *
     * @param fragment The Fragment to display.
     */
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
