package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.view

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import com.prolificinteractive.materialcalendarview.CalendarDay
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import android.R.attr.x
import android.view.*
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE



/**
 * Created by nazarko on 14.02.18.
 */
class NoteDialog : DialogFragment() {



    interface NodeDialogListener{

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
        return AlertDialog.Builder(activity, R.style.NoteDialogThema)
                .setView(view)
                .create()
                .apply {
                    setCanceledOnTouchOutside(false)
                    window.setGravity(Gravity.TOP)
                    val params = window.attributes
                    params.y = 100
                    window.attributes = params
                }
    }

}