package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decoratorsold

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*

/**
 * Created by nazarko on 09.02.18.
 */
class HighlightWeekendsDecorator:DayViewDecorator {


    private var calendar = Calendar.getInstance()
    private lateinit var highlightDrawable: Drawable
    private var color = Color.parseColor("#228BC34A")

    init{
        highlightDrawable = ColorDrawable(color)
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        var weekDay = calendar.get(Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(highlightDrawable)
    }
}