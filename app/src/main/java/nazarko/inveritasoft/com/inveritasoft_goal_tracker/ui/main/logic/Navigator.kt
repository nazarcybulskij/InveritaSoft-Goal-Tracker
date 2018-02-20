package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.logic

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.MainAction
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.MainResult
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.NavigationTarget
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.schedulers.BaseSchedulerProvider

/**
 * Created by nazarko on 19.02.18.
 */
class Navigator(private val schedulerProvider: BaseSchedulerProvider) {


    private val showCommentDialogProcessor =
            ObservableTransformer<MainAction.ShowCommentDialogAction, NavigationTarget> { actions ->
                actions.flatMap { action ->
                    Single.just(NavigationTarget.ShowCommentDialog(action.date))
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