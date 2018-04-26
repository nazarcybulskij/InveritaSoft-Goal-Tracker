package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.mvi.MviAction
import com.prolificinteractive.materialcalendarview.CalendarDay

/**
 * Created by nazarko on 15.02.18.
 */

sealed class MainAction: MviAction {

    data class InitialAction(var str:String) : MainAction()
    data class DataClickAction(var  date: CalendarDay) : MainAction()

    data class ShowCommentDialogAction(var  date: CalendarDay) : MainAction()


    data class DeleteCommentAction(var  date: CalendarDay) : MainAction()
    class CancelAction : MainAction()
    data class SetCommentAction(var  date: CalendarDay,var comment:String) : MainAction()


}
