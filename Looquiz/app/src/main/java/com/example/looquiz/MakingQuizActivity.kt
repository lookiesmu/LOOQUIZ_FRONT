package com.example.looquiz

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_making_quiz)

        var radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

/*
        var rname = 유적지 이름 - 경복궁
        var dname = 세부 장소 - 광화문
        var qname = 퀴즈 질문
        var qcontent1 = 1
        var qcontent2 = 2
        var qcontent3 = 3
        var qcontent4 = 4
        var qcontent5 = 5
        var hcontent = 힌트
        var cityname = 도시이름 - 서울
        var answer = 정답 번호 - 3
        var solution = 해설
  */
        makingquiz_btn.setOnClickListener{
            var radioBtn = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            //Log.d("radioBtn>> ", ""+radioBtn.text.toString().toInt())
            Asynctask().execute("0", getString(R.string.create_quiz), "경복궁", "광화문",
                makingquiz_inputquestion.text.toString(), makingquiz_inputans1.text.toString(), makingquiz_inputans2.text.toString(),
                makingquiz_inputans3.text.toString(), makingquiz_inputans4.text.toString(), makingquiz_inputans5.text.toString(),
                makingquiz_inputhint.text.toString(), "서울", "3", makingquiz_inputcontent.text.toString()
                )

            //Asynctask().execute("2", getString(R.string.get_badge), "경복궁")
        }
/*
        //지역선택 spinner
        input_region.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
*/
    }

    inner class Asynctask: AsyncTask<String, Void, String>() {
        var state : Int = -1 // POST_createQ = 0 DELETE_delete =1
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
                var solution = params[13]
                response = Okhttp(applicationContext).POST(client, url,
                    CreateJson().json_createquiz(rname, dname, qname, qcontent1, qcontent2, qcontent3,
                        qcontent4, qcontent5, hcontent, cityname, answer, solution))

            } else if( state == 1){
                Log.d("1 param[1] 확인", ""+params[1])
                Log.d("1 param[2] 확인", ""+params[2])
                var qid = params[2].toInt()
                url = url +"${qid}"
                Log.d("deleteQ url check>> ", ""+url)
                response = Okhttp(applicationContext).DELETE(client, url)

            } else if( state ==2){
                Log.d("2 param[2] 확인", ""+params[2])
                var rname = params[2]
                response = Okhttp(applicationContext).POST(client, url, CreateJson().json_getbadge(rname))
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
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "퀴즈 생성에 실패했습니다.\n다시 시도해주십시오.", Toast.LENGTH_SHORT).show()
                    }
                } else if (state == 1) {
                    if (json.getInt("message").equals(1)) {
                        Toast.makeText(applicationContext, "퀴즈가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "퀴즈 삭제를 실패했습니다.\n다시 시도해주십시오.", Toast.LENGTH_SHORT).show()
                    }
                } else if(state == 2){
                    if (json.getInt("message").equals(1)) {
                        Toast.makeText(applicationContext, "뱃지가 생성되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "다시 시도해주십시오.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
