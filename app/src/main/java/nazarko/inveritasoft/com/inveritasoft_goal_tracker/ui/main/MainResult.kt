package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import com.example.android.architecture.blueprints.todoapp.mvibase.MviResult

/**
 * Created by nazarko on 15.02.18.
 */
sealed class MainResult:MviResult {

    sealed class InitResult : MainResult(){
        data class Success(val str:String) : InitResult()
        data class Failure(val error: Throwable) : InitResult()
        object InFlight : InitResult()
    }
}