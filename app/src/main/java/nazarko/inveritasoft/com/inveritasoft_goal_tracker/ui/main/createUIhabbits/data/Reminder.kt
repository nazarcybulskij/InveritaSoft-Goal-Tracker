package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.createUIhabbits.data

import java.util.*

data class Reminder(var date:Date,var days:Array<DayOfWeek>)

 enum class DayOfWeek (val dayNumber: Int) {


    NONE(-1),
    SUNDAY(0) ,
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6);

     companion object {
         fun from(findValue: Int): DayOfWeek = DayOfWeek.values().first { it.dayNumber == findValue }
     }



}