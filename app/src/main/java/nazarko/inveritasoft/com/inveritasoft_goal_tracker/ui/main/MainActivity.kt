package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import android.os.Bundle
import android.view.View
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.HighlightWeekendsDecorator
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.OneDaySelectedDecorator
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.SelectedManyDecorator
import java.util.*
import kotlin.collections.HashSet

class MainActivity : HabitsActivity(),OnDateSelectedListener {

    lateinit var selectedManyDecorator:SelectedManyDecorator;
    lateinit var oneDaySelectedDecorator:OneDaySelectedDecorator;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initviews()
    }

    private fun initviews() {
        var calendar = Calendar.getInstance();
        calendarView.currentDate =  CalendarDay.from(calendar.time);
        calendarView.setOnDateChangedListener(this)

        selectedManyDecorator = SelectedManyDecorator(this,HashSet<CalendarDay>())
       // oneDaySelectedDecorator = OneDaySelectedDecorator(this,HashSet<CalendarDay>())

        calendarView.addDecorators(HighlightWeekendsDecorator(),selectedManyDecorator)
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
        calendarView.setDateTextAppearance(R.style.CalendarDateTextAppearance)
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        selectedManyDecorator.addDate(date)
        //oneDaySelectedDecorator.addDate(date)
        calendarView.invalidateDecorators()
    }
}
