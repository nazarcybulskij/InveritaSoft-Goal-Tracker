package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import com.example.android.architecture.blueprints.todoapp.mvibase.MviAction
import com.prolificinteractive.materialcalendarview.CalendarDay

/**
 * Created by nazarko on 15.02.18.
 */

sealed class MainAction:MviAction{
    data class InitialAction(var str:String) : MainAction()
    data class DataClickAction(var  date: CalendarDay) : MainAction()
    data class DataLongClickAction(var str:String) : MainAction()


}
