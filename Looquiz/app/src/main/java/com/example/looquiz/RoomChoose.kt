package com.example.looquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.room_choose.*

class RoomChoose : AppCompatActivity() {

    var frag_list = ArrayList<ListOfRoom>()
    var title_list = ArrayList<String>()

    var list_fragment = ListOfRoom()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_choose)

/*        var tran = supportFragmentManager.beginTransaction()
        tran.replace(R.id.container, list_fragment)
        tran.commit()*/

        supportActionBar?.hide()

        for(i in 0..1){
            //var sub = SubFragment()
            var listF = ListOfRoom()
//            listF.str1 = "Sub ${i + 1}"

            frag_list.add(listF)
            title_list.add("내가 만든 방")
            title_list.add("내가 참여한 방")
        }

        pager.adapter = PagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(pager)


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
