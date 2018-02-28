package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.view


import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import com.example.android.architecture.blueprints.todoapp.mvibase.MviView
import com.prolificinteractive.materialcalendarview.CalendarDay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.dialog_note.*
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.CommentViewState
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.MainIntent
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.MainViewState
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.Goal
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.viewmodel.CommentViewModel
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.viewmodel.MainViewModel
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.util.HabitsViewModelFactory
import java.text.SimpleDateFormat


/**
 * Created by nazarko on 20.02.18.
 */
class NoteDialog : DialogFragment(), MviView<MainIntent, CommentViewState> {

    private val deleteCommentIntentPublisher = PublishSubject.create<MainIntent.CommentDeleteIntent>()
    private val cancelCommentIntentPublisher = PublishSubject.create<MainIntent.CancelCommentIntent>()
    private val setCommentIntentPublisher = PublishSubject.create<MainIntent.CommentSetIntent>()

    lateinit var date : CalendarDay;
    lateinit var goal : Goal;
    lateinit var commentEdit : TextView


    interface NodeDialogListener {
        fun cancelComment(data: CalendarDay)
        fun deleteComment(data: CalendarDay, comment: String)
        fun setComment(data: CalendarDay, comment: String)
    }

    private var listener: NoteDialog.NodeDialogListener? = null

    private val viewModel: CommentViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders
                .of(this, HabitsViewModelFactory.getInstance(activity))
                .get(CommentViewModel::class.java)
    }

    // Used to manage the data flow lifecycle and avoid memory leak.
    private val disposables = CompositeDisposable()

    companion object {
        val TAG = "NoteDialog"
        val BUNDLE_DIALOG_DATE = "bundle:date"
        val BUNDLE_DIALOG_GOAL = "bundle:goal"

        fun show(activity: AppCompatActivity, date: CalendarDay,goal:Goal) {
            NoteDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(BUNDLE_DIALOG_DATE, date)
                    putParcelable(BUNDLE_DIALOG_GOAL, goal)
                }
            }.show(activity.supportFragmentManager, TAG)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        bind()
        if (context is NoteDialog.NodeDialogListener) {
            listener = context
        }
    }

    override fun onDetach() {
        disposables.clear()
        super.onDetach()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_note, null)
        date = arguments.getParcelable(NoteDialog.BUNDLE_DIALOG_DATE) as CalendarDay
        goal = arguments.getParcelable(NoteDialog.BUNDLE_DIALOG_GOAL) as Goal
        var dateFormatDayFormatter = SimpleDateFormat("dd MMM yyyy")

        view.findViewById<TextView>(R.id.dateSubtitleText).text = resources.getString(R.string.write_note
        ) + " ${dateFormatDayFormatter.format(date.date)}.";
        commentEdit = view.findViewById<EditText>(R.id.commentEdit)

        commentEdit.append(goal.comment)


        view.findViewById<View>(R.id.cancel).setOnClickListener(View.OnClickListener {
            cancelCommentIntentPublisher.onNext(MainIntent.CancelCommentIntent())
        })

        view.findViewById<View>(R.id.set).setOnClickListener(View.OnClickListener {
            setCommentIntentPublisher.onNext(MainIntent.CommentSetIntent(date, commentEdit.text.trim().toString()))
        })
        view.findViewById<View>(R.id.delete).setOnClickListener(View.OnClickListener {
            deleteCommentIntentPublisher.onNext(MainIntent.CommentDeleteIntent(date, commentEdit.text.trim().toString()))
        })
        return AlertDialog.Builder(activity, R.style.NoteDialogThema)
                .setView(view)
                .create()
                .apply {
                    setCanceledOnTouchOutside(false)
                    window.setGravity(Gravity.TOP)
                    window.requestFeature(Window.FEATURE_NO_TITLE);
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
                    val params = window.attributes
                    params.y = 100
                    window.attributes = params
                }
    }

    private fun bind() {
        // Subscribe to the ViewModel and call render for every emitted state
        //disposables.add(viewModel.states().subscribe(this::render))
        disposables.add(viewModel.states().subscribe(
                { result -> render(result) },
                { error -> Log.e("TAG", "{$error.message}") },
                { Log.d("TAG", "completed") }
        )
        )
        // Pass the UI's intents to the ViewModel
        viewModel.processIntents(intents())
    }

    override fun intents(): Observable<MainIntent> = Observable.merge(deleteCommentIntentPublisher,setCommentIntentPublisher,cancelCommentIntentPublisher)

    override fun render(state: CommentViewState) {
        if (state.delete){
            listener?.deleteComment(date, commentEdit.text.trim().toString())
            dismiss()
            return
        }

        if (state.set){
            listener?.setComment(date, commentEdit.text.trim().toString())
            dismiss()
            return
        }

        if (state.cancel){
            listener?.cancelComment(date)
            dismiss()
            return
        }

    }


}