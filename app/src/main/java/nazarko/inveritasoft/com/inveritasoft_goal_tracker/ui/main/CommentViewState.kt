package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.mvi.MviViewState
import com.prolificinteractive.materialcalendarview.CalendarDay
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.logic.MainActionProcessorHolder
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.Goal

/**
 * Created by nazarko on 19.02.18.
 */


data class CommentViewState(
        val name:String = "",
        var goals:HashMap<CalendarDay, Goal>,
        var delete:Boolean,
        var set:Boolean,
        var cancel:Boolean,
        val loading: Boolean,
        val error: Throwable?
): MviViewState {
    companion object {
        fun idle(): CommentViewState {
            return CommentViewState(
                    name = "idle",
                    goals = MainActionProcessorHolder.goals,
                    delete = false,
                    set = false,
                    cancel = false,
                    loading = false,
                    error = null
            )
        }
    }
}