package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import com.prolificinteractive.materialcalendarview.*
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.mvi.MviIntent
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.mvi.MviView
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.mvi.MviViewModel
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.mvi.MviViewState
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.future.FutureDateFailDecorator
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.future.FutureDateFailWithCommentDecorator
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.future.FutureDateSuccessDecorator
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.future.FutureDateSuccessWithCommentDecorator
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.other.HighlightWeekendsDecorator
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.other.NoneWithCommentDecorator
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.past.*
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.decorator.today.*
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.Goal
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.viewmodel.MainViewModel
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.view.NoteDialog
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.util.HabitsViewModelFactory
import java.util.*

class MainActivity : HabitsActivity(),
        OnDateSelectedListener,
        OnDateLongSelectedListener,
        NoteDialog.NodeDialogListener,
        MviView<MainIntent, MainViewState> {

    // Used to manage the data flow lifecycle and avoid memory leak.
    private val disposables = CompositeDisposable()

    private val dateClickIntentPublisher = PublishSubject.create<MainIntent.DataClickIntent>()

    private val showCommentDialogPublisher = PublishSubject.create<MainAction.ShowCommentDialogAction>()






    private val viewModel: MainViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders
                .of(this, HabitsViewModelFactory.getInstance(this))
                .get(MainViewModel::class.java)
    }




    private lateinit var dateFailDecorator: FutureDateFailDecorator
    private lateinit var dateSuccessDecorator: FutureDateSuccessDecorator
    private lateinit var dateFailWithCommentDecorator: FutureDateFailWithCommentDecorator
    private lateinit var dateSuccessWithCommentDecorator: FutureDateSuccessWithCommentDecorator

    private lateinit var pastFailDecorator: PastDateFailDecorator
    private lateinit var pastFailWithCommentDecorator: PastDateFailWithCommentDecorator
    private lateinit var pastSuccessDecorator: PastDateSuccessDecorator
    private lateinit var pastSuccessWithCommentDecorator: PastDateSuccessWithCommentDecorator
    private lateinit var pastLeftDaySuccessDecorator: PastDateLeftDaySuccessDecorator
    private lateinit var pastLeftDaySuccessWithCommentDecorator: PastDateLeftDaySuccessWithCommentDecorator
    private lateinit var pastRigthDaySuccessDecorator: PastDateRigthDaySuccessDecorator
    private lateinit var pastRigthDaySuccessWithCommentDecorator: PastDateRigthDaySuccessWithCommentDecorator
    private lateinit var pastTwoDaySuccessDecorator: PastDateTwoDaySuccessDecorator
    private lateinit var pastTwoDaySuccessWithCommentDecorator: PastDateTwoDaySuccessWithCommentDecorator

    private lateinit var todayFailDecorator: TodayDateFailDecorator
    private lateinit var todayFailWithCommentDecorator: TodayDateFailWithCommentDecorator
    private lateinit var todaySuccessDecorator: TodayDateSuccessDecorator
    private lateinit var todaySuccessWithCommentDecorator: TodayDateSuccessWithCommentDecorator
    private lateinit var todayDateOnlyDecorator: TodayDateOnlyDecorator
    private lateinit var todayDateOnlyWithCommentDecorator: TodayDateOnlyWithCommentDecorator
    private lateinit var todayLeftDayOnlyDecorator: TodayDateLeftDaySuccessDecorator
    private lateinit var todayLeftDayOnlyWithCommentDecorator: TodayDateLeftDaySuccessWithCommentDecorator


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

        disposables.add(viewModel.navigates().subscribe(
                { result ->  navigateTo(result) },
                { error -> Log.e("TAG", "{$error.message}")},
                { Log.d("TAG", "completed") }
        )
        )

        // Pass the UI's intents to the ViewModel
        viewModel.processIntents(intents())
        viewModel.processNavigate(navigate())
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
        val calendar = Calendar.getInstance()
        calendarView.currentDate = CalendarDay.from(calendar.time)
        calendarView.setOnDateChangedListener(this)
        calendarView.setOnDateLongClickListener(this)
        weekButton.setOnClickListener {
            calendarView.state().edit()
                    .setCalendarDisplayMode(CalendarMode.WEEKS)
                    .commit()
        }
        monthButton.setOnClickListener {
            calendarView.state().edit()
                    .setCalendarDisplayMode(CalendarMode.MONTHS)
                    .commit()
        }
        calendarView.setDateTextAppearance(R.style.CalendarDateTextAppearance)
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        dateClickIntentPublisher.onNext(MainIntent.DataClickIntent(date))
    }

    override fun cancelComment(data: CalendarDay) {

    }

    override fun deleteComment(data: CalendarDay, comment: String) {
        calendarView.invalidateDecorators()
    }

    override fun setComment(data: CalendarDay, comment: String) {
        calendarView.invalidateDecorators()
    }



    override fun onDateLongSelected(widget: MaterialCalendarView, date: CalendarDay) {
        showCommentDialogPublisher.onNext(MainAction.ShowCommentDialogAction(date))
    }

    override fun intents(): Observable<MainIntent> {
        return Observable.merge(initialIntent(), dateClickIntentPublisher)
    }

     private fun navigate(): Observable<MainAction> {
        return  showCommentDialogPublisher.cast(MainAction::class.java)
     }


    private fun initialIntent():Observable<MainIntent.InitialIntent> = Observable.just(MainIntent.InitialIntent(""))



    override fun render(state: MainViewState) {
        Log.d("TAG",state.toString())
        if (!state.active){
            initCalendar(state.goals)
            Log.d("TAG","init")
        }else{
            if (!state.loading){
                calendarView.invalidateDecorators()
                Log.d("TAG","invalidate")
            }
        }

    }

    private fun navigateTo(result: NavigationTarget) {
        if (result is NavigationTarget.ShowCommentDialog){
            NoteDialog.show(this,result.data,result.goal)
        }
    }



}
