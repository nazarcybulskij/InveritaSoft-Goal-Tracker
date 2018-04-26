package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.today

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
class TodayDateSuccessWithCommentDecorator(var context: Context, val goalsMap:HashMap<CalendarDay,Goal>) : DayViewDecorator {

    val drawable: Drawable = ContextCompat.getDrawable(context, R.drawable.success_circle_background)
    val circledrawable: Drawable = ContextCompat.getDrawable(context,R.drawable.circle_day)
    private val todaydrawable: Drawable = ContextCompat.getDrawable(context,R.drawable.today)
    val commentdrawable: Drawable

    val finalDrawable:LayerDrawable

    private val today = CalendarDay.today()

    init{
        commentdrawable = ContextCompat.getDrawable(context, R.drawable.comment)
        finalDrawable = LayerDrawable(arrayOf(drawable,todaydrawable,circledrawable,commentdrawable))
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        val todayBoolean:Boolean = today == day!! &&  goalsMap[day]?.result == ResultDay.SUCCESS
        return  todayBoolean && goalsMap[day]?.iscomment == true

    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(finalDrawable)
    }
}