package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import com.prolificinteractive.materialcalendarview.CalendarDay

/**
 * Created by nazarko on 19.02.18.
 */
sealed class NavigationTarget {

    data class ShowCommentDialog(var data:CalendarDay) : NavigationTarget()

}