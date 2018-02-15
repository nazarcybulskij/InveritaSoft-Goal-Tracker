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
class FutureDateFailDecorator(var context: Context, val goalsMap:HashMap<CalendarDay,Goal> ) : DayViewDecorator {

    lateinit var drawable: Drawable

    lateinit var finalDrawable:LayerDrawable

    private var today = CalendarDay.today()

    init{
        drawable = ContextCompat.getDrawable(context, R.drawable.fail_circle_background)
        finalDrawable = LayerDrawable(arrayOf(drawable))
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return  goalsMap.get(day)?.result == ResultDay.FAIL  && today.isBefore(day!!) && goalsMap.get(day)?.iscomment == false
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(finalDrawable)
    }

}