package com.example.looquiz

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.act_ratemap.*
import okhttp3.OkHttpClient
import org.json.JSONObject

class RateMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_ratemap)

        ratemap_btnplace_seoul.setOnClickListener {

            startActivity(Intent(this, QuizRateActivity::class.java))

        }
    }
    inner class Asynctask: AsyncTask<String, Void, String>() {
        var state = -1 //GET_badge = 0 , GET_citylist = 1 ,GET_corplist = 2
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])
            var client = OkHttpClient()
            var url = params[1]

            if(state == 0){
                response = Okhttp(applicationContext).GET(client,url)

            }
            else if(state == 1) {
                response = Okhttp(applicationContext).GET(client, url)
            }

            else if(state == 2) {
                var bid = params[2]
                url = url+"${bid}"
                response = Okhttp(applicationContext).GET(client, url)
            }

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
                if(state == 0){
                    if (json.getInt("message") == 1) {
                        Toast.makeText(applicationContext,"비밀번호 확인 되었습니다", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(applicationContext,"비밀번호 확인 실패하였습니다", Toast.LENGTH_SHORT).show()
                    }
                }
                else if(state == 1){
                    if (json.getInt("message") == 1) {
                        Toast.makeText(applicationContext,"새 비밀번호가 저장되었습니다", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(applicationContext,"비밀번호 수정에 실패하였습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
