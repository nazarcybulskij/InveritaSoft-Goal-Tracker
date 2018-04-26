package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.past

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.support.v4.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.Goal
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.ResultDay
import java.util.*

/**
 * Created by nazarko on 13.02.18.
 */
class PastDateRigthDaySuccessDecorator(var context: Context, val goalsMap:HashMap<CalendarDay,Goal>) : DayViewDecorator {

    var drawable: Drawable = ContextCompat.getDrawable(context, R.drawable.success_circle_background)
    var circledrawable: Drawable = ContextCompat.getDrawable(context,R.drawable.rigth_day)
    var finalDrawable:LayerDrawable

    private val today = CalendarDay.today()
    private val calendar = Calendar.getInstance()

    init{
        finalDrawable = LayerDrawable(arrayOf(drawable,circledrawable))
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        val nextBoolean:Boolean
        val todayBoolean:Boolean = goalsMap[day]?.result == ResultDay.SUCCESS && today.isAfter(day!!)
        calendar.time = day?.date
        calendar.add(Calendar.DATE, 1)
        val nextDate = CalendarDay.from(calendar.time)
        nextBoolean = goalsMap[nextDate]?.result == ResultDay.SUCCESS
        return  todayBoolean  && nextBoolean && goalsMap[day]?.iscomment == false
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(finalDrawable)
    }

}