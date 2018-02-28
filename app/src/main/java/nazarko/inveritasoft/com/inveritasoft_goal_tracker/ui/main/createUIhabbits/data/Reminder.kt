package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.createUIhabbits.data

import java.util.*

/**
 * Created by nazarko on 28.02.18.
 */
data class Reminder(var date:Date,var days:List<DayOfWeek> )

enum class DayOfWeek(val dayNumber: Int) {
    SUNDAY(1) ,
    MONDAY(2),
    TUESDAY(3),
    WEDNESDAY(4),
    THURSDAY(5),
    FRIDAY(6),
    SATURDAY(7)
}