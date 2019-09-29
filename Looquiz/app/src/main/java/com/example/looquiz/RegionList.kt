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
import java.util.ArrayList

class RegionList: AppCompatActivity(), BigRegionAdapter.ClickListener {
    override fun onItemClicked(item: String) {
        Toast.makeText(this, "Item clicked $item", Toast.LENGTH_SHORT).show()

        when (item){
            "수도권" -> grid_view.adapter = RegionBaseAdapter(this,listOf("서울", "인천", "수원", "시흥", "양평", "파주", "평택"))
            "충청도" -> grid_view.adapter = RegionBaseAdapter(this,listOf("청주", "충주", "제천", "천안", "공주", "논산", "당진", "서산"))
            "전라도" -> grid_view.adapter = RegionBaseAdapter(this,listOf("전주", "군산", "남원", "익산", "광주", "여수", "순천", "목포"))
            "경상도" -> grid_view.adapter = RegionBaseAdapter(this,listOf("대구", "경주", "안동", "상주", "포항", "부산", "진주", "통영"))
            "강원도" -> grid_view.adapter = RegionBaseAdapter(this,listOf("춘천", "원주", "속초", "강릉", "동해", "태백"))
            "제주도" -> grid_view.adapter = RegionBaseAdapter(this,listOf("한라산", "서귀포", "우도"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region)

        val adapter = RegionBaseAdapter(this,listOf("서울", "인천", "수원", "시흥", "양평", "파주", "평택"))

        // 도별 어댑터
        val posts = arrayListOf<String>("수도권","충청도", "전라도","경상도", "강원도","제주도")

        recyclerView.layoutManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        recyclerView.adapter = BigRegionAdapter(posts, this)


        grid_view.adapter = adapter

        // Configure the grid view
        grid_view.numColumns = 3
        grid_view.horizontalSpacing = 15
        grid_view.verticalSpacing = 15
        grid_view.stretchMode = GridView.STRETCH_COLUMN_WIDTH
    }
}