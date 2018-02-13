package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import java.util.*

/**
 * Created by nazarko on 13.02.18.
 */
class OneDaySelectedDecorator(var context:Context,val dates: HashSet<CalendarDay>): DayViewDecorator {

    lateinit var drawable: Drawable
    lateinit var soliddrawable: Drawable

    init{
        drawable = ContextCompat.getDrawable(context, R.drawable.circle_background)
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return  dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(drawable)
    }

    public fun addDate(date:CalendarDay) = dates.add(date)
}