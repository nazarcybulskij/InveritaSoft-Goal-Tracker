package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.createUIhabbits

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import com.prolificinteractive.materialcalendarview.CalendarDay
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.MainIntent
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.Goal
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.view.NoteDialog
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by nazarko on 28.02.18.
 */
class CreateRemindersDialog: DialogFragment() {

    lateinit var  mTimeTextView: TextView

    val cal = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("hh:mm a")
    val timeListener = TimePickerDialog.OnTimeSetListener{view,hour,minute ->
        cal.set(Calendar.HOUR,hour)
        cal.set(Calendar.MINUTE,minute)
        mTimeTextView.text = dateFormat.format(cal.time).toUpperCase()
    }

    companion object {
        val TAG = "CreateRemindersDialog"

        fun show(activity: FragmentActivity) {
            CreateRemindersDialog().apply {
                arguments = Bundle().apply {

                }
            }.show(activity.supportFragmentManager, TAG)
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_create_reminders, null)
        view.findViewById<View>(R.id.cancel).setOnClickListener(View.OnClickListener {
            dismiss()
        })
        view.findViewById<View>(R.id.ok).setOnClickListener(View.OnClickListener {
            dismiss()
        })
        mTimeTextView =   view.findViewById<TextView>(R.id.time)
        mTimeTextView.text = dateFormat.format(cal.time).toUpperCase()
        mTimeTextView.setOnClickListener(View.OnClickListener {
            TimePickerDialog(context,timeListener,cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE),false).show()
        })
        return AlertDialog.Builder(activity, R.style.CreateDialogThema)
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