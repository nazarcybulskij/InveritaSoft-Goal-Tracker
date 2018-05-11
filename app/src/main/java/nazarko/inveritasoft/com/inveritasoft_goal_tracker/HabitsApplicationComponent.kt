package nazarko.inveritasoft.com.inveritasoft_goal_tracker

import android.content.Context
import dagger.Component
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.HabitsActivity
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.util.DB

/**
 * Created by nazarko on 08.02.18.
 */
@AppScope
@Component(modules = [(HabitsModule::class), (AppContextModule::class)])
interface HabitsApplicationComponent {

    fun context(): Context

    val database: DB

    fun inject(habbitApplication: HabitsApplication)


}