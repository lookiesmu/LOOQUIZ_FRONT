package com.example.looquiz

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.act_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main2.*
import kotlinx.android.synthetic.main.dia_quiz.view.*
import okhttp3.OkHttpClient
import org.json.JSONObject

class Main2Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var gMap: GoogleMap? = null
    var locManager: LocationManager? = null
    //lateinit var desLng: LatLng
    lateinit var userLoc: Location
    var distance = 0.0

    //var quizList = arrayOf("정답임", "오답임", "오답", "옳지 않은 보기", "관련없는 보기")
    var quizList:Array<String>? = null

    lateinit var quest1: LatLng
    lateinit var quest2: LatLng
    lateinit var quest3: LatLng
    lateinit var quest4: LatLng
    lateinit var quest5: LatLng
    lateinit var quest6: LatLng
    lateinit var quest7: LatLng
    lateinit var quest8: LatLng
    lateinit var quest9: LatLng
    lateinit var quest10: LatLng

    var permission_list = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    lateinit var diaQuizView:View
    //var diaQuizView = layoutInflater.inflate(R.layout.dia_quiz, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main2)
        setSupportActionBar(toolbar)

        val intent = getIntent()
        region.text = intent.getStringExtra("regionName")
        var rName = intent.getStringExtra("regionName")

        Asynctask().execute("0", getString(R.string.region_quizlist), rName)

        quest1 = LatLng(37.575840, 126.977009) //광화문
        quest2 = LatLng(37.5834703,126.9752437) //신무문
        quest3 = LatLng(37.5760892,126.9793858) //동십자각
        quest4 = LatLng(37.5785225,126.9769845)//근정전
        quest5 = LatLng(37.5790885,126.977044)//사정전
        quest6 = LatLng(37.580314, 126.978075) //자경전
        quest7 = LatLng(37.5798388,126.975708)//경회루
        quest8 = LatLng(37.5825542,126.9741758) //태원전
        quest9 =  LatLng(37.583146, 126.977261) //건청궁
        quest10 = LatLng(37.5759947, 126.9746318)//국립고궁박물관


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 0)
        } else {
            init()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.popup_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_seoul -> {
                //showRegionList(item.title.toString())
                return true
            }
            R.id.action_gyeongsang -> { //경주
                //showRegionList(item.title.toString())
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    /*
        fun showRegionList(region: String){
            regionListBuilder = AlertDialog.Builder(this)
            regionListBuilder.setTitle(region+" 리스트")
            Asynctask().execute("0", getString(R.string.cityname), region)
        }
    */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_mypage -> {
                startActivity(Intent(this, MyPageActivity::class.java))
            }
            R.id.nav_all -> {
                startActivity(Intent(this, MakingQuizActivity::class.java))
            }
            R.id.nav_room -> {
                startActivity(Intent(this, RoomChoose::class.java))
            }
            R.id.nav_setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return
            }
        }
        init()
    }

    fun init() {
        var callback = MapReadyCallback()
        var mapFragment = supportFragmentManager.findFragmentById(R.id.regionmapview) as SupportMapFragment
        mapFragment.getMapAsync(callback)
    }

    inner class MapReadyCallback : OnMapReadyCallback {
        override fun onMapReady(p0: GoogleMap?) {
            gMap = p0
            getMyLocation()
            Log.d("현위치 >>", "userLoc ${userLoc}")

            gMap?.animateCamera(CameraUpdateFactory.zoomTo(15f))
            gMap?.moveCamera(CameraUpdateFactory.newLatLng(quest1))

            var marker1 = gMap?.addMarker(MarkerOptions().position(quest1))
            marker1?.title = "광화문"
            marker1?.tag = 1
            /*
            var map1: HashMap<MarkerOptions().position(quest1), Int> = hashMapOf(marker1 to 1)
            var map = HashMap<String, Int>()
            map.put("one", 1)
            map.put("four", 4)
            Log.d("map Log", ""+map.get("four"))
            */
            var marker2 = gMap?.addMarker(MarkerOptions().position(quest2))
            marker2?.title = "신무문"
            var marker3 = gMap?.addMarker(MarkerOptions().position(quest3))
            marker3?.title = "동십자각"
            var marker4 = gMap?.addMarker(MarkerOptions().position(quest4))
            marker4?.title = "근정전"
            var marker5 = gMap?.addMarker(MarkerOptions().position(quest5))
            marker5?.title = "사정전"
            var marker6 = gMap?.addMarker(MarkerOptions().position(quest6))
            marker6?.title = "자경전"
            var marker7 = gMap?.addMarker(MarkerOptions().position(quest7))
            marker7?.title = "경회루"
            var marker8 = gMap?.addMarker(MarkerOptions().position(quest8))
            marker8?.title = "태원전"
            var marker9 = gMap?.addMarker(MarkerOptions().position(quest9))
            marker9?.title = "건청궁"
            var marker10 = gMap?.addMarker(MarkerOptions().position(quest9))
            marker10?.title = "국립고궁박물관"

            gMap?.setOnInfoWindowClickListener {
                distance = getDistance(userLoc, it.position)
                Log.d("최종 거리계산 >> ", "${distance}km")
                //               if(distance<= 70) {
                when (it) {
                    marker1 -> {
                        Log.d("marker1 tag >> ", ""+marker1?.tag)
                        Log.d("marker1 tag >> ", ""+marker1?.tag.toString().toInt())
                        makeQuiz(marker1?.title, marker1?.tag.toString().toInt())
                    }
                    marker2 -> makeQuiz(marker2?.title, marker2?.tag.toString().toInt())
                    marker3 -> makeQuiz(marker3?.title, marker3?.tag.toString().toInt())
                    marker4 -> makeQuiz(marker4?.title, marker4?.tag.toString().toInt())
                    marker5 -> makeQuiz(marker5?.title, marker5?.tag.toString().toInt())
                    marker6 -> makeQuiz(marker6?.title, marker6?.tag.toString().toInt())
                    marker7 -> makeQuiz(marker7?.title, marker7?.tag.toString().toInt())
                    marker8 -> makeQuiz(marker8?.title, marker8?.tag.toString().toInt())
                    marker9 -> makeQuiz(marker9?.title, marker9?.tag.toString().toInt())
                    marker10 -> makeQuiz(marker10?.title, marker10?.tag.toString().toInt())
                }
//                }else Toast.makeText(applicationContext, "문제를 풀 수 없습니다.", Toast.LENGTH_LONG).show()
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                    return
                }
                if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                    return
                }
            }
            gMap?.isMyLocationEnabled = true
            gMap?.mapType = GoogleMap.MAP_TYPE_NORMAL //기본
        }
    }

    fun makeQuiz(regionName: String?, quizId: Int) {//fun makeQuiz(regionName: String?, quizId: Int)
        Asynctask().execute("1",getString(R.string.before_quiz), quizId.toString())

        var quizBuilder = AlertDialog.Builder(this@Main2Activity)
        diaQuizView = layoutInflater.inflate(R.layout.dia_quiz, null)
        //var diaQuizBtnView = layoutInflater.inflate(R.layout.dia_quiz_button, null)
        var quizBuilder2: AlertDialog = quizBuilder.create()

        var quizAnsBuilder = AlertDialog.Builder(this@Main2Activity)
        var quizAnsView = layoutInflater.inflate(R.layout.dia_ans, null)
        //var quizAnsBuilder2: AlertDialog = quizAnsBuilder.create()
        quizAnsBuilder.setView(quizAnsView)
        quizAnsBuilder.setPositiveButton("확인", null)

        var quizWAnsBuilder = AlertDialog.Builder(this@Main2Activity)
        var quizWAnsView = layoutInflater.inflate(R.layout.dia_wans, null)
        //var quizWAnsBuilder2: AlertDialog = quizWAnsBuilder.create()
        quizWAnsBuilder.setView(quizWAnsView)
        quizWAnsBuilder.setPositiveButton("닫기", null)

        var hintBuilder = AlertDialog.Builder(this@Main2Activity)
        var diaHintView = layoutInflater.inflate(R.layout.dia_hint, null)
        //var hintBuilder2: AlertDialog = hintBuilder.create()
        hintBuilder.setView(diaHintView)
        hintBuilder.setPositiveButton("확인", null)

        var btnHint: Button = diaQuizView.findViewById<Button>(R.id.btn_hint)
        btnHint.setOnClickListener {
            quizBuilder2.dismiss()
            hintBuilder.show()
        }

        var btnReport: Button = quizAnsView.findViewById(R.id.btn_report)
        btnReport.setOnClickListener {
            //오류 신고
        }
        var selectItem = -1

        var choiceListener = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                selectItem = which
            }
        }

        var diaListListener = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (selectItem == -1) {
                    Toast.makeText(this@Main2Activity, "항목을 선택해주세요", Toast.LENGTH_LONG).show()
                    quizBuilder2.dismiss()
                    makeQuiz(regionName, quizId)
                } else {
                    //Toast.makeText(this@Main2Activity, " ${selectItem}번째, ${quizList[selectItem]}", Toast.LENGTH_LONG).show()
                    quizBuilder2.dismiss()
                    //정답일 경우
                    quizAnsBuilder.show()

                    //오답일 경우
                    //quizWAnsBuilder.show()
                }
            }
        }

        diaQuizView.findViewById<TextView>(R.id.region_name).text = regionName
        quizBuilder.setCustomTitle(diaQuizView)
        //quizBuilder.setView(diaQuizBtnView)
        quizBuilder.setSingleChoiceItems(quizList, -1, choiceListener)
/*
        diaQuizBtnView.findViewById<Button>(R.id.btn_quiz_yes).setOnClickListener {
            if (selectItem == -1) {
                Toast.makeText(this@Main2Activity, "항목을 선택해주세요", Toast.LENGTH_LONG).show()
                quizAnsBuilder2.setCancelable(false)
            } else {
                Toast.makeText(this@Main2Activity, " ${selectItem}번째, ${quizList[selectItem]}", Toast.LENGTH_LONG).show()
                quizBuilder2.dismiss()

                //정답일 경우
                quizAnsBuilder.show()

                //오답일 경우
                //quizWAnsBuilder.show()
            }
        }
        diaQuizBtnView.findViewById<Button>(R.id.btn_quiz_no).setOnClickListener {
            //quizAnsBuilder2.setCancelable(true)
            quizBuilder2.dismiss()
        }
        */
        quizBuilder.setPositiveButton("확인", diaListListener)
        quizBuilder.setNegativeButton("취소", null)
        quizBuilder.show()

    }

    //현재위치 측정
    fun getMyLocation() {
        locManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                return
            }
            if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                return
            }
        }

        var location = locManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        var location2 = locManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        if (location != null) {
            setMyLocation(location)
        } else {
            if (location2 != null) {
                setMyLocation(location2)
            }
        }
        var listener = GetMyLocationListener()

        if (locManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)!! == true) {
            locManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 15f, listener)
        } else if (locManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)!! == true) {
            locManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 15f, listener)
        }
    }

    fun getDistance(nowLoc: Location, latlng2: LatLng): Double {
        var startLoc = nowLoc
        val endLoc = Location("PointB")
        endLoc.latitude = latlng2.latitude
        endLoc.longitude = latlng2.longitude
//      Log.d("startLoc >> ", "${startLoc}")
//      Log.d("endLoc >> ", "${endLoc}")

        //var distance = startLoc.distanceTo(endLoc).toDouble()
//      Log.d("거리계산>>", "${distance}")
        return startLoc.distanceTo(endLoc).toDouble()
    }

    fun setMyLocation(location: Location) {
        var userLng = LatLng(location.latitude, location.longitude) //현위치
        userLoc = location
        //distance = getDistance(location, desLng)

        var update1 = CameraUpdateFactory.newLatLng(userLng)
        var update2 = CameraUpdateFactory.zoomTo(15f)

        gMap?.moveCamera(update1)
        gMap?.animateCamera(update2)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                return
            }
            if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                return
            }
        }
        gMap?.isMyLocationEnabled = true
        gMap?.mapType = GoogleMap.MAP_TYPE_NORMAL //기본

//        Log.d("Main2Activity des 확인 위도>> ", "${quest1.latitude}")
//        Log.d("Main2Activity des 확인 경도>> ", "${quest1.longitude}")
    }

    inner class GetMyLocationListener : LocationListener {
        override fun onLocationChanged(location: Location?) {
            setMyLocation(location!!)
            locManager?.removeUpdates(this)
        }

        override fun onProviderDisabled(provider: String?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }
    }

    inner class Asynctask: AsyncTask<String, Void, String>() {
        var state = -1 //POST_regionQuizList= 0 , POST_quizQuestion = 1
        var response:String? = null

        override fun doInBackground(vararg params: String?): String? {
            state = Integer.parseInt(params[0])
            var client = OkHttpClient()
            var url = params[1]
            Log.d("param[2] 확인", ""+params[2])

            if(state == 0){ //퀴즈 리스트 조회
                var rname = params[2]
                response = Okhttp(applicationContext).POST(client, url, CreateJson().json_regionquizlist(rname))
            } else if(state == 1){ //퀴즈 문제 조회
                var quizId = params[2].toString().toInt()
                response = Okhttp(applicationContext).POST(client, url,CreateJson().json_beforequiz(quizId))
            }

            Log.d("통신 결과 response >> ", response)
            return response
        }

        override fun onPostExecute(result: String) {
            if(!result.isNullOrEmpty()) Log.d("checktest", result)

            if(!result[0].equals('{')) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext,"네트워크 연결이 좋지 않습니다", Toast.LENGTH_SHORT).show()

                return
            } else{
                var json = JSONObject(result)
                if(state == 0){
                    if(json.getInt("message") == 1){
                        var jsonArray = json.getJSONArray("data")
                        for (i in 0 until jsonArray.length()){
                            Log.d("obj", ""+jsonArray.get(i))
                            var jsonObject:JSONObject = jsonArray.getJSONObject(i)
                            Log.d("obj > qid", ""+jsonObject.getInt("qid"))
                            Log.d("obj > dname", ""+jsonObject.getString("dname"))
                        }

                    } else{ //state=0, message = 0
                        Log.d("Fail 0", " 0 네트워크 연결이 좋지 않음")
                        Toast.makeText(applicationContext, "네트워크 연결이 좋지 않습니다.", Toast.LENGTH_LONG).show()
                    }
                } else if(state == 1){
                    if (json.getInt("message") == 1){
                        var jsonObject = json.getJSONObject("data")
                        Log.d("data >> ", ""+jsonObject)
                        diaQuizView.region_question.text= jsonObject.getString("qname")
                        quizList = arrayOf(jsonObject.getString("qcontent1"), jsonObject.getString("qcontent2"),
                            jsonObject.getString("qcontent3"), jsonObject.getString("qcontent4"), jsonObject.getString("qcontent5"))
                    } else {
                        Log.d("Fail 1", " 1 네트워크 연결이 좋지 않음")
                        Toast.makeText(applicationContext, "1 네트워크 연결이 좋지 않습니다.", Toast.LENGTH_LONG).show()
                    }
                }

            }

        }
    }
}
