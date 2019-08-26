package com.example.looquiz

import android.app.AlertDialog
import android.app.AlertDialog.Builder
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
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Main2 : AppCompatActivity() {

    var gMap: GoogleMap? = null
    var locManager: LocationManager? = null

    var quizList = arrayOf("정답임", "오답임", "오답", "옳지 않은 보기", "관련없는 보기")
    //var quizList = arrayListOf<String>("정답임", "오답임", "오답", "옳지 않은 보기", "관련없는 보기")
    //val quizAdapter = DiaQuizListAdapter(this, quizList)

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

        q1 = LatLng(37.575840, 126.977009) //광화문
        q2 = LatLng(37.577166, 126.976795) //문양전 보물 343호
        q3 = LatLng(37.577961, 126.976866) //경복궁
        q4 = LatLng(37.580314, 126.978075)//자경전
        qu5 = LatLng(37.578104, 126.979406)//건춘문
        q6 = LatLng(37.577981, 126.974301) //비격진천뢰(천상열차분야지도 각석)
        q7 = LatLng(37.583146, 126.977261)//명성황후조난지(건청궁)
        q8 = LatLng(37.575946, 126.9744144) //홍례문
        q9 = LatLng(37.5759947, 126.9746318)//국립고궁박물관

        //val quizAdapter = DiaQuizListAdapter(this, quizList)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permission_list, 0)
        } else{
            init()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (result in grantResults){
            if (result == PackageManager.PERMISSION_DENIED){
                return
            }
        }
        init()
    }
    fun init(){
        var callback = MapReadyCallback()
        var mapFragment = supportFragmentManager.findFragmentById(R.id.regionmapview) as SupportMapFragment
        mapFragment.getMapAsync(callback)

    }

    inner class MapReadyCallback : OnMapReadyCallback {
        override fun onMapReady(p0: GoogleMap?) {
            gMap = p0
            getMyLocation()
            //var zoom = CameraUpdateFactory.zoomTo(15f)
            gMap?.animateCamera(CameraUpdateFactory.zoomTo(15f))
            gMap?.moveCamera(CameraUpdateFactory.newLatLng(q1))

            var marker1 = gMap?.addMarker(MarkerOptions().position(q1))
            marker1?.title="광화문"
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
                when(it){
                    marker1 ->{
                        Toast.makeText(applicationContext, "main2 광화문", Toast.LENGTH_LONG).show()
                        makeQuiz(marker1?.title)
                    }
                    marker2 -> {
                        Toast.makeText(applicationContext, "main2 문양전 보물 343호", Toast.LENGTH_LONG).show()
                        makeQuiz(marker2?.title)
                    }
                    marker3 -> {
                        Toast.makeText(applicationContext, "main2 경복궁", Toast.LENGTH_LONG).show()
                        makeQuiz(marker3?.title)
                    }
                    marker4 -> {
                        Toast.makeText(applicationContext, "main2 자경전", Toast.LENGTH_LONG).show()
                        makeQuiz(marker4?.title)
                    }
                    marker5 -> {
                        Toast.makeText(applicationContext, "main2 건춘문", Toast.LENGTH_LONG).show()
                        makeQuiz(marker5?.title)
                    }
                    marker6 -> {
                        Toast.makeText(applicationContext, "main2 비격진천뢰(천상열차분야지도 각석)", Toast.LENGTH_LONG).show()
                        makeQuiz(marker6?.title)
                    }
                    marker7 -> {
                        Toast.makeText(applicationContext, "main2 명성황후조난지(건청궁)", Toast.LENGTH_LONG).show()
                        makeQuiz(marker7?.title)
                    }
                    marker8 -> {
                        Toast.makeText(applicationContext, "main2 홍례문", Toast.LENGTH_LONG).show()
                        makeQuiz(marker8?.title)
                    }
                    marker9 -> {
                        Toast.makeText(applicationContext, "main2 국립고궁박물관", Toast.LENGTH_LONG).show()
                        makeQuiz(marker9?.title)
                    }
                }
            }

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_DENIED){
                    return
                }
                if(checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED){
                    return
                }
            }
            gMap?.isMyLocationEnabled = true
            gMap?.mapType = GoogleMap.MAP_TYPE_NORMAL //기본
        }
    }

    fun makeQuiz(regionName:String?){
        var builder = AlertDialog.Builder(this@Main2)
        var hintBuilder = AlertDialog.Builder(this@Main2)

        var diaView = layoutInflater.inflate(R.layout.diaquiz, null)
        var diaHintView = layoutInflater.inflate(R.layout.diahint, null)

        //builder.setView(diaView)
        diaView.findViewById<TextView>(R.id.region_name).text = regionName
        builder.setCustomTitle(diaView)

       /* diaView.findViewById<Button>(R.id.btn_hint).setOnClickListener {
            var diaHintView = layoutInflater.inflate(R.layout.diahint, null)
            builder.setView(diaHintView)
            builder.setPositiveButton("확인", null)
        }*/

        var listener = object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                Toast.makeText(this@Main2, " 확인 ${quizList[which]}", Toast.LENGTH_LONG).show()
                //builder.setItems(null, null)


            }
        }
        //builder.setAdapter(quizAdapter, null)
        builder.setPositiveButton("확인", null)
        builder.setNegativeButton("취소", null)
        builder.setItems(quizList, listener)
        builder.show()

        var btnHint: Button = diaView.findViewById<Button>(R.id.btn_hint)
        btnHint.setOnClickListener {
            Toast.makeText(this, "힌트버튼 클릭", Toast.LENGTH_LONG).show()
            finish()
            showHint()
        }
    }

    fun showHint(){
        var hintBuilder = AlertDialog.Builder(this@Main2)
        var diaHintView = layoutInflater.inflate(R.layout.diahint, null)
        hintBuilder.setView(diaHintView)
        hintBuilder.setPositiveButton("확인", null)
    }
    //현재위치 측정
    fun getMyLocation(){
        locManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_DENIED){
                return
            }
            if(checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED){
                return
            }
        }

        var location = locManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        var location2 = locManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        if(location != null){
            setMyLocation(location)
        } else{
            if(location2 != null){
                setMyLocation(location2)
            }
        }
        var listener = GetMyLocationListener()

        if(locManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)!! == true){
            locManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 15f, listener)
        } else if (locManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)!! == true){
            locManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 15f, listener)
        }
    }

    fun setMyLocation(location: Location){
        var lat = location.latitude
        var lng = location.longitude
        Log.d("Main2  test>> ", "위도 ${lat}")
        Log.d("Main2  test>> ", "경도 ${lng}")
        var position = LatLng(lat, lng) //현위치

        var update1 = CameraUpdateFactory.newLatLng(position)
        var update2 = CameraUpdateFactory.zoomTo(15f)

        gMap?.moveCamera(update1)
        gMap?.animateCamera(update2)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_DENIED){
                return
            }
            if(checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED){
                return
            }
        }
        gMap?.isMyLocationEnabled = true
        gMap?.mapType = GoogleMap.MAP_TYPE_NORMAL //기본

        Log.d("Main2 des 확인 위도>> ", "${q1.latitude}")
        Log.d("Main2 des 확인 경도>> ", "${q1.longitude}")

    }

    inner class GetMyLocationListener: LocationListener {
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
