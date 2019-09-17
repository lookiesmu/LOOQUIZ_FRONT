package com.example.looquiz

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.Toast
import kotlinx.android.synthetic.main.act_editing_quiz.*
import kotlinx.android.synthetic.main.act_making_quiz.*
import okhttp3.OkHttpClient
import org.json.JSONObject

class EditingQuizActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_editing_quiz)

        editingquiz_btn.setOnClickListener{
            Asynctask().execute("0", getString(R.string.edit_quiz), "경복궁", "광화문", "4", "서울",
                editingquiz_inputquestion.text.toString(), editingquiz_inputans1.text.toString(), editingquiz_inputans2.text.toString(),
                editingquiz_inputans3.text.toString(), editingquiz_inputans4.text.toString(), editingquiz_inputans5.text.toString(),
                editingquiz_inputhint.text.toString(), "5", editingquiz_inputcontent.text.toString()
            )
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
        var state : Int = -1 // POST_createQ = 0
        var response: String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])
            var client = OkHttpClient()
            var url = params[1]

            if(state == 0){
                Log.d("param[1] 확인", ""+params[1])

                var rname = params[2]
                var dname = params[3]
                var qid = params[4].toInt()
                var cityname = params[5]
                var qname = params[6]
                var qcontent1 = params[7]
                var qcontent2 = params[8]
                var qcontent3 = params[9]
                var qcontent4 = params[10]
                var qcontent5 = params[11]
                var hcontent = params[12]
                var answer = params[13].toInt()
                var solution = params[14]

                response = Okhttp(applicationContext).PUT(client, url,
                    CreateJson().json_editquiz(rname, dname, qid, cityname, qname, qcontent1, qcontent2, qcontent3,
                        qcontent4, qcontent5, hcontent, answer, solution))

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
                if (json.getInt("message").equals(1)){
                    Toast.makeText(applicationContext,"퀴즈가 수정되었습니다.", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(applicationContext,"퀴즈 수정에 실패했습니다.\n다시 시도해주십시오.", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

}
