package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.logic

import com.prolificinteractive.materialcalendarview.CalendarDay
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.mvi.MviAction
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.mvi.MviResult
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.CommentResult
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.MainAction
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.MainResult
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.Goal
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.ResultDay
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.schedulers.BaseSchedulerProvider

/**
 * Created by nazarko on 15.02.18.
 */
class MainActionProcessorHolder(private val schedulerProvider: BaseSchedulerProvider) {

    companion object  {
        var goals = HashMap<CalendarDay, Goal>()
    }


    private fun updateDate(date:CalendarDay):  Single<HashMap<CalendarDay, Goal>>{
        var goal = goals[date]
        if (goal == null) {
            goal = Goal(ResultDay.SUCCESS, false)
            goals[date] = goal
        } else {
            when (goal.result) {
                ResultDay.FAIL -> goal.result = ResultDay.NONE
                ResultDay.SUCCESS -> goal.result = ResultDay.FAIL
                ResultDay.NONE -> goal.result = ResultDay.SUCCESS
            }
        }
        return Single.just(goals)
    }

    private fun deleteComment(date: CalendarDay): Single<HashMap<CalendarDay, Goal>> {
        var goal = MainActionProcessorHolder.goals[date]
        if (goal == null) {
            goal = Goal(ResultDay.NONE, false)
            MainActionProcessorHolder.goals[date] = goal
        }
        goal.iscomment = false
        return Single.just(goals)
    }

    private fun cancelComment(): Observable<CommentResult.CancelCommentResult.Success> {
        return Observable.just(CommentResult.CancelCommentResult.Success())
    }

    private fun setComment(date: CalendarDay,comment:String): Single<HashMap<CalendarDay, Goal>> {
        var goal = MainActionProcessorHolder.goals[date]
        if (goal == null) {
            goal = Goal(ResultDay.NONE, true,comment)
            MainActionProcessorHolder.goals[date] = goal
        }
        goal.comment = comment
        goal.iscomment = true
        return Single.just(goals)
    }


    private val initProcessor =
            ObservableTransformer<MainAction.InitialAction, MainResult.InitResult> { actions ->
                actions.flatMap { action ->
                    Single.just("temp")
                            // Transform the Single to an Observable to allow emission of multiple
                            // events down the stream (e.g. the InFlight event)
                            .toObservable()
                            // Wrap returned data into an immutable object
                            .map(MainResult.InitResult::Success)
                            .cast(MainResult.InitResult::class.java)
                            // Wrap any error into an immutable object and pass it down the stream
                            // without crashing.
                            // Because errors are data and hence, should just be part of the stream.
                            .onErrorReturn(MainResult.InitResult::Failure)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            // Emit an InFlight event to notify the subscribers (e.g. the UI) we are
                            // doing work and waiting on a response.
                            // We emit it after observing on the UI thread to allow the event to be emitted
                            // on the current frame and avoid jank.
                            .startWith(MainResult.InitResult.InFlight)
                }
            }

    private val dateProcessor =
            ObservableTransformer<MainAction.DataClickAction, MainResult.DateResult> { actions ->
                actions.flatMap { action ->
                        updateDate(action.date)
                            // Transform the Single to an Observable to allow emission of multiple
                            // events down the stream (e.g. the InFlight event)
                            .toObservable()
                            // Wrap returned data into an immutable object
                            .map(MainResult.DateResult::Success)
                            .cast(MainResult.DateResult::class.java)
                            // Wrap any error into an immutable object and pass it down the stream
                            // without crashing.
                            // Because errors are data and hence, should just be part of the stream.
                            .onErrorReturn(MainResult.DateResult::Failure)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            // Emit an InFlight event to notify the subscribers (e.g. the UI) we are
                            // doing work and waiting on a response.
                            // We emit it after observing on the UI thread to allow the event to be emitted
                            // on the current frame and avoid jank.
                            .startWith(MainResult.DateResult.InFlight)
                }
            }

    private val setCommentProcessor =
            ObservableTransformer<MainAction.SetCommentAction, CommentResult.SetCommentResult> { actions ->
                actions.flatMap { action ->
                    setComment(action.date,action.comment)
                            // Transform the Single to an Observable to allow emission of multiple
                            // events down the stream (e.g. the InFlight event)
                            .toObservable()
                            // Wrap returned data into an immutable object
                            .map(CommentResult.SetCommentResult::Success)
                            .cast(CommentResult.SetCommentResult::class.java)
                            // Wrap any error into an immutable object and pass it down the stream
                            // without crashing.
                            // Because errors are data and hence, should just be part of the stream.
                            .onErrorReturn(CommentResult.SetCommentResult::Failure)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            // Emit an InFlight event to notify the subscribers (e.g. the UI) we are
                            // doing work and waiting on a response.
                            // We emit it after observing on the UI thread to allow the event to be emitted
                            // on the current frame and avoid jank.
                            .startWith(CommentResult.SetCommentResult.InFlight)
                }
            }

    private val deleteCommentProcessor =
            ObservableTransformer<MainAction.DeleteCommentAction, CommentResult.DeleteCommentResult> { actions ->
                actions.flatMap { action ->
                    deleteComment(action.date)
                            // Transform the Single to an Observable to allow emission of multiple
                            // events down the stream (e.g. the InFlight event)
                            .toObservable()
                            // Wrap returned data into an immutable object
                            .map(CommentResult.DeleteCommentResult::Success)
                            .cast(CommentResult.DeleteCommentResult::class.java)
                            // Wrap any error into an immutable object and pass it down the stream
                            // without crashing.
                            // Because errors are data and hence, should just be part of the stream.
                            .onErrorReturn(CommentResult.DeleteCommentResult::Failure)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            // Emit an InFlight event to notify the subscribers (e.g. the UI) we are
                            // doing work and waiting on a response.
                            // We emit it after observing on the UI thread to allow the event to be emitted
                            // on the current frame and avoid jank.
                            .startWith(CommentResult.DeleteCommentResult.InFlight)
                }
            }

    private val cancelCommentProcessor =
            ObservableTransformer<MainAction.CancelAction, CommentResult.CancelCommentResult> { actions ->
                actions.flatMap { action ->
                    cancelComment()
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                }
            }





    /**
     * Splits the [Observable] to match each type of [MviAction] to
     * its corresponding business logic processor. Each processor takes a defined [MviAction],
     * returns a defined [MviResult]
     * The global actionProcessor then merges all [Observable] back to
     * one unique [Observable].
     *
     *
     * The splitting is done using [Observable.publish] which allows almost anything
     * on the passed [Observable] as long as one and only one [Observable] is returned.
     *
     *
     * An security layer is also added for unhandled [MviAction] to allow early crash
     * at runtime to easy the maintenance.
     */
    internal var actionProcessor =
            ObservableTransformer<MainAction, MainResult> { actions ->
                actions.publish({ shared ->
                    Observable.merge<MainResult>(
                            shared.ofType(MainAction.InitialAction::class.java).compose(initProcessor),
                            shared.ofType(MainAction.DataClickAction::class.java).compose(dateProcessor)

                    )
                })
            }


    internal var commentProcessor =
            ObservableTransformer<MainAction, CommentResult> { actions ->
                actions.publish({ shared ->
                    Observable.merge<CommentResult>(
                            shared.ofType(MainAction.SetCommentAction::class.java).compose(setCommentProcessor),
                            shared.ofType(MainAction.DeleteCommentAction::class.java).compose(deleteCommentProcessor),
                            shared.ofType(MainAction.CancelAction::class.java).compose(cancelCommentProcessor)
                    )
                })
            }





}