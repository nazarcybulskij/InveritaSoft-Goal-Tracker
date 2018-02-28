package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.createUIhabbits

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_create_reminders.*
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R

/**
 * Created by nazarko on 27.02.18.
 */
class CreateRemindersFragment: Fragment() {

    lateinit var mCreateRemindersAdapter: CreateRemindersAdapter


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
        mCreateRemindersAdapter = CreateRemindersAdapter(ArrayList<UserDto>());
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = mCreateRemindersAdapter;
        val shareViewModel = ViewModelProviders.of(activity).get(CreateHabbitShareViewModel::class.java)
        shareViewModel.clickFab.observe(this, Observer {
            CreateRemindersDialog.show(activity)
        })
        shareViewModel.createReminder.observe(this, Observer {
            okReminders(it)
        })
    }

    fun cancelReminders() {

    }

     fun okReminders(userDto: UserDto?) {
         userDto?.let {
             mCreateRemindersAdapter.data.add(userDto)
             mCreateRemindersAdapter.notifyDataSetChanged()
         }

    }

}