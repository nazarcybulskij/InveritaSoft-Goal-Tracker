package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.future

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
class PastDateLeftDaySuccessWithCommentDecorator(var context: Context, val goalsMap:HashMap<CalendarDay,Goal>) : DayViewDecorator {

    lateinit var drawable: Drawable
    lateinit var circledrawable: Drawable
    lateinit var commentdrawable: Drawable
    lateinit var finalDrawable:LayerDrawable

    private var today = CalendarDay.today()
    private val calendar = Calendar.getInstance()

    init{
        drawable = ContextCompat.getDrawable(context, R.drawable.success_circle_background)
        circledrawable = ContextCompat.getDrawable(context,R.drawable.left_day)
        commentdrawable = ContextCompat.getDrawable(context, R.drawable.comment)
        finalDrawable = LayerDrawable(arrayOf(drawable,circledrawable,commentdrawable))
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        var prevBoolean:Boolean
        var todayBoolean:Boolean
        calendar.setTime(day?.date)
        calendar.add(Calendar.DATE, -1)
        var prevDate = CalendarDay.from(calendar.time)
        prevBoolean = goalsMap.get(prevDate)?.result == ResultDay.SUCCESS && today.isAfter(prevDate!!)
        todayBoolean = goalsMap.get(day)?.result == ResultDay.SUCCESS && today.isAfter(day!!)
        return  todayBoolean && prevBoolean && goalsMap.get(day)?.iscomment == true

    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(finalDrawable)
    }
}