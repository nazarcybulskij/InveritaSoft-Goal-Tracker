package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.model

import android.os.Parcel
import android.os.Parcelable

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

    companion object CREATOR : Parcelable.Creator<Goal> {
        override fun createFromParcel(parcel: Parcel): Goal {
            return Goal(parcel)
        }

        override fun newArray(size: Int): Array<Goal?> {
            return arrayOfNulls(size)
        }
    }

}