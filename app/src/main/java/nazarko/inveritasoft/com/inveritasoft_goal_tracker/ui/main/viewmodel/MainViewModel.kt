package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.viewmodel

import android.arch.lifecycle.ViewModel
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
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.schedulers.SchedulerProvider

/**
 * Created by nazarko on 15.02.18.
 */
class MainViewModel:ViewModel(),MviViewModel<MainIntent,MainViewState> {


    lateinit var mIntentsSubject : PublishSubject<MainIntent>
    lateinit var mStatesObservable : Observable<MainViewState>

    init{
        mIntentsSubject = PublishSubject.create()
        mStatesObservable = compose()
    }

    private fun compose(): Observable<MainViewState> {
        return mIntentsSubject
                .compose<MainIntent>(intentFilter)
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


    override fun processIntents(intents: Observable<MainIntent>) {
        intents.subscribe(mIntentsSubject)
    }

    override fun states(): Observable<MainViewState> =
        mStatesObservable;

    /**
     * take only the first ever InitialIntent and all intents of other types
     * to avoid reloading data on config changes
     */
    private val intentFilter: ObservableTransformer<MainIntent, MainIntent>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                shared.ofType(MainIntent::class.java)
            }
        }

    private fun actionFromIntent(intent: MainIntent): MainAction {
        return when (intent) {
            is MainIntent.InitialIntent -> MainAction.InitialAction("")
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
                is MainResult -> previousState.copy(str = "")
            }
        }
    }
}