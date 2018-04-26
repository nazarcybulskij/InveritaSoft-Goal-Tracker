package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.mvi.MviViewState
import com.prolificinteractive.materialcalendarview.CalendarDay
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.logic.MainActionProcessorHolder
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.Goal

/**
 * Created by nazarko on 15.02.18.
 */
data class MainViewState(
        val name:String,
        var goals:HashMap<CalendarDay,Goal>,
        val active: Boolean,
        val loading: Boolean,
        val error: Throwable?
): MviViewState {
    companion object {
        fun idle(): MainViewState {
            return MainViewState(
                    name = "idle",
                    goals = MainActionProcessorHolder.goals,
                    active = false,
                    loading = false,
                    error = null
            )
        }
    }
}