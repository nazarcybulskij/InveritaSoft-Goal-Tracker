package nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.project;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by nazarko on 16.01.18.
 */

public class BaseFragment<T extends BaseFragment.ActivityListener>  extends Fragment implements LifecycleOwner {

    private T activityListener;

    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @NonNull
    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityListener = (T)context;
        }catch (ClassCastException ex){
            throw new ClassCastException(context.toString() + " must implement ActivityListener");
        }
    }

    @Override
    public void onDestroy() {
        activityListener = null;
        super.onDestroy();
    }


    public interface ActivityListener{

    }
}