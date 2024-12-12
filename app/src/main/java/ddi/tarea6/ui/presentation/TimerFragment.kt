package ddi.tarea6.ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ddi.tarea6.ui.R

/**
 * A Fragment representing the Timer screen.
 *
 * This Fragment inflates the layout for the timer feature and handles its lifecycle events.
 */
class TimerFragment : Fragment() {

    /**
     * Called to create the view hierarchy associated with this Fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views in the Fragment.
     * @param container The parent view that this Fragment's UI will be attached to.
     * @param savedInstanceState If non-null, this Fragment is being re-constructed from a previous saved state.
     * @return The root view of the Fragment's layout, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this Fragment
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }
}
