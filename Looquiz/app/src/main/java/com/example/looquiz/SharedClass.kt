package com.example.looquiz

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

//sharedPreferences를 관리하는  class / sharedPreferences는 context를 관리하기때문에 파라미터로 context를 담는다.

class SharedClass(context:Context){

    //디렉토리
    var sharedPreferences = context.getSharedPreferences("token",MODE_PRIVATE)
    //추가 변수
    var editPreferences = sharedPreferences.edit()

    //token이라는 이름의 새로생성
    fun setToken(token : String){
        editPreferences.putString("token",token).apply()
    }

    fun setAuto(auto : Boolean){ editPreferences.putBoolean("auto",auto).apply()}
    //새로 생성한 token 가져옴
    fun getToken():String?{
        return sharedPreferences.getString("token",null)
    }

    fun getAuto() : Boolean{ return sharedPreferences.getBoolean("auto",false)}

    fun setCodenum(codenum:String?){
        editPreferences.putString("codenum",codenum).apply()
    }
    fun getCodenum():String?{
        return sharedPreferences.getString("codenum",null)
    }
}


