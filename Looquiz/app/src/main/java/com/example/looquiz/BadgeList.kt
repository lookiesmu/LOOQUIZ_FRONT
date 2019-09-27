package com.example.looquiz

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_badge.*
import kotlinx.android.synthetic.main.activity_region.*
import okhttp3.OkHttpClient
import org.json.JSONObject

class BadgeList : AppCompatActivity() {

    var cityimg = mapOf<String,Int>(Pair("경복궁",R.drawable.p1), Pair("창덕궁",R.drawable.p2), Pair("덕수궁",R.drawable.p3), Pair("불국사",R.drawable.p4), Pair("화엄사",R.drawable.p5), Pair("한라산",R.drawable.p6), Pair("북한산",R.drawable.p7), Pair("다보탑",R.drawable.p8), Pair("석가탑",R.drawable.p9), Pair("석굴암",R.drawable.p10))

    class BadgeItem (private var region: String, private var image: Int) {
        fun getRegion(): String { return region }
        fun getImage(): Int {return image}
    }

    class BadgeAdapter( var c: Context,  var badgeItem: ArrayList<BadgeItem>) : BaseAdapter() {
        override fun getCount(): Int { return badgeItem.size }

        override fun getItem(position: Int): Any { return badgeItem[position] }

        override fun getItemId(position: Int): Long { return position.toLong() }

        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            var view = view
            if (view == null) {
                view = LayoutInflater.from(c).inflate(R.layout.badge_item, viewGroup, false)
            }

            val badgeItem = this.getItem(position) as BadgeItem
            val img = view!!.findViewById<ImageView>(R.id.gridImg) as ImageView
            val regionTxt = view.findViewById<TextView>(R.id.regionTxt)

            regionTxt.text = badgeItem.getRegion()
            img.setImageResource(badgeItem.getImage())

            view.setOnClickListener { Toast.makeText(c, badgeItem.getRegion(), Toast.LENGTH_SHORT).show() }

            return view
        }
    }

    protected lateinit var  adapter: BadgeAdapter
    private lateinit var gv: GridView

     val data: ArrayList<BadgeItem>
        get() {
            val badgeItems = ArrayList<BadgeItem>()
            return badgeItems
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badge)

        gv = findViewById(R.id.myGridView) as GridView

        adapter = BadgeAdapter(this, data)

        var numcolVar: Int
        if (data.size<4){
            numcolVar = data.size
        } else {
            numcolVar = 4
        }
        gv.adapter = adapter
        gv.numColumns = numcolVar
    }
    inner class Asynctask: AsyncTask<String, Void, String>() {
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            var client = OkHttpClient()
            var url = params[0]

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
                        adapter.badgeItem.add(BadgeItem(rname, cityimg.get(rname)!!))
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}