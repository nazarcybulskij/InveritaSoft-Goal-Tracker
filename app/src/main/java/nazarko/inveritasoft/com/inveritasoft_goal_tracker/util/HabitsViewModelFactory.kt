package nazarko.inveritasoft.com.inveritasoft_goal_tracker.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.viewmodel.CommentViewModel
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.viewmodel.MainViewModel

/**
 * Created by nazarko on 15.02.18.
 */
class HabitsViewModelFactory private constructor(
        private val applicationContext: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == MainViewModel::class.java) {
            return  MainViewModel() as T
        }
        if (modelClass == CommentViewModel::class.java) {
            return  CommentViewModel() as T
        }
        throw IllegalArgumentException("unknown model class $modelClass")
    }

    companion object : SingletonHolder<HabitsViewModelFactory, Context>(::HabitsViewModelFactory)

}