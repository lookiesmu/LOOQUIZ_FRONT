package com.example.looquiz

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.ListFragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.list_fragment.*

/**
 * A simple [Fragment] subclass.
 *
 */

class ListOfRoom : Fragment() {

    val mNicolasCageMovies = listOf(
        roomlist_dataclass("Raising Arizona", "1987"),
        roomlist_dataclass("Vampire's Kiss", "1988"),
        roomlist_dataclass("Con Air", "1997"),
        roomlist_dataclass("Gone in 60 Seconds", "1997"),
        roomlist_dataclass("National Treasure", "2004")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.list_fragment, container, false)

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here
        list_recycler_view.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = ListAdapter(mNicolasCageMovies)
        }
    }

    companion object {
        fun newInstance(): ListOfRoom = ListOfRoom()
    }
}