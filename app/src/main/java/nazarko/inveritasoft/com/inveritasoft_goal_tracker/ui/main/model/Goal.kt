package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model

import android.os.Parcel
import android.os.Parcelable
import com.prolificinteractive.materialcalendarview.CalendarDay

/**
 * Created by nazarko on 13.02.18.
 */
data class Goal(var result: ResultDay, var iscomment: Boolean, var comment: String? = "") : Parcelable {

    constructor(source: Parcel) : this(
            ResultDay.values()[source.readInt()],
            1 == source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(result.ordinal)
        writeInt((if (iscomment) 1 else 0))
        writeString(comment)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Goal> = object : Parcelable.Creator<Goal> {
            override fun createFromParcel(source: Parcel): Goal = Goal(source)
            override fun newArray(size: Int): Array<Goal?> = arrayOfNulls(size)
        }
    }
}