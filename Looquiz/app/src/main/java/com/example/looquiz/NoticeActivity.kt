package com.example.looquiz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ExpandableListView
import java.lang.Exception
import android.opengl.ETC1.getWidth
import android.util.DisplayMetrics
import kotlin.math.exp


class NoticeActivity : AppCompatActivity() {

    lateinit var expListView: ExpandableListView
    lateinit var chapterList: MutableList<chapterNotice>
    var sectionList: MutableList<sectionNotice> = ArrayList()
    lateinit var Chapter_Name: Array<String>
    lateinit var Section_Name: Array<String>
    lateinit var c: chapterNotice
    lateinit var s: sectionNotice
    lateinit var listAdapter: NoticeListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        chapterList = ArrayList()
        prepareListData()

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        var width = displayMetrics.widthPixels
        expListView.setIndicatorBounds(width - 120, width - 30)

    }

    private fun prepareListData() {
        Chapter_Name = resources.getStringArray(R.array.chapter)
        for (i in 0..Chapter_Name.size - 1)
        {
            var k: String = "chapter${i + 1}"
            var id: Int = resources.getIdentifier(k,"array",this.packageName)
            try {
                Section_Name = resources.getStringArray(id)
            }catch (e: Exception)
            {
                Section_Name = arrayOf()
            }
            for (j in 0..Section_Name.size - 1)
            {
                c = chapterNotice(Section_Name[j])
                chapterList.add(c)
            }
            s = sectionNotice(Chapter_Name[i], chapterList)
            sectionList.add(s)
            chapterList = ArrayList()
        }
        displayList()
    }

    private fun displayList() {
        expListView = findViewById(R.id.lvExp) as ExpandableListView
        listAdapter = NoticeListAdapter(this,sectionList)
        expListView.setAdapter(listAdapter)
    }

}
