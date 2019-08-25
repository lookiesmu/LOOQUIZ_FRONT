package com.example.looquiz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

//추가 : 이메일로 임시비밀번호가 전송되었습니다 메세지

class FindPWActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_findpw)

        startActivity(Intent(this,SignInActivity::class.java))
        finish()

    }
}
