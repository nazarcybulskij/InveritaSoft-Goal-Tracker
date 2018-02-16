package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import com.example.android.architecture.blueprints.todoapp.mvibase.MviResult
import com.prolificinteractive.materialcalendarview.CalendarDay
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.Goal

/**
 * Created by nazarko on 15.02.18.
 */
sealed class MainResult:MviResult {

    open fun name():String = "MainResult"



    sealed class InitResult : MainResult(){
        data class Success(val str:String) : InitResult(){
            override fun name() ="InitResult.Success"
        }
        data class Failure(val error: Throwable) : InitResult(){
            override fun name() ="InitResult.Failure"
        }
        object InFlight : InitResult(){
            override fun name() ="InitResult.InFlight"
        }
    }

    sealed class DateResult : MainResult(){
        data class Success(var goals:HashMap<CalendarDay, Goal>) : DateResult(){
            override fun name() ="DateResult.Success"
        }
        data class Failure(val error: Throwable) : DateResult(){
            override fun name() ="DateResult.Failure"
        }
        object InFlight : DateResult(){
            override fun name() ="DateResult.InFlight"
        }
    }


    sealed class DateLongResult : MainResult(){
        data class Success(val str:String) : DateLongResult(){
            override fun name() ="DateLongResult.Success"
        }
        data class Failure(val error: Throwable) : DateLongResult(){
            override fun name() ="DateLongResult.Failure"
        }
        object InFlight : DateLongResult(){
            override fun name() ="DateLongResult.InFlight"
        }
    }


}