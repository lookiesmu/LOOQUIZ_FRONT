package com.example.looquiz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.act_signup.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_signup)

        signup_btnSIGNUP.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
            finish()
        }
        signup_btnCHECK.setOnClickListener {

        }
    }
}
