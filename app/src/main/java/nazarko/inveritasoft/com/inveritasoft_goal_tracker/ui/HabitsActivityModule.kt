package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui

import android.content.Context
import dagger.Module
import dagger.Provides
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.util.Dao

/**
 * Created by nazarko on 08.02.18.
 */
@Module
class HabitsActivityModule {

    @Provides
    @ActivityScope
    fun getDao(context: Context): Dao {
        return Dao(context)
    }

}