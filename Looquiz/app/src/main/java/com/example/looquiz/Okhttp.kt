package com.example.looquiz

import okhttp3.*
import java.io.IOException

class Okhttp{

    fun POST(client: OkHttpClient?, url: String?, jsonbody:String?):String?{
        var response: Response

        try {
            var request= Request.Builder()
                .url(url!!)
                .post(RequestBody.create(MediaType.parse("application/json"), jsonbody!!))
                .build()
            response = client!!.newCall(request).execute()

            return response.body()?.string()!!

        }catch (e: IOException){
            return e.toString()
        }
        return null
    }

    fun GET(clinet: OkHttpClient, url:String):String?{
        var response: Response

        try {
            var request= Request.Builder()
                .url(url!!)
                .get()
                .build()
            response = clinet!!.newCall(request).execute()

            return response.body()?.string()!!

        }catch (e: IOException){
            return e.toString()
        }
        return null
    }

    fun DELETE(client: OkHttpClient?, url: String?):String?{
        var response: Response

        try {
            var request= Request.Builder()
                .url(url!!)
                .build()
            response = client!!.newCall(request).execute()
            return response.body()?.string()!!
        }catch (e: IOException){
            return e.toString()
        }
        return null
    }

    fun PUT(client: OkHttpClient?, url:String?, jsonbody: String?):String?{
        var response : Response
        try {
            var request= Request.Builder()
                .url(url!!)
                .put(RequestBody.create(MediaType.parse("application/json"),jsonbody!!))
                .build()
            response=client!!.newCall(request).execute()
            return response.body()?.string()
        }catch(e: IOException){
            return e.toString()
        }
        return null
    }

}