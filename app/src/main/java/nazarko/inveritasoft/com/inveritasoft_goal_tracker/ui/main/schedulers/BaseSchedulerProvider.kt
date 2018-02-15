package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.schedulers

import io.reactivex.Scheduler

/**
 * Created by nazarko on 15.02.18.
 */
interface BaseSchedulerProvider {
    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}