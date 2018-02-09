package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R

/**
 * Created by nazarko on 09.02.18.
 */
class SelectedDecorator(context:Context):DayViewDecorator  {

    lateinit var drawable:Drawable

    init{
        drawable = ContextCompat.getDrawable(context,R.drawable.my_selector)
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
       return true;
    }

    override fun decorate(view: DayViewFacade?) {
       view?.setSelectionDrawable(drawable)
    }
}