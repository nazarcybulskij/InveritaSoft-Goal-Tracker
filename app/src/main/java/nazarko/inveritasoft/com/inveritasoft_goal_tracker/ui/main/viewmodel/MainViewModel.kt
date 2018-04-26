package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.mvi.MviResult
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.mvi.MviView
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.mvi.MviViewModel
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.mvi.MviViewState
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.*
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.logic.MainActionProcessorHolder
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.logic.Navigator
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.schedulers.SchedulerProvider

/**
 * Created by nazarko on 15.02.18.
 */
class MainViewModel:ViewModel(), MviViewModel<MainIntent, MainViewState> {


    private var mIntentsSubject : PublishSubject<MainIntent> = PublishSubject.create()
    private var mStatesObservable : Observable<MainViewState>


    private var mNavigateSubject : PublishSubject<MainAction>
    private var mNavigateObservable : Observable<NavigationTarget>


    init{
        mStatesObservable = compose()
        mNavigateSubject = PublishSubject.create()
        mNavigateObservable = navigatecompose()
    }

    private fun compose(): Observable<MainViewState> {
        return mIntentsSubject
                .map(this::actionFromIntent)
                .compose(MainActionProcessorHolder(SchedulerProvider()).actionProcessor)
                // Cache each state and pass it to the reducer to create a new state from
                // the previous cached one and the latest Result emitted from the action processor.
                // The Scan operator is used here for the caching.
                .scan(MainViewState.idle(), reducer)
                // Emit the last one event of the stream on subscription
                // Useful when a View rebinds to the ViewModel after rotation.
                .replay(1)
                // Create the stream on creation without waiting for anyone to subscribe
                // This allows the stream to stay alive even when the UI disconnects and
                // match the stream's lifecycle to the ViewModel's one.
                .autoConnect(0)
    }

    private fun navigatecompose(): Observable<NavigationTarget> {
        return mNavigateSubject.compose(Navigator(SchedulerProvider()).actionProcessor)


    }


    override fun processIntents(intents: Observable<MainIntent>) {
        intents.subscribe(mIntentsSubject)
    }

    fun processNavigate(intents: Observable<MainAction>) {
        intents.subscribe(mNavigateSubject)
    }

    override fun states(): Observable<MainViewState> =
        mStatesObservable

    fun navigates(): Observable<NavigationTarget> =
             mNavigateObservable

    private fun actionFromIntent(intent: MainIntent): MainAction {
        return when (intent) {
            is MainIntent.InitialIntent -> MainAction.InitialAction("")
            is MainIntent.DataClickIntent -> MainAction.DataClickAction(intent.date)
            else  -> MainAction.InitialAction("")
//            is MainIntent.CommentSetIntent -> MainAction.SetCommentAction(intent.date,intent.comment)
//            is MainIntent.CommentDeleteIntent -> MainAction.DeleteCommentAction(intent.date)
        }
    }

    companion object {
        /**
         * The Reducer is where [MviViewState], that the [MviView] will use to
         * render itself, are created.
         * It takes the last cached [MviViewState], the latest [MviResult] and
         * creates a new [MviViewState] by only updating the related fields.
         * This is basically like a big switch statement of all possible types for the [MviResult]
         */
        private val reducer = BiFunction { previousState: MainViewState, result: MainResult ->
            when (result) {
                is MainResult.InitResult -> when (result){
                    is MainResult.InitResult.Success -> previousState.copy(name = result.name(),active = true,loading = false,goals = previousState.goals)
                    is MainResult.InitResult.InFlight -> previousState.copy(name = result.name(),active = true,loading = true,goals = previousState.goals)
                    is MainResult.InitResult.Failure -> previousState.copy(name = result.name(),active = true,error = previousState.error,goals = previousState.goals)
                }
                is MainResult.DateResult -> when (result){
                    is MainResult.DateResult.Success -> previousState.copy(name = result.name(),active = true,loading = false,goals = result.goals)
                    is MainResult.DateResult.InFlight -> previousState.copy(name = result.name(),active = true,loading = true,goals = previousState.goals)
                    is MainResult.DateResult.Failure -> previousState.copy(name = result.name(),active = true,error = previousState.error,goals = previousState.goals)
                }
//                is MainResult.DateLongResult -> when (result){
//                    is MainResult.DateLongResult.Success -> previousState.copy(name = result.name(),active = true,loading = false,goals = previousState.goals)
//                    is MainResult.DateLongResult.InFlight -> previousState.copy(name = result.name(),active = true,loading = false,goals = previousState.goals)
//                    is MainResult.DateLongResult.Failure -> previousState.copy(name = result.name(),active = true,loading = false,goals = previousState.goals)
//                }
            }
        }
    }
}