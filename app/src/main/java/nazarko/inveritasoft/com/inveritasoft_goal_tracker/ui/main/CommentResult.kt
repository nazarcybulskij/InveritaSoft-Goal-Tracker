package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.mvi.MviResult
import com.prolificinteractive.materialcalendarview.CalendarDay
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model.Goal

/**
 * Created by nazarko on 19.02.18.
 */

sealed class CommentResult: MviResult {

    open fun name():String = "CommentResult"

    sealed class SetCommentResult : CommentResult(){
        data class Success(var goals:HashMap<CalendarDay, Goal>) : SetCommentResult(){
            override fun name() ="SetCommentResult.Success"
        }
        data class Failure(val error: Throwable) : SetCommentResult(){
            override fun name() ="SetCommentResult.Failure"
        }
        object InFlight : SetCommentResult(){
            override fun name() ="SetCommentResult.InFlight"
        }
    }

    sealed class DeleteCommentResult : CommentResult(){
        data class Success(var goals:HashMap<CalendarDay, Goal>) : DeleteCommentResult(){
            override fun name() ="DeleteCommentResult.Success"
        }
        data class Failure(val error: Throwable) : DeleteCommentResult(){
            override fun name() ="DeleteCommentResult.Failure"
        }
        object InFlight : DeleteCommentResult(){
            override fun name() ="DeleteCommentResult.InFlight"
        }
    }

    sealed class CancelCommentResult : CommentResult(){
        class Success : CancelCommentResult(){
            override fun name() ="CancelCommentResult.Success"
        }
    }



}