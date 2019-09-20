package com.example.looquiz

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_advice.*

class AdviceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advice)

        val listView = findViewById<ListView>(R.id.listview)

//        어답터 설정
        listView.adapter = MyCustomAdapter(this)

//        Item click listener
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectItem = parent.getItemAtPosition(position) as String
            when (selectItem) {
                "문제 신고" -> Toast.makeText(this, selectItem, Toast.LENGTH_SHORT).show()
                "악용 사례 신고" -> {
                    val url_Test = "https://www.google.com"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url_Test))
                    startActivity(intent)
                }
                "의견 보내기" -> 3
                "고객센터" -> 4
                "보안 도움말" -> 5
            }
            //Toast.makeText(this, selectItem, Toast.LENGTH_SHORT).show()
        }
    }

    //    Adapter class
    private class MyCustomAdapter(context: Context) : BaseAdapter() {
        private val mContext: Context

        //데이터 어레이
        private val names = arrayListOf<String>(
            "문제 신고", "악용 사례 신고", "의견 보내기", "고객센터", "보안 도움말"
        )

        init {
            mContext = context
        }
        override fun getCount(): Int {
            return names.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
        override fun getItem(position: Int): Any {
            val selectItem = names.get(position)
            return selectItem
        }
        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.row_advice, viewGroup, false)

            val nameTextView = rowMain.findViewById<TextView>(R.id.name_textview)
            nameTextView.text = names.get(position)

            return rowMain
        }
    }
}