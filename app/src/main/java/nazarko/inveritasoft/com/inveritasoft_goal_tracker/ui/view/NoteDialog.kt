package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.view

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import android.view.*
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.dialog_note.*
import org.w3c.dom.Comment
import java.text.SimpleDateFormat


/**
 * Created by nazarko on 14.02.18.
 */
class NoteDialog : DialogFragment(){


    interface NodeDialogListener{
        fun  cancelComment(data:CalendarDay,comment: String)
        fun deleteComment(data:CalendarDay,comment: String)
        fun setComment(data:CalendarDay,comment: String)
    }

    companion object {
        val TAG ="NoteDialog"
        val BUNDLE_DIALOG_DATE = "bundle:date"

        fun show(activity: Activity,date:CalendarDay) {
            NoteDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(BUNDLE_DIALOG_DATE, date)
                }
            }.show(activity.fragmentManager, TAG)
        }
    }

    private var listener: NodeDialogListener? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is NodeDialogListener) {
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_note, null)
        var date =  arguments.getParcelable(BUNDLE_DIALOG_DATE) as CalendarDay
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
        return AlertDialog.Builder(activity,R.style.NoteDialogThema)
                .setView(view)
                .create()
                .apply {
                    setCanceledOnTouchOutside(false)
                    window.setGravity(Gravity.TOP)
                    window.requestFeature(Window.FEATURE_NO_TITLE);
                    window.setSoftInputMode(SOFT_INPUT_STATE_VISIBLE)
                    val params = window.attributes
                    params.y = 100
                    window.attributes = params
                }
    }



}