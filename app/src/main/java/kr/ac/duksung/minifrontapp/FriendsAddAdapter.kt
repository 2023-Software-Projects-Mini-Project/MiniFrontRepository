package kr.ac.duksung.minifrontapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.friends_list.view.*


class FriendsAddAdapter (var userList : MutableList<String>) : RecyclerView.Adapter<FriendsAddAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsAddAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.friends_list, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: FriendsAddAdapter.ViewHolder, position: Int) {
        val item = userList[position]
        holder.apply {
            bind(item)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var view : View = v

        fun bind(item: String) {
            view.friends_1.text = item
        }
    }
}