package com.example.looquiz

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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

class Main2Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var gMap: GoogleMap? = null
    var locManager: LocationManager? = null
    //lateinit var desLng: LatLng
    lateinit var userLoc: Location
    var distance = 0.0

    var quizList = arrayOf("정답임", "오답임", "오답", "옳지 않은 보기", "관련없는 보기")

    lateinit var q1: LatLng
    lateinit var q2: LatLng
    lateinit var q3: LatLng
    lateinit var q4: LatLng
    lateinit var qu5: LatLng
    lateinit var q6: LatLng
    lateinit var q7: LatLng
    lateinit var q8: LatLng
    lateinit var q9: LatLng

    var permission_list = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main2)
        setSupportActionBar(toolbar)

        q1 = LatLng(37.575840, 126.977009) //광화문
        q2 = LatLng(37.577166, 126.976795) //문양전 보물 343호
        q3 = LatLng(37.577961, 126.976866) //경복궁
        q4 = LatLng(37.580314, 126.978075)//자경전
        qu5 = LatLng(37.578104, 126.979406)//건춘문
        q6 = LatLng(37.577981, 126.974301) //비격진천뢰(천상열차분야지도 각석)
        q7 = LatLng(37.583146, 126.977261)//명성황후조난지(건청궁)
        q8 = LatLng(37.575946, 126.9744144) //홍례문
        q9 = LatLng(37.5759947, 126.9746318)//국립고궁박물관


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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_gbg -> {
                Toast.makeText(this, "메뉴 경복궁 누름", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.action_bukchon -> {
                Toast.makeText(this, "메뉴 북촌한옥마을 누름", Toast.LENGTH_LONG).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_mypage -> {
                startActivity(Intent(this, MyPageActivity::class.java))
            }
            R.id.nav_all -> {

            }
            R.id.nav_room -> {
                startActivity(Intent(this, RoomChoose::class.java))
            }
            R.id.nav_setting -> {

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
            gMap?.moveCamera(CameraUpdateFactory.newLatLng(q1))

            var marker1 = gMap?.addMarker(MarkerOptions().position(q1))
            marker1?.title = "광화문"
            var marker2 = gMap?.addMarker(MarkerOptions().position(q2))
            marker2?.title = "문양전 보물 343호"
            var marker3 = gMap?.addMarker(MarkerOptions().position(q3))
            marker3?.title = "경복궁"
            var marker4 = gMap?.addMarker(MarkerOptions().position(q4))
            marker4?.title = "자경전"
            var marker5 = gMap?.addMarker(MarkerOptions().position(qu5))
            marker5?.title = "건춘문"
            var marker6 = gMap?.addMarker(MarkerOptions().position(q6))
            marker6?.title = "비격진천뢰(천상열차분야지도 각석)"
            var marker7 = gMap?.addMarker(MarkerOptions().position(q7))
            marker7?.title = "명성황후조난지(건청궁)"
            var marker8 = gMap?.addMarker(MarkerOptions().position(q8))
            marker8?.title = "홍례문"
            var marker9 = gMap?.addMarker(MarkerOptions().position(q9))
            marker9?.title = "국립고궁박물관"

            gMap?.setOnInfoWindowClickListener {
                distance = getDistance(userLoc, it.position)
                Log.d("최종 거리계산 >> ", "${distance}km")
 //               if(distance<= 70) {
                    when (it) {
                        marker1 -> {
                            //Toast.makeText(applicationContext, "main2 광화문", Toast.LENGTH_LONG).show()
                            makeQuiz(marker1?.title)
                        }
                        marker2 -> makeQuiz(marker2?.title)
                        marker3 -> makeQuiz(marker3?.title)
                        marker4 -> makeQuiz(marker4?.title)
                        marker5 -> makeQuiz(marker5?.title)
                        marker6 -> makeQuiz(marker6?.title)
                        marker7 -> makeQuiz(marker7?.title)
                        marker8 -> makeQuiz(marker8?.title)
                        marker9 -> makeQuiz(marker9?.title)
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

    fun makeQuiz(regionName: String?) {
        var quizBuilder = AlertDialog.Builder(this@Main2Activity)
        var diaQuizView = layoutInflater.inflate(R.layout.dia_quiz, null)
        //var diaQuizBtnView = layoutInflater.inflate(R.layout.dia_quiz_button, null)
        var quizBuilder2: AlertDialog = quizBuilder.create()

        var quizAnsBuilder = AlertDialog.Builder(this@Main2Activity)
        var quizAnsView = layoutInflater.inflate(R.layout.dia_ans, null)
        var quizAnsBuilder2: AlertDialog = quizAnsBuilder.create()
        quizAnsBuilder.setView(quizAnsView)
        quizAnsBuilder.setPositiveButton("확인", null)

        var quizWAnsBuilder = AlertDialog.Builder(this@Main2Activity)
        var quizWAnsView = layoutInflater.inflate(R.layout.dia_wans, null)
        var quizWAnsBuilder2: AlertDialog = quizWAnsBuilder.create()
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

        var listener = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (selectItem == -1) {
                    Toast.makeText(this@Main2Activity, "항목을 선택해주세요", Toast.LENGTH_LONG).show()
                    quizBuilder2.dismiss()
                    makeQuiz(regionName)

                } else {
                    Toast.makeText(this@Main2Activity, " ${selectItem}번째, ${quizList[selectItem]}", Toast.LENGTH_LONG).show()
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
        quizBuilder.setPositiveButton("확인", listener)
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

//        Log.d("Main2Activity des 확인 위도>> ", "${q1.latitude}")
//        Log.d("Main2Activity des 확인 경도>> ", "${q1.longitude}")
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
}
