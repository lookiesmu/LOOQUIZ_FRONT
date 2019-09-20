package com.example.looquiz

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.android.synthetic.main.room_choose.*

class ListOfRoomFragment() : Fragment() {
    lateinit var adapter : ListAdapter
    val mNicolasCageMovies = ArrayList<roomlist_dataclass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ListAdapter(this.context!!,mNicolasCageMovies)
        list_recycler_view.adapter = adapter
        list_recycler_view.layoutManager = LinearLayoutManager(activity)
    }

    companion object {
        fun newInstance(): ListOfRoomFragment = ListOfRoomFragment()
    }

    fun getFragmentAdapter():ListAdapter{
        return adapter
    }
}