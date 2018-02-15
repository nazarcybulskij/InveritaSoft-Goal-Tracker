package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.viewmodel

import android.arch.lifecycle.ViewModel
import com.example.android.architecture.blueprints.todoapp.mvibase.MviViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.MainAction
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.MainActivity
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.MainIntent
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.MainViewState
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
                .compose(MainActionProcessorHolder(SchedulerProvider()).actionProcessor).compose()
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
                Observable.merge<MainIntent>(
                        shared.ofType(MainIntent::class.java),
                        shared.ofType(MainIntent::class.java)
                )
            }
        }

    private fun actionFromIntent(intent: MainIntent): MainAction {
        return when (intent) {
            is MainIntent.InitialIntent -> MainAction.InitialAction("")
        }
    }
}