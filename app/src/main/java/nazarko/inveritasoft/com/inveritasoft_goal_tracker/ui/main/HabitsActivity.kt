package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.HabitsApplication
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.HabitsApplicationComponent
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.BaseActivity
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.DaggerHabitsActivityComponent
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.HabitsActivityComponent
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.util.Empty
import javax.inject.Inject

/**
 * Created by nazarko on 08.02.18.
 */
abstract class HabitsActivity : BaseActivity() {

    lateinit var component: HabitsActivityComponent
    lateinit var appComponent: HabitsApplicationComponent

    @Inject
    lateinit var empty:Empty;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent = (applicationContext as HabitsApplication).component
        component = DaggerHabitsActivityComponent.builder()
                .habitsApplicationComponent(appComponent)
                .build()

        component.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.habits, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> addHabit()
            R.id.menu_edit -> editHabit()
            R.id.menu_remind -> remindSettingHabit()
            R.id.menu_note -> notesHabit()
            R.id.menu_weekly -> weeklyViewHabit()
            R.id.menu_range -> rangeHabits()
            R.id.menu_export -> exportHabits()
            R.id.menu_lock -> lockHabit()
            R.id.menu_sync -> syncHabits()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun syncHabits() {
        Toast.makeText(this,"sync",Toast.LENGTH_SHORT).show()
    }

    private fun lockHabit() {
        Toast.makeText(this,"lock",Toast.LENGTH_SHORT).show()
    }

    private fun exportHabits() {
        Toast.makeText(this,"export",Toast.LENGTH_SHORT).show()
    }

    private fun rangeHabits() {
        Toast.makeText(this, "range", Toast.LENGTH_SHORT).show()
    }

    private fun weeklyViewHabit() {
        Toast.makeText(this,"weekly",Toast.LENGTH_SHORT).show()
    }

    private fun notesHabit() {
        Toast.makeText(this,"notes",Toast.LENGTH_SHORT).show()
    }

    private fun remindSettingHabit() {
        Toast.makeText(this,"remind",Toast.LENGTH_SHORT).show()
    }

    private fun editHabit() {
        Toast.makeText(this,"edit",Toast.LENGTH_SHORT).show()
    }

    private fun addHabit() {
        Toast.makeText(this,"add",Toast.LENGTH_SHORT).show()
    }


}