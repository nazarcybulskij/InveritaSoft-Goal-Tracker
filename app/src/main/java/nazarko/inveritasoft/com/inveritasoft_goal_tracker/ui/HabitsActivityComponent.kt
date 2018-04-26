package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui

import dagger.Component
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.HabitsApplicationComponent
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.HabitsActivity
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.util.Dao

/**
 * Created by nazarko on 08.02.18.
 */

@ActivityScope
@Component(modules = [(EmptyModule::class), (HabitsActivityModule::class)], dependencies = [(HabitsApplicationComponent::class)])
interface HabitsActivityComponent {

    val dao: Dao


    fun inject(habbitActivity: HabitsActivity)

}