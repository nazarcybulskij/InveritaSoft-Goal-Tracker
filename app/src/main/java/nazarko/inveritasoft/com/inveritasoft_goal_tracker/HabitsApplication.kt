package nazarko.inveritasoft.com.inveritasoft_goal_tracker

import android.app.Application
import android.content.Context
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.DaggerHabitsActivityComponent

/**
 * Created by nazarko on 08.02.18.
 */
class HabitsApplication : Application() {

    private lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        context = this
        HabitsApplication.component = DaggerHabitsApplicationComponent
                .builder()
                .appContextModule(AppContextModule(context))
                .build()

    }


    val component: HabitsApplicationComponent
        get() = HabitsApplication.component

    companion object {
        lateinit var component: HabitsApplicationComponent
    }


}