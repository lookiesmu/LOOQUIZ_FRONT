
package com.example.looquiz

import android.content.DialogInterface
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.room_choose.*
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalTime
import kotlin.random.Random

class RoomChoose : AppCompatActivity() {
    //var roomlist_dataclass : roomlist_dataclass? = null
    var makeroom_list = ArrayList<roomlist_dataclass>()
    var joinroom_list = ArrayList<roomlist_dataclass>()

    var room = roomlist_dataclass("","")
    var frag_list = ArrayList<ListOfRoomFragment>()
    var title_list = ArrayList<String>()
    var myRoom_Fragment = ListOfRoomFragment()
    var joinRoom_Fragment = ListOfRoomFragment()


    var qrname: String? = null
    //var create_codenum :String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_choose)

        Asynctask().execute("0",getString(R.string.search_makeroom))
        //Asynctask().execute("1",getString(R.string.participate_room))


        supportActionBar?.hide()

        title_list.add("내가 만든 방")
        frag_list.add(myRoom_Fragment)
        title_list.add("내가 참여한 방")
        frag_list.add(joinRoom_Fragment)
        tabs.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(p0: TabLayout.Tab?) {
                if(p0!!.position == 1)
                    createroom_btn.visibility = View.INVISIBLE
                else
                    createroom_btn.visibility = View.VISIBLE
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
        })

        pager.adapter = PagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(pager)

        createroom_btn.setOnClickListener {
            //랜덤 스트링
            var codenum = CreateCodenum().execute()
            Log.d("create codenum",codenum)

            var adapter = myRoom_Fragment.getFragmentAdapter()
            var builder = AlertDialog.Builder(this)
            builder.setTitle(" 방 만들기 ")
            var v1 = layoutInflater.inflate(R.layout.dia_createroom,null)
            builder.setView(v1)
            builder.setPositiveButton(" 확인 " , null)
            var dialog = builder.create()

            dialog.setOnShowListener {
                val title = dialog.findViewById<EditText>(R.id.createroom_inputTitle)
                val date = dialog.findViewById<DatePicker>(R.id.createroom_spinnerdate)
                val text:TextView? = dialog.findViewById<TextView>(R.id.createroom_codenum)
                text!!.text = codenum
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                    if(title!!.text.isNullOrEmpty())
                        Toast.makeText(applicationContext,"방 이름을 입력해주세요",Toast.LENGTH_SHORT).show()
                    else{
                        room.roomtitle = title.text.toString()
                        room.roomcodenum = codenum
                        Asynctask().execute("2",getString(R.string.make_room),title.text.toString(),codenum
                            ,"${date!!.year}${date!!.month+1}${date!!.dayOfMonth}")
                        dialog.dismiss()
                    }
                }
            }

            dialog.show()


        }
    }
    inner class Asynctask: AsyncTask<String, Void, String>() {
        var response: String? = null
        var state:Int? = -1//0 = GET_makeroomlist, 1 = GET_joinroomlist, 2 = POST

        override fun doInBackground(vararg params: String): String? {
            var client = OkHttpClient()
            state = params[0].toInt()
            var url = params[1]

            if(state == 0){
                response = Okhttp(applicationContext).GET(client,url)
            }
            else if(state == 1){
                response=Okhttp(applicationContext).GET(client,url)
            }
            else{
                qrname = params[2]
                var codenum = params[3]
                var endtime = params[4]

                response = Okhttp(applicationContext).POST(client, url, CreateJson().json_makeroom(qrname,codenum, endtime))

                Log.d("checktest",CreateJson().json_makeroom(qrname,codenum, endtime))
            }
            return response
        }

        override fun onPostExecute(result: String) {

            Toast.makeText(applicationContext, "여기까지 실행됨", Toast.LENGTH_SHORT).show()
            Log.d("checktest", result)

            if (!result[0].equals('{')) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                return
            } else {
                var json = JSONObject(result)

                if(state == 0 || state == 1) {
                    if (json.getInt("message") == 1) {
                        var roomlist = ArrayList<roomlist_dataclass>()
                        var data = json.getJSONArray("data")
                        for (i in 0 until data.length()) {
                            var room: JSONObject = data[i] as JSONObject
                            roomlist.add(roomlist_dataclass(room.getString("qrname"), room.getString("codenum")))
                        }
                        if (state == 0)
                            myRoom_Fragment.adapter!!.listinit(roomlist)
                        else if (state == 1)
                            joinRoom_Fragment.adapter!!.listinit(roomlist)
                    }
                    else {
                        Toast.makeText(applicationContext, "일치하는 정보가 없습니다", Toast.LENGTH_SHORT).show()
                    }
                }
                else { // post state == 2
                    var adapter = myRoom_Fragment.adapter
                    var message = json.getInt("message")
                    if(message == 0)
                        Toast.makeText(applicationContext,"방 생성 실패",Toast.LENGTH_SHORT).show()
                    else if (message == 1){
                        adapter!!.add(room!!)
                    }
                    else if(message == 2)
                        Toast.makeText(applicationContext,"중복된 참가코드",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    inner class PagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm){

        override fun getCount(): Int {
            return frag_list.size
        }

        override fun getItem(position: Int): Fragment {
            return frag_list.get(position)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return title_list.get(position)
        }
    }
}

