package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.createUIhabbits

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by nazarko on 28.02.18.
 */
class CreateRemindersDialog: DialogFragment() {

    lateinit var  mTimeTextView: TextView


    private var shareViewModel: CreateHabbitShareViewModel? = null

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
            shareViewModel?.createReminder?.value = UserDto("name","comments")
            dismiss()
        })

//        for (checkbox in ViewTagRange(view)){
//            if (checkbox is CheckBox){
//                checkbox.setOnCheckedChangeListener{ buttonView, isChecked ->
//                    Toast.makeText(activity,"dfgdfgfdg",Toast.LENGTH_SHORT).show()
//                }
//            }
//        }


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

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        shareViewModel = ViewModelProviders.of(activity).get(CreateHabbitShareViewModel::class.java)
    }

    override fun onDetach() {
        super.onDetach()
    }

    class ViewTagRange(var view:View):Iterable<View>{
        override fun iterator(): Iterator<View> {
            return ViewIterator(this)
        }
    }

    class ViewIterator(final val range: ViewTagRange) : Iterator<View> {

        private var current: View = range.view.findViewWithTag<View>("tag")

        override fun hasNext() :Boolean {
            return current != null
        }

        override fun next() :View {
            current = range.view.findViewWithTag<CheckBox>("tag")
            return current
        }
    }

}