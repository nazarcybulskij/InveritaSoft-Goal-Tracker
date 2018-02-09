package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator

import android.graphics.Typeface
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

/**
 * Created by nazarko on 09.02.18.
 */
class ClickDecorator:DayViewDecorator {

    lateinit var date:CalendarDay

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return date != null && day == date
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(StyleSpan(Typeface.BOLD))
        view?.addSpan(RelativeSizeSpan(1.4f))
    }
}