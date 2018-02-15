package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.other

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.support.v4.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.Goal
import java.util.HashMap

/**
 * Created by nazarko on 15.02.18.
 */
class NoneWithCommentDecorator(var context: Context, val goalsMap: HashMap<CalendarDay, Goal> ) : DayViewDecorator {

    lateinit var commentdrawable: Drawable

    init{
        commentdrawable = ContextCompat.getDrawable(context, R.drawable.comment)
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return  goalsMap.get(day)?.iscomment == true
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(commentdrawable)
    }
}