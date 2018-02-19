package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R

/**
 * Created by nazarko on 19.02.18.
 */

enum class FragmentAction {
    ADD, REPLACE
}

sealed class NavigationTarget {

    data class Finish(
            val result: Int? = null,
            val data: Map<String, Int>? = null
    ) : NavigationTarget()

    data class Action(val action: String, val uri: Uri) : NavigationTarget()


    data class Activity(
            val name: Class<*>,
            val args: Parcelable? = null,
            val requestCode: Int = -1
    ) : NavigationTarget()

    data class Fragment(
            val instance: android.support.v4.app.Fragment,
            val action: FragmentAction,
            val transition: Int = -1
    ) : NavigationTarget()

}

const val KEY_ACTIVITY_ARGS = "KEY_ACTIVITY_ARGS"

fun navigateTo(activity: FragmentActivity, target: NavigationTarget) {
    when (target) {
        is NavigationTarget.Finish -> navigateFinish(activity, target)
        is NavigationTarget.Action -> navigateAction(activity, target)
        is NavigationTarget.Activity -> navigateActivity(activity, target)
        is NavigationTarget.Fragment -> navigateFragment(activity, target)
    }
}

private fun navigateFinish(activity: FragmentActivity, target: NavigationTarget.Finish) {
    if (target.result != null) {
        if (target.data != null) {
            val resultData = Intent().apply {
                target.data.forEach { key, value -> putExtra(key, value) }
            }
            activity.setResult(target.result, resultData)
        } else {
            activity.setResult(target.result)
        }
    }
    ActivityCompat.finishAfterTransition(activity)
}
private fun navigateAction(activity: FragmentActivity, target: NavigationTarget.Action) {
    val intent = Intent(target.action, target.uri)
    activity.startActivity(intent)
}

private fun navigateActivity(activity: FragmentActivity, target: NavigationTarget.Activity) {
    val intent = Intent(activity, target.name).apply {
        putExtra(KEY_ACTIVITY_ARGS, target.args)
    }
    if (target.requestCode > -1) {
        activity.startActivityForResult(intent, target.requestCode)
    } else {
        activity.startActivity(intent)
    }
}

private fun navigateFragment(
        activity: FragmentActivity,
        target: NavigationTarget.Fragment,
        container: Int = R.id.container_main
) {
    activity.supportFragmentManager.beginTransaction().apply {
        when (target.action) {
            FragmentAction.ADD -> add(container, target.instance)
            FragmentAction.REPLACE -> replace(container, target.instance)
        }

        if (target.transition > -1) {
            setTransition(target.transition)
        }
    }.commit()
}





