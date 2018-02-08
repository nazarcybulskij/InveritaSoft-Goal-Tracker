package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui

import dagger.Component
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.HabitsApplicationComponent
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.util.DB
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.util.Dao

/**
 * Created by nazarko on 08.02.18.
 */

@ActivityScope
@Component(modules = arrayOf(
    HabitsActivityModule::class
), dependencies = arrayOf(HabitsApplicationComponent::class))
interface HabitsActivityComponent {

    val dao: Dao

}