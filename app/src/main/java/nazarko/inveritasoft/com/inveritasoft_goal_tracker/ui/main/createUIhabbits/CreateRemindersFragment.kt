package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.createUIhabbits

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R

/**
 * Created by nazarko on 27.02.18.
 */
class CreateRemindersFragment: Fragment() {


    companion object {

        fun newInstance(): CreateRemindersFragment {
            return CreateRemindersFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_create_reminders, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shareViewModel = ViewModelProviders.of(activity).get(FabClickShareViewModel::class.java)
        shareViewModel.click.observe(this, Observer {
            Toast.makeText(activity,"fab",Toast.LENGTH_SHORT).show()
        })

    }


}