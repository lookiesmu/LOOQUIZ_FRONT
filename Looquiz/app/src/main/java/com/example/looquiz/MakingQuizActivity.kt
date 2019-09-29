package com.example.looquiz

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.act_making_quiz.*
import okhttp3.OkHttpClient
import org.json.JSONObject

class MakingQuizActivity : AppCompatActivity() {

    //var dnameList = arrayOf("", "", "", "", "", "", "", "", "", "")
    //var dnameList = mutableListOf<String>()
    var dnameList = arrayListOf<String>()
    //var selectedDname = ""
    var myroom_rname:String? = null
    var myroom_codenum: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_making_quiz)
/*
        Asynctask().execute("1", getString(R.string.region_quizlist), "경복궁")
        var spinnerAdapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, dnameList)
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        makingquiz_spinner.adapter = spinnerAdapter1
        makingquiz_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("position 확인", ""+position)
                //selectedDname = dnameList[position]
                Log.d("makingquiz_spinner 확인", ""+dnameList[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
*/
        if(intent.hasExtra("roomcodenum")){
            myroom_codenum = intent.getStringExtra("roomcodenum")
            Log.d("방 번호 확인1 ", ""+myroom_codenum)
        }
        /*if(intent.hasExtra("rname")){
            myroom_rname = intent.getStringExtra("rname")
            Log.d("방 지역 확인1", ""+myroom_rname)
        }
        myroom_rname = intent.getStringExtra("rname")
        Log.d("방 지역 확인2", ""+myroom_rname)*/

        var radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        makingquiz_btn.setOnClickListener{
            var radio_answer = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            if(radio_answer == null){
                Toast.makeText(this, "정답을 체크해주세요", Toast.LENGTH_LONG).show()
            } else {
                if(makingquiz_inputquestion.text.toString() == "" || makingquiz_inputans1.text.toString() == "" ||
                    makingquiz_inputans2.text.toString() == "" || makingquiz_inputans3.text.toString() == "" ||
                    makingquiz_inputans4.text.toString() == "" || makingquiz_inputans5.text.toString() == "" ||
                    makingquiz_inputhint.text.toString() == "" || makingquiz_inputcityname.text.toString() == "" ||
                    makingquiz_inputdname.text.toString() == "" || makingquiz_inputcontent.text.toString() == ""  ){
                    Toast.makeText(this, "모든 항목을 입력해주세요", Toast.LENGTH_LONG).show()
                } else{
                    Asynctask().execute("0", getString(R.string.create_quiz), "경복궁", makingquiz_inputdname.text.toString(),
                        makingquiz_inputquestion.text.toString(), makingquiz_inputans1.text.toString(), makingquiz_inputans2.text.toString(),
                        makingquiz_inputans3.text.toString(), makingquiz_inputans4.text.toString(), makingquiz_inputans5.text.toString(),
                        makingquiz_inputhint.text.toString(), makingquiz_inputcityname.text.toString(), radio_answer.text.toString(),
                        myroom_codenum, makingquiz_inputcontent.text.toString())
                }
            }

        }

    }

    inner class Asynctask: AsyncTask<String, Void, String>() {
        var state : Int = -1 // POST_createQ = 0,  POST_regionQuizList =1
        var response: String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])
            var client = OkHttpClient()
            var url = params[1]

            if(state == 0){
                Log.d("0 param[1] 확인", ""+params[1])
                Log.d("0 param[2] 확인", ""+params[2])
                var rname = params[2]
                var dname = params[3]
                var qname = params[4]
                var qcontent1 = params[5]
                var qcontent2 = params[6]
                var qcontent3 = params[7]
                var qcontent4 = params[8]
                var qcontent5 = params[9]
                var hcontent = params[10]
                var cityname = params[11]
                var answer = params[12].toInt()
                var roomcodenum = params[13]
                var solution = params[14]
                response = Okhttp(applicationContext).POST(client, url,
                    CreateJson().json_createquiz(rname, dname, qname, qcontent1, qcontent2, qcontent3,
                        qcontent4, qcontent5, hcontent, cityname, answer, roomcodenum, solution))

            } else if( state == 1){
                Log.d("1 param[1] 확인", ""+params[1])
                Log.d("1 param[2] 확인", ""+params[2])
                var rname = params[2]
                response = Okhttp(applicationContext).POST(client, url, CreateJson().json_regionquizlist(rname))

            }

            Log.d("response>>", ""+response)
            return response
        }

        override fun onPostExecute(result: String) {
            Log.d("network2",result)

            if(!result[0].equals('{')) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext, "네트워크 연결이 좋지 않습니다", Toast.LENGTH_SHORT).show()
                return
            }else {
                var json = JSONObject(result)

                if (state == 0) { //GET_idcheck
                    if (json.getInt("message").equals(1)) {
                        Toast.makeText(applicationContext, "퀴즈가 생성되었습니다.", Toast.LENGTH_SHORT).show()
                        var intent2 = Intent(applicationContext, MyRoomActivity::class.java)
                        intent2.putExtra("codenum", myroom_codenum)
                        startActivity(intent2)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "퀴즈 생성에 실패했습니다.\n다시 시도해주십시오.", Toast.LENGTH_SHORT).show()
                    }
                } else if (state == 1) {
                    if(json.getInt("message") == 1){
                        var jsonArray = json.getJSONArray("data")
                        Log.d("MakingQuiz jsonArray >>", ""+jsonArray)
                        for(i in 0 until jsonArray.length()){
                            Log.d("obj", ""+jsonArray.get(i))
                            var jsonObject:JSONObject = jsonArray.getJSONObject(i)
                            dnameList.add(jsonObject.getString("dname").toString())
                            //Log.d("dnameList 확인", ""+dnameList[i])
                        }
                        Log.d("dnameList 확인", ""+dnameList)
                    } else{ //state=0, message = 0
                        Log.d("Fail 0", " 0 네트워크 연결이 좋지 않음")
                        Toast.makeText(applicationContext, "네트워크 연결이 좋지 않습니다.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}