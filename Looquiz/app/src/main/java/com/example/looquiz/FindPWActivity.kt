package com.example.looquiz

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.act_findid.*
import kotlinx.android.synthetic.main.act_findpw.*
import okhttp3.OkHttpClient
import org.json.JSONObject

//추가 : 이메일로 임시비밀번호가 전송되었습니다 메세지

class FindPWActivity : AppCompatActivity() {

    var url:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_findpw)

        findpw_btnok.setOnClickListener {
            Asynctask().execute(getString(R.string.findPW),findpw_inputID.text.toString()
                ,"${findpw_inputEMAIL.text.toString()}@${findpw_spinner.selectedItem}")

            url = "${getString(R.string.findPW)}${findpw_inputID.text.toString()}${findpw_inputEMAIL.text.toString()}@${findpw_spinner.selectedItem}"
            Log.d("okhttp",url)
        }


    }
    inner class Asynctask: AsyncTask<String, Void, String>() {
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            var client = OkHttpClient()
            var url = params[0]
            var uid = params[1]
            var email = params[2]

            url = url + "${uid}?email=${email}"
            response = Okhttp(applicationContext).GET(client,url)

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

                    Toast.makeText(applicationContext,"임시비밀번호가 해당 이메일로 발송되었습니다", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(applicationContext,"일치하는 정보가 없습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

