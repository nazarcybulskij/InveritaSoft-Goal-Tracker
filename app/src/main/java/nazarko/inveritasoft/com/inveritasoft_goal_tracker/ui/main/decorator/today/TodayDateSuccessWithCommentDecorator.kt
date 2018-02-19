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
class TodayDateSuccessWithCommentDecorator(var context: Context, val goalsMap:HashMap<CalendarDay,Goal>) : DayViewDecorator {

    lateinit var drawable: Drawable
    lateinit var circledrawable: Drawable
    lateinit var todaydrawable: Drawable
    lateinit var commentdrawable: Drawable

    lateinit var finalDrawable:Drawable

    private var today = CalendarDay.today()

    init{
        drawable = ContextCompat.getDrawable(context, R.drawable.success_circle_background)
        todaydrawable = ContextCompat.getDrawable(context,R.drawable.today)
        circledrawable = ContextCompat.getDrawable(context,R.drawable.circle_day)
        commentdrawable = ContextCompat.getDrawable(context, R.drawable.comment)
        finalDrawable = LayerDrawable(arrayOf(drawable,todaydrawable,circledrawable,commentdrawable)).mutate()
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        var todayBoolean:Boolean
        todayBoolean = today.equals(day!!)  &&  goalsMap.get(day)?.result == ResultDay.SUCCESS
        return  todayBoolean && goalsMap.get(day)?.iscomment == true

    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(finalDrawable)
    }
}