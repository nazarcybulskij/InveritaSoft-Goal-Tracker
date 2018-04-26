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
class PastDateFailWithCommentDecorator(var context: Context, val goalsMap:HashMap<CalendarDay,Goal> ) : DayViewDecorator {

    val drawable: Drawable = ContextCompat.getDrawable(context, R.drawable.fail_circle_background)
    val commentdrawable: Drawable = ContextCompat.getDrawable(context, R.drawable.comment)

    val finalDrawable:LayerDrawable

    private val today = CalendarDay.today()


    init{
        finalDrawable = LayerDrawable(arrayOf(drawable,commentdrawable))
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return  goalsMap[day]?.result == ResultDay.FAIL && today.isAfter(day!!) && goalsMap[day]?.iscomment == true
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(finalDrawable)
    }


}