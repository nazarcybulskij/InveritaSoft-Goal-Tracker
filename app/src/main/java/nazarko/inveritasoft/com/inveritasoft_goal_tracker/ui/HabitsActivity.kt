package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.HabitsApplication
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.HabitsApplicationComponent

/**
 * Created by nazarko on 08.02.18.
 */
abstract class HabitsActivity :AppCompatActivity() {

    lateinit var component: HabitsActivityComponent
    lateinit var appComponent: HabitsApplicationComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent = (applicationContext as HabitsApplication).component
        component = DaggerHabitsActivityComponent
                .builder()
                .habitsApplicationComponent(appComponent)
                .build()
        
    }


}