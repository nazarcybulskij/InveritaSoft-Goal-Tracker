package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import android.os.Bundle
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.HabitsApplication
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.HabitsApplicationComponent
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

        component.dao.toast()

        empty.toast();

    }


}