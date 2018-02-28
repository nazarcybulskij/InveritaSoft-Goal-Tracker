package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.createUIhabbits

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R

/**
 * Created by nazarko on 28.02.18.
 */
class CreateRemindersAdapter(var data:MutableList<UserDto>): RecyclerView.Adapter<CreateRemindersAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.user_list_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var userDto = data[position]
        holder?.txtName?.text = userDto.name
        holder?.txtComment?.text = userDto.comment
    }


    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        var txtName: TextView? = null
        var txtComment: TextView? = null

        init {
            this.txtName = row?.findViewById<TextView>(R.id.txtName)
            this.txtComment = row?.findViewById<TextView>(R.id.txtComment)
        }
    }
}