package com.example.looquiz

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

class ListAdapter(private val list: List<roomlist_dataclass>)
    : RecyclerView.Adapter<roomlist_ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): roomlist_ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return roomlist_ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: roomlist_ViewHolder, position: Int) {
        val roomList: roomlist_dataclass = list[position]
        holder.bind(roomList)
        holder.itemView.setOnClickListener {
            Log.d(TAG,"Clicked: ${list.get(position).roomC}")
            startActivity(Intent(,RoomInfo::class.java))//방 목록에서 방누르면 방정보로 넘어가는 코드인데 오류생김...
        }
    }

    override fun getItemCount(): Int = list.size
}