package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.createUIhabbits

import android.view.View
import android.view.ViewGroup
import java.util.ArrayList


fun ViewGroup.getAllViewByTag(tag: String): ArrayList<View> {
    val views = ArrayList<View>()
    val childCount = getChildCount()
    for (i in 0 until childCount) {
        val child = getChildAt(i)
        if (child is ViewGroup)
            views.addAll(child.getAllViewByTag(tag))
        val tagObj = child.getTag()
        if (tagObj != null && tagObj == tag)
            views.add(child)
    }
    return views
}
