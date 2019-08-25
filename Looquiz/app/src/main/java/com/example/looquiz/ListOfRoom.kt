package com.example.looquiz

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class ListOfRoom : ListFragment() {

    var textView: TextView? = null
    var list = arrayOf("경복궁관광1", "경복궁관광2", "경복궁관광3", "경복궁관광4", "경복궁관광5")
    var str1:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.list_fragment, container, false)

        textView = view.findViewById<TextView>(R.id.textView)

        var adapter = ArrayAdapter<String>(
            activity, android.R.layout.simple_list_item_1, list
        )

        listAdapter = adapter

        return view
    }

/*    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
    }*/

}
