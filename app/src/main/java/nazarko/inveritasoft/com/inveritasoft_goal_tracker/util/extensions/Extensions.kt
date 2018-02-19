package nazarko.inveritasoft.com.inveritasoft_goal_tracker.util.extensions

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import io.reactivex.Observable

/**
 * Created by nazarko on 19.02.18.
 */

fun <T> Observable<T>.bindTo(lifecycleRegistry: LifecycleRegistry): Observable<T> = compose {
    it
            .takeWhile { lifecycleRegistry.currentState != Lifecycle.State.DESTROYED }
            .filter { lifecycleRegistry.currentState.isAtLeast(Lifecycle.State.STARTED) }
}

