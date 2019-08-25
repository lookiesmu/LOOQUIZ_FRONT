package com.example.looquiz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.act_mypage.*

class MyPageActivity : AppCompatActivity() {

    var corplist = arrayOf("*  항목1","*  힝목2","*  항목3","*  항목4" )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_mypage)


        mypage_btnquizrate.setOnClickListener {
            startActivity(Intent(this,RateMapActivity::class.java))
        }

        mypage_btncorplist.setOnClickListener {

            var builder = AlertDialog.Builder(this)
            builder.setTitle(" 제휴 리스트 ")

            builder.setNegativeButton("닫기",null)
            builder.setItems(corplist,null)
            builder.show()

        }
    }
}
