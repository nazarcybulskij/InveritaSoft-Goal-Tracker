package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decoratorsold

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.support.v4.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import java.util.HashSet

/**
 * Created by nazarko on 12.02.18.
 */
class SelectedManyDecorator(var context: Context,val dates: HashSet<CalendarDay>): DayViewDecorator {

    lateinit var twoDayDrawable: Drawable
    lateinit var leftDayDrawable: Drawable
    lateinit var rigthDayDrawable: Drawable
    lateinit var oneDayDrawable: Drawable

    lateinit var soliddrawable: Drawable
    lateinit var todaydrawable: Drawable

    lateinit var finalDrawable:LayerDrawable



    init{
        twoDayDrawable = ContextCompat.getDrawable(context, R.drawable.two_day)
        leftDayDrawable = ContextCompat.getDrawable(context, R.drawable.left_day)
        rigthDayDrawable = ContextCompat.getDrawable(context, R.drawable.rigth_day)
        oneDayDrawable = ContextCompat.getDrawable(context, R.drawable.circle_day)
        soliddrawable = ContextCompat.getDrawable(context,R.drawable.fail_circle_background)
        todaydrawable = ContextCompat.getDrawable(context,R.drawable.today)
        finalDrawable = LayerDrawable(arrayOf(todaydrawable,soliddrawable,twoDayDrawable))
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return  dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(finalDrawable)
    }

    public fun addDate(date:CalendarDay) = dates.add(date)
}