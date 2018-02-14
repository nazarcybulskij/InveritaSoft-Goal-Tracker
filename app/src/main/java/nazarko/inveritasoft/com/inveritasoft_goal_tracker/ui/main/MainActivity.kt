package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.prolificinteractive.materialcalendarview.*
import kotlinx.android.synthetic.main.activity_main.*
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.future.*
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decoratorsold.HighlightWeekendsDecorator
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.Goal
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.ResultDay
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.view.NoteDialog
import java.util.*
import kotlin.collections.HashMap

class MainActivity : HabitsActivity(),OnDateSelectedListener, OnDateLongSelectedListener {

    lateinit var dateFailDecorator: FutureDateFailDecorator;
    lateinit var dateSuccessDecorator: FutureDateSuccessDecorator;

    lateinit var pastFailDecorator: PastDateFailDecorator;
    lateinit var pastSuccessDecorator: PastDateSuccessDecorator;
    lateinit var pastLeftDaySuccessDecorator: PastDateLeftDaySuccessDecorator;
    lateinit var pastRigthDaySuccessDecorator: PastDateRigthDaySuccessDecorator;
    lateinit var pastTwoDaySuccessDecorator: PastDateTwoDaySuccessDecorator;

    lateinit var todayFailDecorator: TodayDateFailDecorator;
    lateinit var todaySuccessDecorator: TodayDateSuccessDecorator;
    lateinit var todayDateOnlyDecorator: TodayDateOnlyDecorator;
    lateinit var todayLeftDayOnlyDecorator: TodayDateLeftDaySuccessDecorator;

    var goals = HashMap<CalendarDay, Goal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initviews()
    }

    private fun initviews() {
        var calendar = Calendar.getInstance();
        calendarView.currentDate = CalendarDay.from(calendar.time);
        calendarView.setOnDateChangedListener(this)
        calendarView.setOnDateLongClickListener(this)
        dateFailDecorator = FutureDateFailDecorator(this, goals)
        dateSuccessDecorator = FutureDateSuccessDecorator(this, goals)

        todayFailDecorator = TodayDateFailDecorator(this, goals)
        todaySuccessDecorator = TodayDateSuccessDecorator(this, goals)
        todayDateOnlyDecorator = TodayDateOnlyDecorator(this, goals)
        todayLeftDayOnlyDecorator = TodayDateLeftDaySuccessDecorator(this, goals)

        pastFailDecorator = PastDateFailDecorator(this, goals)
        pastSuccessDecorator = PastDateSuccessDecorator(this, goals)
        pastLeftDaySuccessDecorator = PastDateLeftDaySuccessDecorator(this, goals)
        pastRigthDaySuccessDecorator = PastDateRigthDaySuccessDecorator(this, goals)
        pastTwoDaySuccessDecorator = PastDateTwoDaySuccessDecorator(this, goals)

        calendarView.addDecorators(
                HighlightWeekendsDecorator(),

                dateFailDecorator,
                dateSuccessDecorator,

                todayDateOnlyDecorator,
                todayFailDecorator,
                todaySuccessDecorator,
                todayLeftDayOnlyDecorator,

                pastFailDecorator,
                pastSuccessDecorator,
                pastLeftDaySuccessDecorator,
                pastRigthDaySuccessDecorator,
                pastTwoDaySuccessDecorator
        )

        weekButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                calendarView.state().edit()
                        .setCalendarDisplayMode(CalendarMode.WEEKS)
                        .commit()
            }
        });
        monthButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                calendarView.state().edit()
                        .setCalendarDisplayMode(CalendarMode.MONTHS)
                        .commit()
            }
        });
        calendarView.setDateTextAppearance(R.style.CalendarDateTextAppearance)
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        var goal = goals.get(date);

        if (goal == null) {
            goal = Goal(ResultDay.SUCCESS, false)
            goals.put(date, goal)
        } else {
            when (goal.result) {
                ResultDay.FAIL -> goal.result = ResultDay.NONE
                ResultDay.SUCCESS -> goal.result = ResultDay.FAIL
                ResultDay.NONE -> goal.result = ResultDay.SUCCESS
            }
        }

        dateFailDecorator.update(goal!!, date)
        dateSuccessDecorator.update(goal!!, date)
        todayFailDecorator.update(goal!!, date)
        todaySuccessDecorator.update(goal!!, date)

        calendarView.clearSelection();
        calendarView.invalidateDecorators()
    }



    override fun onDateLongSelected(widget: MaterialCalendarView, date: CalendarDay) {
        NoteDialog.show(this,date)
    }



}
