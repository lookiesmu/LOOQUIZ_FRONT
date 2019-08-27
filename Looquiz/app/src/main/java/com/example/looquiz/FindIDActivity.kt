package com.example.looquiz

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.act_findid.*
import kotlinx.android.synthetic.main.act_signup.*
import okhttp3.OkHttpClient
import org.json.JSONObject

class FindIDActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_findid)

        findid_btnok.setOnClickListener {
            Asynctask().execute(getString(R.string.findID),findid_inputNAME.text.toString(),"${findid_inputEMAIL.text.toString()}@${findid_spinner.selectedItem}")
        }
        findid_btnfindpw.setOnClickListener {
            startActivity(Intent(this,FindPWActivity::class.java))
            finish()
        }
    }
    inner class Asynctask: AsyncTask<String, Void, String>() {
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            var client = OkHttpClient()
            var url = params[0]
            var uname = params[1]
            var email = params[2]

            url = url + "${uname}?email=${email}"
            response = Okhttp().GET(client,url)

            return response
        }

        override fun onPostExecute(result: String) {

            Log.d("checktest",result)

            if(!result[0].equals('{')) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext,"네트워크 연결이 좋지 않습니다", Toast.LENGTH_SHORT).show()
                return
            }
            else{
                var json = JSONObject(result)

                if (json.getInt("message") == 1) {
                    var uid = json.getJSONObject("uid")
                    Toast.makeText(applicationContext,"회원님의 아이디는 ${uid} 입니다", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(applicationContext,"일치하는 정보가 없습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
