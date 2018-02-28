package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.viewmodel

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.android.architecture.blueprints.todoapp.mvibase.MviResult
import com.example.android.architecture.blueprints.todoapp.mvibase.MviView
import com.example.android.architecture.blueprints.todoapp.mvibase.MviViewModel
import com.example.android.architecture.blueprints.todoapp.mvibase.MviViewState
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.*
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.logic.MainActionProcessorHolder
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.logic.Navigator
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.schedulers.SchedulerProvider

/**
 * Created by nazarko on 15.02.18.
 */
class MainViewModel:ViewModel(),MviViewModel<MainIntent,MainViewState> {


    lateinit var mIntentsSubject : PublishSubject<MainIntent>
    lateinit var mStatesObservable : Observable<MainViewState>


    lateinit var mNavigateSubject : PublishSubject<MainAction>
    lateinit var mNavigateObservable : Observable<NavigationTarget>


    init{
        mIntentsSubject = PublishSubject.create()
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
        return mNavigateSubject.compose(Navigator(SchedulerProvider()).actionProcessor);



    }


    override fun processIntents(intents: Observable<MainIntent>) {
        intents.subscribe(mIntentsSubject)
    }

    public fun processNavigate(intents: Observable<MainAction>) {
        intents.subscribe(mNavigateSubject)
    }

    override fun states(): Observable<MainViewState> =
        mStatesObservable;

     public fun navigates(): Observable<NavigationTarget> =
             mNavigateObservable;

    /**
     * take only the first ever InitialIntent and all intents of other types
     * to avoid reloading data on config changes
     */
    private val intentFilter: ObservableTransformer<MainIntent, MainIntent>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                shared.ofType(MainIntent.InitialIntent::class.java).take(1).cast(MainIntent::class.java)
            }
        }

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