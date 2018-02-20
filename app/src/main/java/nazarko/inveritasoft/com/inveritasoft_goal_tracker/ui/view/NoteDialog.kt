package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.view


import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import com.prolificinteractive.materialcalendarview.CalendarDay
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.viewmodel.MainViewModel
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.util.HabitsViewModelFactory
import java.text.SimpleDateFormat


/**
 * Created by nazarko on 20.02.18.
 */
class NoteTwoDialog : DialogFragment(){


    interface NodeDialogListener{
        fun  cancelComment(data:CalendarDay,comment: String)
        fun deleteComment(data:CalendarDay,comment: String)
        fun setComment(data:CalendarDay,comment: String)
    }

    private var listener: NoteDialog.NodeDialogListener? = null

    private val viewModel: MainViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders
                .of(this, HabitsViewModelFactory.getInstance(activity))
                .get(MainViewModel::class.java)
    }






    companion object {
        val TAG ="NoteDialog"
        val BUNDLE_DIALOG_DATE = "bundle:date"

        fun show(activity: Activity, date: CalendarDay) {
            NoteDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(BUNDLE_DIALOG_DATE, date)
                }
            }.show(activity.fragmentManager, TAG)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is NoteDialog.NodeDialogListener) {
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_note, null)
        var date =  arguments.getParcelable(NoteDialog.BUNDLE_DIALOG_DATE) as CalendarDay
        var dateFormatDayFormatter = SimpleDateFormat("dd MMM yyyy")

        view.findViewById<TextView>(R.id.dateSubtitleText).text =resources.getString(R.string.write_note
        )+ " ${dateFormatDayFormatter.format(date.date)}.";

        val commentEdit =  view.findViewById<EditText>(R.id.commentEdit)

        view.findViewById<View>(R.id.cancel).setOnClickListener(View.OnClickListener {
            listener?.cancelComment(date,commentEdit.text.trim().toString())
            dismiss()
        })
        view.findViewById<View>(R.id.set).setOnClickListener(View.OnClickListener {
            listener?.setComment(date,commentEdit.text.trim().toString())
            dismiss()
        })
        view.findViewById<View>(R.id.delete).setOnClickListener(View.OnClickListener {
            listener?.deleteComment(date,commentEdit.text.trim().toString())
            dismiss()
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

}