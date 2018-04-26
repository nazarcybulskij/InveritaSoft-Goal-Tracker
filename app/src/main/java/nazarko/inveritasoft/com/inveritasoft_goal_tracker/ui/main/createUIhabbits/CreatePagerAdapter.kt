package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.createUIhabbits

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by nazarko on 23.02.18.
 */
class CreatePagerAdapter(fragmentManager:FragmentManager, private var list:List<String>):FragmentStatePagerAdapter(fragmentManager) {


    private val HABBIT_POSUTION = 0
    private val REMIND_POSUTION = 1

    override fun getItem(position: Int): Fragment {
        return when(position){
            HABBIT_POSUTION -> CreateHabbitFragment.newInstance()
            REMIND_POSUTION -> CreateRemindersFragment.newInstance()
            else -> CreateHabbitFragment.newInstance()
        }

    }

    override fun getCount(): Int  = list.size

    override fun getPageTitle(position: Int): CharSequence  = list[position]
}