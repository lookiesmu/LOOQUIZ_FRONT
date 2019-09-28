package com.example.looquiz

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.app.AlertDialog
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.SearchEvent
import android.view.View
import android.widget.Button
import android.widget.SearchView
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
import okhttp3.OkHttpClient
import org.json.JSONObject

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var gMap: GoogleMap? = null
    var locManager: LocationManager? = null
    //lateinit var userLng: LatLng
    lateinit var desLng: LatLng
    var distance = 0.0

    var permission_list = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    //var regionList = arrayOf("*  항목1","*  힝목2","*  항목3","*  항목4" )
    var regionList: Array<String>? = null
    //var regionListBuilder = AlertDialog.Builder(this)
    lateinit var regionListBuilder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        setSupportActionBar(toolbar)

        var colorAlpha = Color.parseColor("#80FFFFFF") //app bar 투명도 설정을 위한 컬러 코드 정수화
        toolbar.setBackgroundColor(colorAlpha) //app bar 투명도 설정을 위한 배경색 임시 설정 이 두개 코드 지워도댐

        desLng = LatLng(37.579600, 126.976998)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 0)
        } else {
            init()
        }

        //로그아웃
        var navigationView = findViewById<NavigationView>(R.id.nav_view)
        var view:View = navigationView.getHeaderView(0)
        var userid=view.findViewById<TextView>(R.id.uid)

        var uid:String? = null
        uid = intent.getStringExtra("uid")
        userid.text = "${uid}" + " 님"

        var btn_logout = view.findViewById<Button>(R.id.btn_logout)
        btn_logout.setOnClickListener {
            Asynctask().execute("2",getString(R.string.logout))
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_LONG).show()
            startActivity(Intent(this,SignInActivity::class.java))
            finish()
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

        var search_item:MenuItem? = menu?.findItem(R.id.app_bar_search)
        var search_view= search_item?.actionView as android.support.v7.widget.SearchView

        search_view.queryHint = "참가코드를 입력해주세요."

        search_view.setOnQueryTextListener(object: android.support.v7.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(search_word: String?): Boolean {
                //방 참여 통신
                Asynctask().execute("1",getString(R.string.enter_room), search_word.toString())
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_seoul -> {
                showRegionList(item.title.toString())
                return true
            }
            /*R.id.action_gyeonggi -> {
                showRegionList(item.title.toString())
                return true
            }
            R.id.action_gangwon -> {
                showRegionList(item.title.toString())
                return true
            }
            R.id.action_chungcheong -> {
                //Toast.makeText(this, ""+item.title, Toast.LENGTH_LONG).show()
                return true
            }*/
            R.id.action_gyeongsang -> { //경주
                showRegionList(item.title.toString())
                return true
            }
            /* R.id.action_jeolla -> {
                 //Toast.makeText(this, ""+item.title, Toast.LENGTH_LONG).show()
                 return true
             }
             R.id.action_jeu -> {
                 //Toast.makeText(this, ""+item.title, Toast.LENGTH_LONG).show()
                 return true
             }*/
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun showRegionList(region: String){
        regionListBuilder = AlertDialog.Builder(this)
        regionListBuilder.setTitle(region+" 리스트")
        Asynctask().execute("0", getString(R.string.cityname), region)
    }

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
        //var mapFragment = supportFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        var mapFragment = supportFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(callback)
    }

    inner class MapReadyCallback : OnMapReadyCallback {
        override fun onMapReady(p0: GoogleMap?) {
            gMap = p0
            getMyLocation()
            Log.d("final check >> ", "${distance}km")
            gMap?.addMarker(MarkerOptions().position(desLng).title("경복궁"))
            //gMap?.addMarker(MarkerOptions().position(LatLng(37.5824994,126.9833762)).title("북촌한옥마을"))
            gMap?.moveCamera(CameraUpdateFactory.newLatLng(desLng))
            gMap?.setOnInfoWindowClickListener {
                //                if(distance <= 100) {
                val intent = Intent(applicationContext, Main2Activity::class.java)
                //Log.d("마커 title >>", ""+MarkerOptions().position(desLng).title("경복궁").title)
                intent.putExtra("regionName", MarkerOptions().position(desLng).title("경복궁").title)
                startActivity(intent)
//                }else Toast.makeText(applicationContext, "접근할 수 없습니다.", Toast.LENGTH_LONG).show()

            }
        }
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
            locManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 15f, listener)
        } else if (locManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)!! == true) {
            locManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 15f, listener)
        }
    }

    fun getDistance(nowLoc: Location, latlng2: LatLng): Double {
        var startLoc = nowLoc
        val endLoc = Location("PointB")
        endLoc.latitude = latlng2.latitude
        endLoc.longitude = latlng2.longitude
//        Log.d("startLoc >> ", "${startLoc}")
//        Log.d("endLoc >> ", "${endLoc}")
//        Log.d("거리계산>>", "${distance}")
        return startLoc.distanceTo(endLoc).toDouble()
    }

    fun setMyLocation(location: Location) {
        var userLng = LatLng(location.latitude, location.longitude) //현위치

        distance = getDistance(location, desLng)

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
        var state = -1 // GET_regionList = 0(전체도시 리스트 조회)  POST_enterRoom = 1(방 참여) GET_logout=2
        var response: String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])
            var client = OkHttpClient()
            var url = params[1]

            if(state == 0){ //전체 도시 리스트
                Log.d("param[2] 확인", ""+params[2])

                var cityname = params[2]
                url = url + "${cityname}"
                Log.d("확인2", url)
                response = Okhttp(applicationContext).GET(client, url)

            } else if(state == 1){
                var codenum = params[2]
                response = Okhttp(applicationContext).POST(client, url, CreateJson().json_enterroom(codenum))
            }
            else{
                response = Okhttp(applicationContext).GET(client, url)
                Log.d("check",url)
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

                if(state == 0) {
                    if (json.getInt("message") == 1) {
                        var jsonArray = json.getJSONArray("data")
                        Log.d("data 확인", "" + jsonArray)
                        var regList = Array<String>(jsonArray.length(), { "" })

                        for (i in 0 until jsonArray.length()) {
                            regList?.set(i, jsonArray.get(i).toString())
                        }

                        regionList = regList
                        regionListBuilder.setItems(regionList, null)
                        //regionListBuilder.setSingleChoiceItems(regionList, -1, null)
                        regionListBuilder.setNegativeButton("닫기", null)
                        regionListBuilder.show()

                    } else { //message == 0
                        Toast.makeText(applicationContext, "네트워크 연결이 좋지 않습니다.", Toast.LENGTH_LONG).show()
                    }
                } else if(state == 1){
                    if (json.getInt("message") == 1) {
                        Toast.makeText(applicationContext, "성공적으로 가입되었습니다.", Toast.LENGTH_LONG).show()
                        startActivity(Intent(applicationContext, RoomChoose::class.java))
                    } else { //message == 0
                        Toast.makeText(applicationContext, "참가코드를 올바르게 입력해주세요", Toast.LENGTH_LONG).show()
                    }
                }
                else{

                }
            }
        }
    }


}
