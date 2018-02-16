package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.android.architecture.blueprints.todoapp.mvibase.MviIntent
import com.example.android.architecture.blueprints.todoapp.mvibase.MviView
import com.example.android.architecture.blueprints.todoapp.mvibase.MviViewModel
import com.example.android.architecture.blueprints.todoapp.mvibase.MviViewState
import com.prolificinteractive.materialcalendarview.*
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.future.*
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.other.HighlightWeekendsDecorator
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.other.NoneWithCommentDecorator
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.Goal
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.ResultDay
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.viewmodel.MainViewModel
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.view.NoteDialog
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.util.HabitsViewModelFactory
import java.util.*
import kotlin.collections.HashMap

class MainActivity : HabitsActivity(),
        OnDateSelectedListener,
        OnDateLongSelectedListener,
        NoteDialog.NodeDialogListener,
        MviView<MainIntent, MainViewState> {

    // Used to manage the data flow lifecycle and avoid memory leak.
    private val disposables = CompositeDisposable()

    private val dateClickIntentPublisher = PublishSubject.create<MainIntent.DataClickIntent>()
    private val dateLongClickIntentPublisher = PublishSubject.create<MainIntent.DataLongClickIntent>()



    private val viewModel: MainViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders
                .of(this, HabitsViewModelFactory.getInstance(this))
                .get(MainViewModel::class.java)
    }




    lateinit var dateFailDecorator: FutureDateFailDecorator;
    lateinit var dateSuccessDecorator: FutureDateSuccessDecorator;
    lateinit var dateFailWithCommentDecorator: FutureDateFailWithCommentDecorator;
    lateinit var dateSuccessWithCommentDecorator: FutureDateSuccessWithCommentDecorator;

    lateinit var pastFailDecorator: PastDateFailDecorator;
    lateinit var pastFailWithCommentDecorator: PastDateFailWithCommentDecorator;
    lateinit var pastSuccessDecorator: PastDateSuccessDecorator;
    lateinit var pastSuccessWithCommentDecorator: PastDateSuccessWithCommentDecorator;
    lateinit var pastLeftDaySuccessDecorator: PastDateLeftDaySuccessDecorator;
    lateinit var pastLeftDaySuccessWithCommentDecorator: PastDateLeftDaySuccessWithCommentDecorator;
    lateinit var pastRigthDaySuccessDecorator: PastDateRigthDaySuccessDecorator;
    lateinit var pastRigthDaySuccessWithCommentDecorator: PastDateRigthDaySuccessWithCommentDecorator;
    lateinit var pastTwoDaySuccessDecorator: PastDateTwoDaySuccessDecorator;
    lateinit var pastTwoDaySuccessWithCommentDecorator: PastDateTwoDaySuccessWithCommentDecorator;

    lateinit var todayFailDecorator: TodayDateFailDecorator;
    lateinit var todayFailWithCommentDecorator: TodayDateFailWithCommentDecorator;
    lateinit var todaySuccessDecorator: TodayDateSuccessDecorator;
    lateinit var todaySuccessWithCommentDecorator: TodayDateSuccessWithCommentDecorator;
    lateinit var todayDateOnlyDecorator: TodayDateOnlyDecorator;
    lateinit var todayDateOnlyWithCommentDecorator: TodayDateOnlyWithCommentDecorator;
    lateinit var todayLeftDayOnlyDecorator: TodayDateLeftDaySuccessDecorator;
    lateinit var todayLeftDayOnlyWithCommentDecorator: TodayDateLeftDaySuccessWithCommentDecorator;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initviews()
        bind()
    }


    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    /**
     * Connect the [MviView] with the [MviViewModel]
     * We subscribe to the [MviViewModel] before passing it the [MviView]'s [MviIntent]s.
     * If we were to pass [MviIntent]s to the [MviViewModel] before listening to it,
     * emitted [MviViewState]s could be lost
     */
    private fun bind() {
        // Subscribe to the ViewModel and call render for every emitted state
        //disposables.add(viewModel.states().subscribe(this::render))

        disposables.add(viewModel.states().subscribe(
                { result ->  render(result) },
                { error -> Log.e("TAG", "{$error.message}")},
                { Log.d("TAG", "completed") }
        )
        )
        // Pass the UI's intents to the ViewModel
        viewModel.processIntents(intents())
    }


    private fun initCalendar(goals:HashMap<CalendarDay,Goal>){
        dateFailDecorator = FutureDateFailDecorator(this, goals)
        dateFailWithCommentDecorator = FutureDateFailWithCommentDecorator(this, goals)
        dateSuccessDecorator = FutureDateSuccessDecorator(this, goals)
        dateSuccessWithCommentDecorator = FutureDateSuccessWithCommentDecorator(this, goals)
        todayFailDecorator = TodayDateFailDecorator(this, goals)
        todayFailWithCommentDecorator = TodayDateFailWithCommentDecorator(this, goals)
        todaySuccessDecorator = TodayDateSuccessDecorator(this, goals)
        todaySuccessWithCommentDecorator = TodayDateSuccessWithCommentDecorator(this, goals)
        todayDateOnlyDecorator = TodayDateOnlyDecorator(this, goals)
        todayDateOnlyWithCommentDecorator = TodayDateOnlyWithCommentDecorator(this, goals)
        todayLeftDayOnlyDecorator = TodayDateLeftDaySuccessDecorator(this, goals)
        todayLeftDayOnlyWithCommentDecorator = TodayDateLeftDaySuccessWithCommentDecorator(this, goals)
        pastFailDecorator = PastDateFailDecorator(this, goals)
        pastFailWithCommentDecorator = PastDateFailWithCommentDecorator(this, goals)
        pastSuccessDecorator = PastDateSuccessDecorator(this, goals)
        pastSuccessWithCommentDecorator = PastDateSuccessWithCommentDecorator(this, goals)
        pastLeftDaySuccessDecorator = PastDateLeftDaySuccessDecorator(this, goals)
        pastLeftDaySuccessWithCommentDecorator = PastDateLeftDaySuccessWithCommentDecorator(this, goals)
        pastRigthDaySuccessDecorator = PastDateRigthDaySuccessDecorator(this, goals)
        pastRigthDaySuccessWithCommentDecorator = PastDateRigthDaySuccessWithCommentDecorator(this, goals)
        pastTwoDaySuccessDecorator = PastDateTwoDaySuccessDecorator(this, goals)
        pastTwoDaySuccessWithCommentDecorator = PastDateTwoDaySuccessWithCommentDecorator(this, goals)
        calendarView.addDecorators(
                HighlightWeekendsDecorator(),
                NoneWithCommentDecorator(this, goals),
                dateFailDecorator,
                dateFailWithCommentDecorator,
                dateSuccessDecorator,
                dateSuccessWithCommentDecorator,
                todayDateOnlyDecorator,
                todayDateOnlyWithCommentDecorator,
                todayFailDecorator,
                todayFailWithCommentDecorator,
                todaySuccessDecorator,
                todaySuccessWithCommentDecorator,
                todayLeftDayOnlyDecorator,
                todayLeftDayOnlyWithCommentDecorator,
                pastFailDecorator,
                pastFailWithCommentDecorator,
                pastSuccessDecorator,
                pastSuccessWithCommentDecorator,
                pastLeftDaySuccessDecorator,
                pastLeftDaySuccessWithCommentDecorator,
                pastRigthDaySuccessDecorator,
                pastRigthDaySuccessWithCommentDecorator,
                pastTwoDaySuccessDecorator,
                pastTwoDaySuccessWithCommentDecorator
        )
    }


    private fun initviews() {
        var calendar = Calendar.getInstance();
        calendarView.currentDate = CalendarDay.from(calendar.time);
        calendarView.setOnDateChangedListener(this)
        calendarView.setOnDateLongClickListener(this)
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
        dateClickIntentPublisher.onNext(MainIntent.DataClickIntent(date))
    }

    override fun cancelComment(data: CalendarDay, comment: String) {

    }

    override fun deleteComment(data: CalendarDay, comment: String) {
//        var goal = goals.get(data);
//        if (goal == null) {
//              goal = Goal(ResultDay.NONE, false)
//              goals.put(data, goal)
//        }
//        goal?.iscomment = false;

    }

    override fun setComment(data: CalendarDay, comment: String) {
//        var goal = goals.get(data);
//        if (goal == null) {
//            goal = Goal(ResultDay.NONE, true)
//        }
//        goal?.iscomment = true;
//        goals.put(data, goal)
//        calendarView.invalidateDecorators()
    }



    override fun onDateLongSelected(widget: MaterialCalendarView, date: CalendarDay) {
        NoteDialog.show(this,date)
        dateLongClickIntentPublisher.onNext(MainIntent.DataLongClickIntent(""))
    }

    override fun intents(): Observable<MainIntent> {
        return Observable.merge(initialIntent(), dataClickIntent(),dataLongClickIntent())
    }

    private fun initialIntent():Observable<MainIntent.InitialIntent> = Observable.just(MainIntent.InitialIntent(""))

    private fun dataClickIntent():Observable<MainIntent.DataClickIntent> = dateClickIntentPublisher

    private fun dataLongClickIntent():Observable<MainIntent.DataLongClickIntent> = dateLongClickIntentPublisher

    override fun render(state: MainViewState) {
        Log.d("TAG",state.toString())
        if (!state.active){
            initCalendar(state.goals)
            Log.d("TAG","init")
        }else{
            if (!state.loading){
                calendarView.clearSelection()
                calendarView.invalidateDecorators()
                Log.d("TAG","invalidate")
            }
        }

    }



}
