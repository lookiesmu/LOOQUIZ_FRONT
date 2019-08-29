package com.example.looquiz

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.act_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        setSupportActionBar(toolbar)

        desLng = LatLng(37.579600, 126.976998)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 0)
        } else {
            init()
        }

        //로그아웃
        var navigationView = findViewById<NavigationView>(R.id.nav_view)
        var view:View = navigationView.getHeaderView(0)
        var btn_logout = view.findViewById<Button>(R.id.btn_logout)
        btn_logout.setOnClickListener {
            Toast.makeText(this, "로그아웃 버튼 누름", Toast.LENGTH_LONG).show()
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
                //des = LatLng(37.5824994,126.9833762)
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
                startActivity(Intent(this, MakingQuizActivity::class.java))
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
}
