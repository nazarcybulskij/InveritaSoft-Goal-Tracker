package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import com.example.android.architecture.blueprints.todoapp.mvibase.MviAction

/**
 * Created by nazarko on 15.02.18.
 */

sealed class MainAction:MviAction{
    data class InitialAction(var str:String) : MainAction()

}
