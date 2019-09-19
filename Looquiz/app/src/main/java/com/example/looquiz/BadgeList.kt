package com.example.looquiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_badge.*
import kotlinx.android.synthetic.main.activity_region.*

class BadgeList : AppCompatActivity() {

    class BadgeItem (private var region: String, private var image: Int) {
        fun getRegion(): String { return region }
        fun getImage(): Int {return image}
    }

    class BadgeAdapter(private var c: Context, private var badgeItem: ArrayList<BadgeItem>) : BaseAdapter() {
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

    private lateinit var  adapter: BadgeAdapter
    private lateinit var gv: GridView

    private val data: ArrayList<BadgeItem>
        get() {
            val badgeItems = ArrayList<BadgeItem>()

            var badgeItem = BadgeItem("Seoul", R.mipmap.ic_launcher)
            badgeItems.add(badgeItem)

            badgeItem = BadgeItem("Incheon", R.mipmap.ic_launcher)
            badgeItems.add(badgeItem)

            badgeItem = BadgeItem("Incheon", R.mipmap.ic_launcher)
            badgeItems.add(badgeItem)

            badgeItem = BadgeItem("Incheon", R.mipmap.ic_launcher)
            badgeItems.add(badgeItem)

            badgeItem = BadgeItem("Incheon", R.mipmap.ic_launcher)
            badgeItems.add(badgeItem)

            badgeItem = BadgeItem("Incheon", R.mipmap.ic_launcher)
            badgeItems.add(badgeItem)

            badgeItem = BadgeItem("Incheon", R.mipmap.ic_launcher)
            badgeItems.add(badgeItem)

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
}