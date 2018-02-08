package nazarko.inveritasoft.com.inveritasoft_goal_tracker

import dagger.Component
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.util.DB

/**
 * Created by nazarko on 08.02.18.
 */
@AppScope
@Component(modules = arrayOf(
        HabitsModule::class,
        AppContextModule::class
))
public interface HabitsApplicationComponent {

    val database: DB

}