package com.example.looquiz

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.act_signup.*
import okhttp3.OkHttpClient
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_signup)

        signup_btnCHECK.setOnClickListener {
            Asynctask().execute("0",getString(R.string.signup_checkID),signup_inputID.text.toString())
        }
        signup_btnSIGNUP.setOnClickListener {
            if(signup_inputPW.text.toString().equals(signup_inputPW2.text.toString())) {
                Asynctask().execute(
                    "1",
                    getString(R.string.signup),
                    signup_inputID.text.toString()
                    ,
                    signup_inputPW.text.toString(),
                    signup_inputNAME.text.toString(),
                    "${signup_inputEMAIL.text.toString()}@${signup_spinner.selectedItem}"
                )
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }else{
                Toast.makeText(applicationContext,"비밀번호가 일치하지않습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class Asynctask: AsyncTask<String, Void, String>() {
        var state : Int = -1 // GET = 0, POST =1
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])
            var client = OkHttpClient()
            var url = params[1]
            var uid = params[2]

            if(state == 0){ //GET_idcheck
                url = url + "${uid}"
                response = Okhttp().GET(client, url)
            }
            else if (state == 1){ //POST_signup
                var pw = params[3]
                var uname = params[4]
                var email = params[5]

                response = Okhttp().POST(client,url,CreateJson().json_signup(uid,pw,uname,email))
            }
            return response
        }

        override fun onPostExecute(result: String) {
            Log.d("network2",result)

            if(!result[0].equals('{')) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext,"네트워크 연결이 좋지 않습니다", Toast.LENGTH_SHORT).show()
                return
            }

            var json = JSONObject(result)
            if(state == 0) { //GET_idcheck
                if (json.getInt("message").equals(1))
                    Toast.makeText(applicationContext,"사용가능한 아이디입니다", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(applicationContext,"이미 사용 중인 아이디입니다", Toast.LENGTH_SHORT).show()
            }
            else if (state == 1){ //POST_signup

                if(json.getInt("message").equals(1)){
                    Toast.makeText(applicationContext,"회원 가입에 성공했습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
