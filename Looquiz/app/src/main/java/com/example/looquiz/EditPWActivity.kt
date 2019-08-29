package com.example.looquiz

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast

class EditPWActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_edit_pw)

        var checkPWBuilder = AlertDialog.Builder(this)
        var checkPWBuilder2:AlertDialog = checkPWBuilder.create()
        var checkPWView = layoutInflater.inflate(R.layout.dia_checkpw, null)
        checkPWBuilder.setView(checkPWView)

        var checkPWListener = object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                //통신
            }
        }
        var negativeListener = object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                Toast.makeText(applicationContext, "비밀번호가 옳지 않습니다.", Toast.LENGTH_LONG).show()
                //startActivity(Intent(applicationContext, SettingActivity::class.java))
                finish()
            }
        }
        checkPWBuilder.setPositiveButton("확인", checkPWListener)
        checkPWBuilder.setNegativeButton("닫기", negativeListener)

        checkPWBuilder.show()








    }
}
