package com.example.looquiz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.act_findid.*

class FindIDActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_findid)

        findid_btnfindpw.setOnClickListener {
            startActivity(Intent(this,FindPWActivity::class.java))
            finish()
        }
    }
}
