package nazarko.inveritasoft.com.inveritasoft_goal_tracker.util

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import javax.inject.Inject

/**
 * Created by nazarko on 09.02.18.
 */
class Empty (var context: Context) {

    fun toast() = Toast.makeText(context,"empty",Toast.LENGTH_SHORT).show()

}
