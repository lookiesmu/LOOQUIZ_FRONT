package com.example.looquiz

import android.content.Context
import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class Okhttp(var context: Context){
    var SharedClass = SharedClass(context)

    fun POST(client: OkHttpClient?, url: String?, jsonbody:String?):String?{
        var response: Response

        var token = SharedClass.getToken()

        try {
            var builder= Request.Builder()
            builder
                .url(url!!)
                .post(RequestBody.create(MediaType.parse("application/json"), jsonbody!!))

            //header에 넣기
            if(!token.isNullOrEmpty()) {
                builder.header("Authorization", token)
            }

            var request = builder.build()
            response = client!!.newCall(request).execute()

            //header에 받기
            if (!response.header("Authorization").isNullOrEmpty()){
                SharedClass.setToken(response.header("Authorization").toString())
                Log.d("okhttp_tokentest","recieve token : ${response.header("Authorization").toString()}")
            }

            var str:String =response.body()!!.string()

            return str

        }catch (e: IOException){
            return e.toString()
        }
        return null
    }

    fun GET(clinet: OkHttpClient, url:String):String?{
        var response: Response
        var token = SharedClass.getToken()

        try {
            var builder= Request.Builder()
                .url(url!!)
                .get()
            //header에 넣기
            if(!token.isNullOrEmpty()) {
                Log.d("okhttp_tokentest","send token : ${token}")
                builder.header("Authorization", token)
            }

            var request = builder.build()
            response = clinet.newCall(request).execute()

            return response.body()?.string()!!
        }catch (e: IOException){
            return e.toString()
        }
        return null
    }


    fun DELETE(clinet: OkHttpClient, url:String):String?{
        var response: Response
        var token = SharedClass.getToken()

        try {
            var builder= Request.Builder()
                .url(url!!)
                .delete()
            //header에 넣기
            if(!token.isNullOrEmpty()) {
                Log.d("okhttp_tokentest","send token : ${token}")
                builder.header("Authorization", token)
            }

            var request = builder.build()
            response = clinet!!.newCall(request).execute()

            return response.body()?.string()!!
        }catch (e: IOException){
            return e.toString()
        }
        return null
    }

    fun PUT(client: OkHttpClient?, url: String?, jsonbody:String?):String?{
        var response: Response

        var token = SharedClass.getToken()

        try {
            var builder= Request.Builder()
            builder
                .url(url!!)
                .put(RequestBody.create(MediaType.parse("application/json"), jsonbody!!))

            //header에 넣기
            if(!token.isNullOrEmpty())
                builder.header("Authorization",token)

            var request = builder.build()
            response = client!!.newCall(request).execute()

            //header에 받기
            if (!response.header("Authorization").isNullOrEmpty()){
                SharedClass.setToken(response.header("Authorization").toString())
                Log.d("okhttp_tokentest","recieve token : ${response.header("Authorization").toString()}")
            }

            var str:String =response.body()!!.string()

            return str

        }catch (e: IOException){
            return e.toString()
        }
        return null
    }
}