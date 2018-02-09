package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import android.os.Bundle
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.ClickDecorator
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.HighlightWeekendsDecorator
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.SelectedDecorator
import java.util.*

class MainActivity : HabitsActivity(),OnDateSelectedListener {

    var oneDayDecorator = ClickDecorator();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initviews()
    }

    private fun initviews() {
        calendarView.setOnDateChangedListener(this)
        calendarView.addDecorators(HighlightWeekendsDecorator(),SelectedDecorator(this))
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        oneDayDecorator.date = date;
        calendarView.invalidateDecorators()
    }
}
