package com.example.looquiz

import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.act_edit_pw.*
import kotlinx.android.synthetic.main.dia_checkpw.*
import okhttp3.OkHttpClient
import org.json.JSONObject

class EditPWActivity : AppCompatActivity() {

    var pw:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_edit_pw)


        btn_editPW.setOnClickListener {
            if (editpw_inputpw.text.toString() == editpw_inputpw2.text.toString()){
                Asynctask().execute("1",getString(R.string.edit_pw)
                    ,editpw_inputpw.text.toString(),editpw_inputpw2.text.toString())
                Toast.makeText(applicationContext," 비밀번호가 수정되었습니다 ",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext," 비밀번호가 일치하지 않습니다 ",Toast.LENGTH_SHORT).show()
            }
        }
        btn_secession.setOnClickListener {
            Asynctask().execute("2",getString(R.string.delete_mem),pw)
            Toast.makeText(applicationContext," 회원 탈퇴 되었습니다 ",Toast.LENGTH_SHORT).show()
        }
        var checkPWBuilder = AlertDialog.Builder(this)
        var checkPWBuilder2:AlertDialog = checkPWBuilder.create()
        var checkPWView = layoutInflater.inflate(R.layout.dia_checkpw, null)
        checkPWBuilder.setView(checkPWView)

        var checkPWListener = object :DialogInterface.OnClickListener{

            override fun onClick(dialog: DialogInterface?, which: Int) {
                Asynctask().execute("0",getString(R.string.checkpw),checkpw_inputpw.text.toString())
            }
        }
        checkPWBuilder.setPositiveButton("확인", checkPWListener)
        checkPWBuilder.setNegativeButton("닫기", null)

        checkPWBuilder.show()
    }
    inner class Asynctask: AsyncTask<String, Void, String>() {
        var state = -1 //POST_ckeckPW = 0 , PUT_editPW = 1 ,POST_deletemem = 2
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])
            var client = OkHttpClient()
            var url = params[1]
            pw = params[2]
            if(state == 0){

                response = Okhttp(applicationContext).POST(client,url,CreateJson().json_checkpw(pw))

            }
            else if(state == 1) {
                var upw = params[3]
                response = Okhttp(applicationContext).PUT(client, url, CreateJson().json_editpw(pw, upw))
            }

            else if(state == 2) {
                response = Okhttp(applicationContext).PUT(client, url, CreateJson().json_secession(pw))
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
