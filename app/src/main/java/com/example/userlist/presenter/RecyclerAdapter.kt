package com.example.userlist.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.userlist.R
import com.example.userlist.model.User
import com.example.userlist.model.UserModel

class RecyclerAdapter(private var users: List<User>?) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val userView: TextView = itemView.findViewById(R.id.userView)
        val phoneView: TextView = itemView.findViewById(R.id.phoneView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPresenter = users?.get(position)


        holder.userView.text = itemPresenter?.userName
        holder.phoneView.text = itemPresenter?.phoneNumber

    }

    override fun getItemCount(): Int {
        return users?.size ?: 0
    }

}