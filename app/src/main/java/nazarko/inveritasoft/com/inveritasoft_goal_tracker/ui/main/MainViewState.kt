package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import com.example.android.architecture.blueprints.todoapp.mvibase.MviViewState

/**
 * Created by nazarko on 15.02.18.
 */
data class MainViewState(val str:String):MviViewState{
    companion object {
        fun idle(): MainViewState {
            return MainViewState(
                    str = ""
            )
        }
    }
}