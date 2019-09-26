package com.example.looquiz

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.act_myroom.*
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject

class MyRoomActivity : AppCompatActivity() {

    var memberlist: Array<String>? = null
    var quizlist = ArrayList<Quizlist>()
    var myroom_roomcodenum: String? = null
    var myroom_roomtitle:String? = null
    var rname:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_myroom)
        Asynctask().execute("0",getString(R.string.room_quizlist),myroom_roomcodenum)

        myroom_roomtitle = intent.getStringExtra("title")
        myroom_roomcodenum = intent.getStringExtra("codenum")

        myroom_title.text = myroom_roomtitle
        myroom_codenum.text = myroom_roomcodenum
        myroom_rname.text = rname


        val quizlistAdapter = QuizlistAdapter(this, this.myroom_roomcodenum!!,quizlist,true)
        myroom_rv.adapter=quizlistAdapter

        val quizlistlm = LinearLayoutManager(this)
        myroom_rv.layoutManager =quizlistlm
        myroom_rv.setHasFixedSize(true)


        //멤버조회
        myroom_member.setOnClickListener {

            Asynctask().execute(getString(R.string.search_mem),myroom_roomcodenum)
            var builder = AlertDialog.Builder(this)
            builder.setTitle(myroom_roomtitle.toString())

            builder.setNegativeButton("닫기",null)
            builder.setItems(memberlist,null)
            builder.show()
        }
        myroom_createquiz.setOnClickListener {
            startActivity(Intent(this,MakingQuizActivity::class.java))

        }
    }
    inner class Asynctask: AsyncTask<String, Void, String>() {
        var response : String? = null
        var state :Int? = -1// 0 = quizlist, 1 = memlist

        override fun doInBackground(vararg params: String): String? {
            var client = OkHttpClient()
            state = params[0].toInt()
            var url = params[1]
            myroom_roomcodenum = params[2]

            if (state == 0) {
                response = Okhttp(applicationContext).POST(client, url, CreateJson().json_roomquizlist(myroom_roomcodenum))
            }
            else {
                response = Okhttp(applicationContext).POST(client, url, CreateJson().json_searchmem(myroom_roomcodenum))
            }
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

                if(state ==0){
                    if (json.getInt("message") == 1) {

                        var json = JSONObject(result)
                        var jsonary = json.getJSONArray("data")
                     //   var message = json.getInt("message")

                        for(i in 0 until jsonary.length()){
                            var jsonquiz = jsonary[0] as JSONObject
                            var qid : Int = jsonquiz.getInt("qid")
                            var dname : String = jsonquiz.getString("dname")
                            var qname : String = jsonquiz.getString("qname")
                            myroom_rname.text = jsonquiz.getString("rname")
                            quizlist.add(Quizlist(qname,dname,qid))
                        }
                    }
                }
                else{
                    if (json.getInt("message") == 1) {
                        var jsonArray = json.getJSONArray("data")
                        var memary = Array<String>(jsonArray.length(),{""})
                        for(i in 0 until jsonArray.length()){
                            var member:JSONObject = jsonArray[i] as JSONObject
                            memary?.set(i,member.getString("uname"))
                        }
                        memberlist = memary
                        Log.d("check",memberlist.toString())
                    }
                    else {
                        Toast.makeText(applicationContext,"일치하는 정보가 없습니다", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }
}



