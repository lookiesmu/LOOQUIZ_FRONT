package com.example.looquiz

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.widget.GridView
import android.widget.RelativeLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_region.*

class RegionList: AppCompatActivity(), BigRegionAdapter.ClickListener {
    override fun onItemClicked(item: String) {
        Toast.makeText(this, "Item clicked $item", Toast.LENGTH_SHORT).show()

        when (item){
            "수도권" -> grid_view.adapter = RegionBaseAdapter()
            "충북" -> grid_view.adapter = RegionBaseAdapter2()
            "충남" -> grid_view.adapter = RegionBaseAdapter3()
            "전북" -> grid_view.adapter = RegionBaseAdapter4()
            "전남" -> grid_view.adapter = RegionBaseAdapter5()
            "경북" -> grid_view.adapter = RegionBaseAdapter6()
            "경남" -> grid_view.adapter = RegionBaseAdapter7()
            "강원" -> grid_view.adapter = RegionBaseAdapter8()
            "제주" -> grid_view.adapter = RegionBaseAdapter9()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region)

/*        buttonTest3.setOnClickListener{
            val intent = Intent(this, BadgeList::class.java)
            startActivity(intent)
        }*/


        // Get an instance of base adapter
        val adapter = RegionBaseAdapter()
        val adapter2 = RegionBaseAdapter2()

        // 도별 어댑터
        val posts: ArrayList<String> = ArrayList()

        posts.add("수도권")
        posts.add("충북")
        posts.add("충남")
        posts.add("전북")
        posts.add("전남")
        posts.add("경북")
        posts.add("경남")
        posts.add("강원")
        posts.add("제주")

        recyclerView.layoutManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        recyclerView.adapter = BigRegionAdapter(posts, this)


        // Set the grid view adapter
        grid_view.adapter = adapter

        // Configure the grid view
        grid_view.numColumns = 3
        grid_view.horizontalSpacing = 15
        grid_view.verticalSpacing = 15
        grid_view.stretchMode = GridView.STRETCH_COLUMN_WIDTH
    }
}