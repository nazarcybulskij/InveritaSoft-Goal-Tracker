package nazarko.inveritasoft.com.inveritasoft_goal_tracker.util

import android.content.Context
import android.widget.Toast

/**
 * Created by nazarko on 08.02.18.
 */
class Dao(val context: Context) {

    fun toast() = Toast.makeText(context,"dao",Toast.LENGTH_SHORT).show()


}