package com.example.looquiz

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import okhttp3.OkHttpClient
import org.json.JSONObject


class QuizlistAdapter(val context: Context,val codenumber:String,val quizlist:ArrayList<Quizlist>)
    :RecyclerView.Adapter<QuizlistAdapter.Holder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.quizlist_item,p0,false)
        Asynctask().execute("0",context.getString(R.string.room_quizlist))
        return Holder(view)
    }

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        p0?.bind(quizlist[p1],context)
    }

    override fun getItemCount(): Int = quizlist.size

    inner class Holder(itemView: View):RecyclerView.ViewHolder(itemView!!){
        val Dname = itemView.findViewById<TextView>(R.id.quizlist_dname)
        val Qname = itemView.findViewById<TextView>(R.id.quizlist_qname)
        val Quizdel = itemView.findViewById<ImageView>(R.id.quizlist_delete)
        val Quizview = itemView.findViewById<LinearLayout>(R.id.quizlist_view)

        fun bind(quiz: Quizlist, context: Context) {
            Dname.text = quiz.dname
            Qname.text = quiz.qname
            Quizview.setOnClickListener{
            }
            Quizdel.setOnClickListener {
                Asynctask().execute("1",context.getString(R.string.delete_quiz),quiz.qid.toString() )
                quizlist.remove(quiz)
                notifyDataSetChanged()
            }
        }
    }
    inner class Asynctask: AsyncTask<String, Void, String>() {
        var response: String? = null
        var state:Int = -1//0 = POST_quizlist, 1 = Del_quiz
        override fun doInBackground(vararg params: String): String? {
            var client = OkHttpClient()
            state = params[0].toInt()
            var url = params[1]


            if(state == 0){
                response = Okhttp(context).POST(client,url,CreateJson().json_roomquizlist(codenumber))
            }
            else {
                url = url + params[2]

                response = Okhttp(context).DELETE(client, url)
            }
            return response
        }

        override fun onPostExecute(result: String) {

            if (!result[0].equals('{')) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                return
            } else {
                var json = JSONObject(result)
                var jsonary = json.getJSONArray("data")
                var message = json.getInt("message")
                for(i in 0 until jsonary.length()){
                    var jsonquiz = jsonary[0] as JSONObject
                    var qid : Int = jsonquiz.getInt("qid")
                    var dname : String = jsonquiz.getString("dname")
                    var qname : String = jsonquiz.getString("qname")
                    quizlist.add(Quizlist(qname,dname,qid))
                }
            }
        }
    }
}