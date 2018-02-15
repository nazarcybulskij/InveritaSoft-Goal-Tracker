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
import java.util.HashSet

/**
 * Created by nazarko on 13.02.18.
 */
class TodayDateOnlyWithCommentDecorator(var context: Context, val goalsMap:HashMap<CalendarDay,Goal> ) : DayViewDecorator {

    lateinit var todaydrawable: Drawable
    lateinit var commentdrawable: Drawable

    lateinit var finalDrawable:LayerDrawable

    private var today = CalendarDay.today()

    init{
        todaydrawable = ContextCompat.getDrawable(context,R.drawable.today)
        commentdrawable = ContextCompat.getDrawable(context, R.drawable.comment)
        finalDrawable = LayerDrawable(arrayOf(todaydrawable,commentdrawable))
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return today.equals(day!!) && goalsMap.get(day)?.iscomment == true

    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(finalDrawable)
    }

    public fun update(goal: Goal,date:CalendarDay) {
        goalsMap.put(date,goal)
    }


}