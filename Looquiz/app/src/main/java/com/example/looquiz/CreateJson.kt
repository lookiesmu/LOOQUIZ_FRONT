package com.example.looquiz

import org.json.JSONObject

class CreateJson {

    fun json_signin(uid: String?,pw: String?):String?{

        var JSONObject = JSONObject()
        JSONObject.put("uid",uid)
        JSONObject.put("pw",pw)

        return JSONObject.toString()
    }

    fun json_signup(uid: String?,pw:String?,uname:String?,email:String?): String? {

        var JSONObject = JSONObject()
        JSONObject.put("uid", uid)
        JSONObject.put("pw", pw)
        JSONObject.put("uname", uname)
        JSONObject.put("email", email)

        return JSONObject.toString()
    }
}
