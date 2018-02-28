package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.createUIhabbits

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R

/**
 * Created by nazarko on 23.02.18.
 */
class CreateHabbitFragment:Fragment() {

    companion object {

        fun newInstance(): CreateHabbitFragment {
            return CreateHabbitFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_create_habbit, container, false)
    }
}