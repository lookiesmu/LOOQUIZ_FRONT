package com.example.looquiz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.act_ratemap.*

class RateMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_ratemap)

        ratemap_btnplace_seoul.setOnClickListener {

            startActivity(Intent(this, QuizRateActivity::class.java))

        }
    }
}
