package com.example.looquiz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.act_signin.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_signin)



        signin_btnsignin.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        signin_btnsignup.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }

        signin_btnfind.setOnClickListener {
            startActivity(Intent(this,FindIDActivity::class.java))
        }

    }
}
