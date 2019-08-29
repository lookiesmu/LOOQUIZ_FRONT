package com.example.looquiz

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.act_signin.*
import okhttp3.OkHttpClient
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_signin)



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
