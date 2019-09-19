package com.example.looquiz


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.row_bigregion.view.*

class BigRegionAdapter(val posts: ArrayList<String>, private val listener: ClickListener) : RecyclerView.Adapter<BigRegionAdapter.ViewHolder>() {

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.firstName.text = posts[position]

        val item = posts.get(position)
        holder.bind(item,listener)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.row_bigregion, p0, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView){
        var firstName: TextView = itemView.findViewById(R.id.firstName)

        val tv_view = itemView.firstName
        fun bind(item:String, listener: BigRegionAdapter.ClickListener){
            tv_view.text = item
            tv_view.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }

    interface ClickListener {
        fun onItemClicked(item: String)
    }

}