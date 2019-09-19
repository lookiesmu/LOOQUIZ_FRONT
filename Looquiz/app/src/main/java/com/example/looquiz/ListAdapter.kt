package com.example.looquiz

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import okhttp3.OkHttpClient
import org.json.JSONObject

class ListAdapter(val context: Context,val list: ArrayList<roomlist_dataclass>)
    : RecyclerView.Adapter<ListAdapter.Holder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item,p0,false)
        return Holder(view)
    }

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        p0?.bind(list[p1],context)
    }

    override fun getItemCount(): Int = list.size

    inner class Holder(itemView: View):RecyclerView.ViewHolder(itemView!!){

        val roomTitle = itemView.findViewById<TextView>(R.id.roomlist_title)
        val roomCodenum = itemView.findViewById<TextView>(R.id.roomlist_codenum)
        val roomout = itemView.findViewById<ImageView>(R.id.roomlist_outroom)
        val roomlist_view = itemView.findViewById<LinearLayout>(R.id.roomlist_view)

        fun bind(room: roomlist_dataclass, context: Context) {
            roomTitle.text = room.roomtitle
            roomCodenum.text = room.roomcodenum
            roomlist_view.setOnClickListener{
                var intent = Intent(context,MyRoomActivity::class.java)

                intent.putExtra("codenum",room.roomcodenum)
                intent.putExtra("title",room.roomtitle)

            }
            roomout.setOnClickListener {

                Asynctask().execute(context.getString(R.string.delete_room), room.roomcodenum)
                list.remove(room)
                notifyDataSetChanged()
            }
        }
    }

    fun add(room: roomlist_dataclass){
        list.add(room)
        notifyDataSetChanged()
    }

    inner class Asynctask: AsyncTask<String, Void, String>() {
        var response: String? = null

        override fun doInBackground(vararg params: String): String? {
            var client = OkHttpClient()
            var url = params[0]
            var codenum = params[1]

            url = url + "${codenum}"

            response = Okhttp(context).DELETE(client,url)

            return response
        }

        override fun onPostExecute(result: String) {

            if (!result[0].equals('{')) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                return
            } else {
                Toast.makeText(context, "방 나가기", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
