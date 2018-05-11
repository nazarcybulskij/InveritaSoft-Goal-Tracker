package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.createUIhabbits

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.*
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.createUIhabbits.data.DayOfWeek
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.createUIhabbits.data.Reminder
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by nazarko on 28.02.18.
 */
class CreateRemindersDialog: DialogFragment() {





    private lateinit var  mTimeTextView: TextView


    private var shareViewModel: CreateHabbitShareViewModel? = null

    private val cal = Calendar.getInstance()!!

    var reminder = Reminder(cal.time, arrayOf(DayOfWeek.SUNDAY,DayOfWeek.MONDAY,DayOfWeek.TUESDAY,DayOfWeek.WEDNESDAY,DayOfWeek.THURSDAY,DayOfWeek.FRIDAY,DayOfWeek.SATURDAY))

    private val dateFormat = SimpleDateFormat("hh:mm a",Locale.US)
    private val timeListener = TimePickerDialog.OnTimeSetListener{ view, hour, minute ->
        cal.set(Calendar.HOUR,hour)
        cal.set(Calendar.MINUTE,minute)
        reminder.date = cal.time
        mTimeTextView.text = dateFormat.format(cal.time).toUpperCase()
    }

    companion object {
        private const val TAG = "CreateRemindersDialog"

        fun show(activity: FragmentActivity) {
            CreateRemindersDialog().apply {
                arguments = Bundle().apply {

                }
            }.show(activity.supportFragmentManager, TAG)
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_create_reminders, null)
        view.findViewById<View>(R.id.cancel).setOnClickListener{
            dismiss()
        }
        view.findViewById<View>(R.id.ok).setOnClickListener{
            shareViewModel?.createReminder?.value = UserDto("name","comments")
            dismiss()
        }


        var checkBoxs = (view as ViewGroup).getAllViewByTag("tag")

        for (checkBox in checkBoxs){
            checkBox.setOnClickListener {
                if((it as CheckBox).isChecked()) {
                    Log.d("TAG", "Checked ${checkBoxs.indexOf(it)}")
                    reminder.days[checkBoxs.indexOf(it)] =  DayOfWeek.from(checkBoxs.indexOf(it))
                }else {
                    Log.d("TAG", "Un-Checked ${checkBoxs.indexOf(it)} ")
                    reminder.days[checkBoxs.indexOf(it)] =  DayOfWeek.NONE
                }

            }
        }


        mTimeTextView =   view.findViewById(R.id.time)
        mTimeTextView.text = dateFormat.format(cal.time).toUpperCase()
        mTimeTextView.setOnClickListener{
            TimePickerDialog(context,timeListener,cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE),false).show()
        }
        return AlertDialog.Builder(activity, R.style.CreateDialogThema)
                .setView(view)
                .create()
                .apply {
                    setCanceledOnTouchOutside(false)
                    window.setGravity(Gravity.TOP)
                    window.requestFeature(Window.FEATURE_NO_TITLE)
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
                    val params = window.attributes
                    params.y = 100
                    window.attributes = params
                }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        shareViewModel = ViewModelProviders.of(activity).get(CreateHabbitShareViewModel::class.java)
    }

}