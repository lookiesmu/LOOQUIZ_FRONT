package com.example.looquiz

import android.content.ContentValues.TAG
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

class roomlist_ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
    private var mroomCView: TextView? = null
    private var mroomNView: TextView? = null


    init {
        mroomCView = itemView.findViewById(R.id.list_title)
        mroomNView = itemView.findViewById(R.id.list_description)
    }

    fun bind(roomList: roomlist_dataclass) {
        mroomCView?.text = roomList.roomC
        mroomNView?.text = roomList.roomN.toString()
    }


}