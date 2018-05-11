package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.logic

import com.prolificinteractive.materialcalendarview.CalendarDay
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.MainAction
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.NavigationTarget
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.Goal
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.ResultDay
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.schedulers.BaseSchedulerProvider

/**
 * Created by nazarko on 19.02.18.
 */
class Navigator(private val schedulerProvider: BaseSchedulerProvider) {


    private val showCommentDialogProcessor =
            ObservableTransformer<MainAction.ShowCommentDialogAction, NavigationTarget> { actions ->
                actions.flatMap { action ->
                    Single.just(NavigationTarget.ShowCommentDialog(action.date,MainActionProcessorHolder.getGoalFromDate(action.date) ))
                            .toObservable()
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                }
            }





    internal var actionProcessor =
            ObservableTransformer<MainAction, NavigationTarget> { actions ->
                actions.publish({ shared ->
                    shared.ofType(MainAction.ShowCommentDialogAction::class.java).compose(showCommentDialogProcessor)
                })
            }
}

private fun MainActionProcessorHolder.Companion.getGoalFromDate(date: CalendarDay): Goal {
    var goal = goals[date]
    if (goal == null) {
        goal = Goal(ResultDay.NONE, false)
        goals[date] = goal
    }
    return goal

}
