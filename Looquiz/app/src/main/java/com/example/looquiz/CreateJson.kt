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
    fun json_editpw(pw:String?,upw:String?):String?{

        var JSONObject=JSONObject()
        JSONObject.put("pw",pw)
        JSONObject.put("upw",upw)

        return JSONObject.toString()
    }

    fun json_secession(pw:String?):String{

        var JSONObject = JSONObject()
        JSONObject.put("pw",pw)

        return JSONObject.toString()
    }

    fun json_createquiz(rname:String?,dname:String?,qname:String?,qcontent1:String?,qcontent2:String?,qcontent3:String?
                        ,qcontent4:String?,qcontent5:String?,hcontent:String?,solution:String?):String?{

        var JSONObject = JSONObject()
        JSONObject.put("rname",rname)
        JSONObject.put("dname",dname)
        JSONObject.put("qname",qname)
        JSONObject.put("qcontent1",qcontent1)
        JSONObject.put("qcontent2",qcontent2)
        JSONObject.put("qcontent3",qcontent3)
        JSONObject.put("qcontent4",qcontent4)
        JSONObject.put("qcontent5",qcontent5)
        JSONObject.put("hcontent",hcontent)
        JSONObject.put("solution",solution)

        return JSONObject.toString()
    }
    fun json_editquiz(rname:String?,qname:String?,qcontent1:String?,qcontent2:String?,qcontent3:String?
                        ,qcontent4:String?,qcontent5:String?,hcontent:String?,solution:String?):String?{

        var JSONObject = JSONObject()
        JSONObject.put("rname",rname)
        JSONObject.put("qname",qname)
        JSONObject.put("qcontent1",qcontent1)
        JSONObject.put("qcontent2",qcontent2)
        JSONObject.put("qcontent3",qcontent3)
        JSONObject.put("qcontent4",qcontent4)
        JSONObject.put("qcontent5",qcontent5)
        JSONObject.put("hcontent",hcontent)
        JSONObject.put("solution",solution)

        return JSONObject.toString()
    }


    fun json_enterroom(codenum:String?):String{

        var JSONObject = JSONObject()
        JSONObject.put("codenum",codenum)

        return JSONObject.toString()
    }


    fun json_regionquizlist(rname:String?):String{

        var JSONObject = JSONObject()
        JSONObject.put("rname",rname)

        return JSONObject.toString()
    }


}
