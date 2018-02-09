package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
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
        var calendar = Calendar.getInstance();
        calendarView.currentDate =  CalendarDay.from(calendar.time);
        calendarView.setOnDateChangedListener(this)
        calendarView.addDecorators(HighlightWeekendsDecorator(),SelectedDecorator(this))
        weekButton.setOnClickListener( object : View.OnClickListener{
            override fun onClick(p0: View?) {
                calendarView.state().edit()
                        .setCalendarDisplayMode(CalendarMode.WEEKS)
                        .commit()
            }
        });
        monthButton.setOnClickListener( object : View.OnClickListener{
            override fun onClick(p0: View?) {
                calendarView.state().edit()
                        .setCalendarDisplayMode(CalendarMode.MONTHS)
                        .commit()
            }
        });
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        oneDayDecorator.date = date;
        calendarView.invalidateDecorators()
    }
}
