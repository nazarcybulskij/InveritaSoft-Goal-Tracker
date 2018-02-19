package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.navigate

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.MainAction
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.MainResult
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.NavigationTarget

/**
 * Created by nazarko on 19.02.18.
 */
class NavigateProcessorHolder {

    private val commentProcessor =
            ObservableTransformer<MainAction.ShowCommentDialogNavigator, NavigationTarget> { actions ->
                actions.flatMap { actions -> Observable.just(NavigationTarget.Action("","")) }
            }



    internal var actionProcessor =
            ObservableTransformer<MainAction.ShowCommentDialogNavigator, NavigationTarget> { actions ->
                actions.publish({ shared ->
                    Observable.merge<NavigationTarget>(
                            shared.ofType(MainAction.ShowCommentDialogNavigator::class.java).compose(commentProcessor),
                            shared.ofType(MainAction.ShowCommentDialogNavigator::class.java).compose(commentProcessor)
                    )
                })
            }


}