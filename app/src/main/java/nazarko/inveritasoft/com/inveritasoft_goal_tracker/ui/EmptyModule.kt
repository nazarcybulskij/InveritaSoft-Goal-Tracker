package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui

import android.content.Context
import dagger.Module
import dagger.Provides
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.AppScope
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.util.Empty

/**
 * Created by nazarko on 09.02.18.
 */
@Module
class EmptyModule {

    @Provides
    @ActivityScope
    fun getEmpty(context: Context): Empty {
        return Empty(context)
    }

}