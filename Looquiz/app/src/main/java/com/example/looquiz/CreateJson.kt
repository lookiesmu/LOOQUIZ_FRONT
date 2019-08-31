package com.example.looquiz

import org.json.JSONObject

class CreateJson {

    fun json_checkpw(pw: String?):String?{

        var JSONObject = JSONObject()
        JSONObject.put("pw",pw)

        return JSONObject.toString()
    }
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
                        ,qcontent4:String?,qcontent5:String?,hcontent:String?,cityname:String?,answer:String?,solution:String?):String?{

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
        JSONObject.put("cityname",cityname)
        JSONObject.put("answer",answer)
        JSONObject.put("solution",solution)

        return JSONObject.toString()
    }
    fun json_editquiz(rname:String?,dname:String?,qid:Int?,cityname:String?,qname:String?,qcontent1:String?,qcontent2:String?,qcontent3:String?
                        ,qcontent4:String?,qcontent5:String?,hcontent:String?,answer:String?,solution:String?):String?{

        var JSONObject = JSONObject()
        JSONObject.put("rname",rname)
        JSONObject.put("dname",dname)
        JSONObject.put("qid",qid)
        JSONObject.put("cityname",cityname)
        JSONObject.put("qname",qname)
        JSONObject.put("qcontent1",qcontent1)
        JSONObject.put("qcontent2",qcontent2)
        JSONObject.put("qcontent3",qcontent3)
        JSONObject.put("qcontent4",qcontent4)
        JSONObject.put("qcontent5",qcontent5)
        JSONObject.put("hcontent",hcontent)
        JSONObject.put("answer",answer)
        JSONObject.put("solution",solution)

        return JSONObject.toString()
    }
    fun json_searchmem(codenum:String?):String{

        var JSONObject = JSONObject()
        JSONObject.put("codenum",codenum)

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

    fun json_makeroom(qrname:String?,endtime:String?):String{

        var JSONObject = JSONObject()
        JSONObject.put("qrname",qrname)
        JSONObject.put("endtime",endtime)

        return JSONObject.toString()
    }
    fun json_roomquizlist(codenum: String?):String{

        var JSONObject = JSONObject()
        JSONObject.put("codenum",codenum)

        return JSONObject.toString()
    }

    fun json_beforequiz(qid: Int?):String{

        var JSONObject = JSONObject()
        JSONObject.put("qid",qid)

        return JSONObject.toString()
    }
    fun json_afterquiz(rname:String?,result:Int?):String{

        var JSONObject = JSONObject()
        JSONObject.put("rname",rname)
        JSONObject.put("result",result)

        return JSONObject.toString()
    }
    fun json_getbadge(rname:String?):String{

        var JSONObject = JSONObject()
        JSONObject.put("rname",rname)

        return JSONObject.toString()
    }





}
