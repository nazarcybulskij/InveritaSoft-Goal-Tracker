package nazarko.inveritasoft.com.inveritasoft_goal_tracker

import android.content.Context
import dagger.Module
import dagger.Provides
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.util.DB

/**
 * Created by nazarko on 08.02.18.
 */
@Module
class HabitsModule {

    @Provides
    @AppScope
    fun getDB(context:Context): DB {
        return DB(context)
    }

}
