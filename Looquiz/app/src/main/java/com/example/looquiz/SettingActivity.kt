package com.example.looquiz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.act_main.*
import kotlinx.android.synthetic.main.app_bar_setting.*
import kotlinx.android.synthetic.main.content_setting.*

class SettingActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    var settingData = arrayOf("공지사항", "회원정보수정", "이용약관", "라이센스")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_setting)
        setSupportActionBar(toolbar)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, settingData)
        setting_listview.adapter = adapter

        var clickListener = ListListener()
        setting_listview.setOnItemClickListener(clickListener)
    }

    inner class ListListener: AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position){
                0 -> { //공지사항

                }
                1 -> { //회원정보수정

                    startActivity(Intent(applicationContext, EditPWActivity::class.java))
                }
                2 -> { //이용약관

                }
                3 -> { //라이센스

                }
            }
        }
    }
    //드로어
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_mypage -> {
                startActivity(Intent(this, MyPageActivity::class.java))
            }
            R.id.nav_all -> {
                startActivity(Intent(this, MakingQuizActivity::class.java))
            }
            R.id.nav_room -> {
                startActivity(Intent(this, RoomChoose::class.java))
            }
            R.id.nav_setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
