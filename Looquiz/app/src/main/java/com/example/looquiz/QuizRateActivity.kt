package com.example.looquiz

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.act_myroom.*
import kotlinx.android.synthetic.main.act_quizrate.*
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.net.URLEncoder

class QuizRateActivity : AppCompatActivity() {

    var cityList = arrayListOf<QuizRate>(
        //QuizRate(R.drawable.gyeong,"경복궁",6)
    )
    var cityname :String?=null
    var cityimg = mapOf<String,Int>(Pair("경복궁",R.drawable.gyeong))
    val mAdapter=QuizAdapter(this,cityList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_quizrate)

        cityname = intent.getStringExtra("cityname")

       // val mAdapter=QuizAdapter(this,cityList)
        quizrate_rv.adapter=mAdapter

        val lm = LinearLayoutManager(this)
        quizrate_rv.layoutManager = lm
        quizrate_rv.setHasFixedSize(true)

        Asynctask().execute(getString(R.string.scuccesrate))
    }

    inner class Asynctask: AsyncTask<String, Void, String>() {
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            var client = OkHttpClient()
            var url = params[0]


            url+= URLEncoder.encode(cityname,"utf-8")
            //url+= cityname
            Log.d("check",url)
            response = Okhttp(applicationContext).GET(client, url)

            return response
        }

        override fun onPostExecute(result: String) {

            if(!result.isNullOrEmpty())
                Log.d("check",result)

            if(!result[0].equals('{')) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext,"네트워크 연결이 좋지 않습니다", Toast.LENGTH_SHORT).show()
                return
            }
            else{
                var json = JSONObject(result)
                if (json.getInt("message") == 1) {

                    var json = JSONObject(result)
                    var jsonary = json.getJSONArray("data")

                    for(i in 0 until jsonary.length()){
                        var jsonrate = jsonary[0] as JSONObject
                        var rname : String = jsonrate.getString("rname")
                        var oCount : Int = jsonrate.getInt("oCount")
                        mAdapter.cityList.add(QuizRate(cityimg.get(rname),rname,oCount))
                        mAdapter.notifyDataSetChanged()

                    }
                }
            }
        }
    }
}
