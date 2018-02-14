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
 * Created by nazarko on 13.02.18.
 */
class CommentDecorator(var context: Context, val dates: HashSet<CalendarDay>): DayViewDecorator {

    lateinit var commentdrawable: Drawable

    lateinit var finalDrawable: LayerDrawable


    init{
        commentdrawable = ContextCompat.getDrawable(context,R.drawable.comment)
        finalDrawable = LayerDrawable(arrayOf(commentdrawable))
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return  dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(finalDrawable)
    }

    public fun addDate(date:CalendarDay) = dates.add(date)
}