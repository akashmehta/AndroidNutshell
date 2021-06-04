package com.aakash.androidnutshell.usermodule.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aakash.androidnutshell.usermodule.dto.UserItemResponse
import com.aakash.androidnutshell.R

class UserItemsListAdapter(private val itemList: ArrayList<UserItemResponse>,
                           private val onItemClick: (position: Int) -> Unit) :
    RecyclerView.Adapter<UserItemsListAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.layout_user_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: UserItemResponse = itemList[position]
        holder.view.apply {
            if (this is TextView) {
                text = item.login
                setOnClickListener { onItemClick(position) }
            }
        }
    }

    override fun getItemCount(): Int = itemList.size
}