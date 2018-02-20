package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import com.example.android.architecture.blueprints.todoapp.mvibase.MviIntent
import com.prolificinteractive.materialcalendarview.CalendarDay

/**
 * Created by nazarko on 15.02.18.
 */

sealed class MainIntent:MviIntent{

    data class InitialIntent(var str:String) : MainIntent()

    data class DataClickIntent(var  date: CalendarDay) : MainIntent()

    data class CommentSetIntent(var  date: CalendarDay,var comment:String) : MainIntent()

    data class CommentDeleteIntent(var  date: CalendarDay,var comment:String) : MainIntent()

    class CancelCommentIntent() : MainIntent()




}



