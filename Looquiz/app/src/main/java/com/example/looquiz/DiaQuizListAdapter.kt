package com.example.looquiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextClock
import android.widget.TextView

class DiaQuizListAdapter(val context: Context, val quizList:ArrayList<String>): BaseAdapter(){

    override fun getCount(): Int {
        return quizList.size
    }

    override fun getItem(position: Int): Any {
        return quizList[position]
    }

    override fun getItemId(position: Int): Long {
        return quizList[position].toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.diaquiz_listitem, null)
        val quiz5 = view.findViewById<TextView>(R.id.quiz5)

        val quizStr = quizList[position]
        quiz5.text = quizStr
        return view
    }
}