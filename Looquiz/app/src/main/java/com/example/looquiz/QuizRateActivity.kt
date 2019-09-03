package com.example.looquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.act_quizrate.*

class QuizRateActivity : AppCompatActivity() {

    var cityList = arrayListOf<QuizRate>(
        QuizRate("gyeongbak","경복궁",6),
        QuizRate("gwanghwa","광화문",2),
        QuizRate("duck","덕수궁",8)

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_quizrate)


        val mAdapter=QuizAdapter(this,cityList)
        quizrate_rv.adapter=mAdapter

        val lm = LinearLayoutManager(this)
        quizrate_rv.layoutManager = lm
        quizrate_rv.setHasFixedSize(true)
    }
}
