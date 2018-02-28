package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.createUIhabbits

import android.app.assist.AssistStructure
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by nazarko on 28.02.18.
 */
class CreateHabbitShareViewModel :ViewModel() {
    var clickFab = MutableLiveData<Unit>()
    var createReminder = MutableLiveData<UserDto>()
}