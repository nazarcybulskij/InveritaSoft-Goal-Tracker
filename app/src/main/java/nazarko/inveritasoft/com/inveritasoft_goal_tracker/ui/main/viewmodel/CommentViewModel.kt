package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.viewmodel

import com.example.android.architecture.blueprints.todoapp.mvibase.MviViewModel
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.*
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.logic.MainActionProcessorHolder
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.schedulers.SchedulerProvider

/**
 * Created by nazarko on 19.02.18.
 */
class CommentViewModel: MviViewModel<MainIntent, CommentViewState> {

    lateinit var mIntentsSubject : PublishSubject<MainIntent>
    lateinit var mStatesObservable : Observable<CommentViewState>

    init{
        mIntentsSubject = PublishSubject.create()
        mStatesObservable = compose()
    }

    override fun processIntents(intents: Observable<MainIntent>) {
        intents.subscribe(mIntentsSubject)
    }

    override fun states(): Observable<CommentViewState>  = mStatesObservable

    private fun compose(): Observable<CommentViewState> {
        return mIntentsSubject
                .map(this::actionFromIntent)
                .compose(MainActionProcessorHolder(SchedulerProvider()).commentProcessor)
                // Cache each state and pass it to the reducer to create a new state from
                // the previous cached one and the latest Result emitted from the action processor.
                // The Scan operator is used here for the caching.
                .scan(CommentViewState.idle(), CommentViewModel.reducer)
                // Emit the last one event of the stream on subscription
                // Useful when a View rebinds to the ViewModel after rotation.
                .replay(1)
                // Create the stream on creation without waiting for anyone to subscribe
                // This allows the stream to stay alive even when the UI disconnects and
                // match the stream's lifecycle to the ViewModel's one.
                .autoConnect(0)
    }

    private fun actionFromIntent(intent: MainIntent): MainAction {
        return when (intent) {
            is MainIntent.CommentSetIntent -> MainAction.SetCommentAction(intent.date,intent.comment)
            is MainIntent.CommentDeleteIntent -> MainAction.DeleteCommentAction(intent.date)
            else  -> MainAction.InitialAction("")

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
        private val reducer = BiFunction { previousState: CommentViewState, result: CommentResult ->
            when (result) {
                is CommentResult.DeleteCommentResult -> when (result){
                    is CommentResult.DeleteCommentResult.Success -> previousState.copy(name = result.name(),active = true,loading = false,goals = result.goals)
                    is CommentResult.DeleteCommentResult.InFlight -> previousState.copy(name = result.name(),active = true,loading = true,goals = previousState.goals)
                    is CommentResult.DeleteCommentResult.Failure -> previousState.copy(name = result.name(),active = true,error = previousState.error,goals = previousState.goals)
                }
                is CommentResult.SetCommentResult -> when (result){
                    is CommentResult.SetCommentResult.Success -> previousState.copy(name = result.name(),active = true,loading = false,goals = result.goals)
                    is CommentResult.SetCommentResult.InFlight -> previousState.copy(name = result.name(),active = true,loading = true,goals = previousState.goals)
                    is CommentResult.SetCommentResult.Failure -> previousState.copy(name = result.name(),active = true,error = previousState.error,goals = previousState.goals)
                }
            }
        }
    }
}