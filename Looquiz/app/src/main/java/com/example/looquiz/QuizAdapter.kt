package com.example.looquiz

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

class QuizAdapter(val context : Context, val cityList:ArrayList<QuizRate>):

    RecyclerView.Adapter<QuizAdapter.Holder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_quizrate, p0, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        p0?.bind(cityList[p1],context)
    }

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!){
        val CityImage=itemView?.findViewById<ImageView>(R.id.quizrate_item_iv)
        val CityName = itemView?.findViewById<TextView>(R.id.quizrate_item_tv)
        val CityRate = itemView?.findViewById<ProgressBar>(R.id.quizrate_item_pb)

        fun bind(quizrate:QuizRate,context: Context){

            if(quizrate.image != ""){
                val resourceId=context.resources.getIdentifier(quizrate.image,"drawable",context.packageName)
                CityImage?.setImageResource(resourceId)
            }else{
                CityImage?.setImageResource(R.mipmap.ic_launcher)
            }
            CityName?.text = quizrate.name
            CityRate!!.progress = quizrate.rate!!

        }
    }
}