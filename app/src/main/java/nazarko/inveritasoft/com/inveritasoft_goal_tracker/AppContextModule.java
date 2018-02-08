package nazarko.inveritasoft.com.inveritasoft_goal_tracker;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nazarko on 08.02.18.
 */

@Module
public class AppContextModule {
    private final Context context;

    public AppContextModule(@AppContext Context context) {
        this.context = context;
    }

    @Provides
    @AppContext
    Context getContext() {
        return context;
    }
}