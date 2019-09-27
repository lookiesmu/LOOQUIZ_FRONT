package com.example.looquiz

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.act_signin.*
import okhttp3.OkHttpClient
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_signin)

        val view = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                // 23 버전 이상일 때 상태바 하얀 색상에 회색 아이콘 색상을 설정
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = Color.parseColor("#FFFFFF")
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            window.statusBarColor = Color.BLACK
        }

        signin_checkbox.setOnClickListener { SharedClass(applicationContext).setAuto(signin_checkbox.isChecked) }
        if(SharedClass(applicationContext).getAuto() && !SharedClass(applicationContext).getToken().isNullOrEmpty())
            startActivity(Intent(applicationContext,MainActivity::class.java))


        signin_btnsignin.setOnClickListener {
            Asynctask().execute(
                getString(R.string.signin),
                signin_inputID.text.toString(),
                signin_inputPW.text.toString()
            )
        }

        signin_btnsignup.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }

        signin_btnfind.setOnClickListener {
            startActivity(Intent(this,FindIDActivity::class.java))
        }
    }
    inner class Asynctask: AsyncTask<String, Void, String>() {
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            var client = OkHttpClient()
            var url = params[0]
            var uid = params[1]
            var pw = params[2]

            response = Okhttp(applicationContext).POST(client,url,CreateJson().json_signin(uid,pw))

            return response
        }

        override fun onPostExecute(result: String) {


            Log.d("checkcommu",result)
            if(!result.isNullOrEmpty())
                Log.d("checktest",result)

            if(!result[0].equals('{')) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext,"네트워크 연결이 좋지 않습니다", Toast.LENGTH_SHORT).show()

                return
            }
            else{
                var json = JSONObject(result)

                if (json.getInt("message") == 1) {
                    var data = json.getJSONObject("data")
                    var uid  = data.getString("uid")
                    Toast.makeText(applicationContext,"${uid}님 환영합니다", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext,MainActivity::class.java))
                    finish()
                }
                else {
                    Toast.makeText(applicationContext,"일치하는 정보가 없습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}