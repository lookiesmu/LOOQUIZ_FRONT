package com.example.looquiz

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var gMap: GoogleMap? = null
    var locManager: LocationManager? = null
    var desLoc: Location? = null
    var userLoc: Location? = null

    lateinit var userLng: LatLng
    lateinit var des:LatLng


    //var res = 0.0

    var permission_list = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        des = LatLng(37.579600, 126.976998)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permission_list, 0)
        } else{
            init()
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
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
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                startActivity(Intent(this,MyPageActivity::class.java))
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {
                startActivity(Intent(this,RoomChoose::class.java))
            }
            R.id.nav_manage -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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
        //var mapFragment = supportFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        var mapFragment = supportFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(callback)

    }

    inner class MapReadyCallback : OnMapReadyCallback {
        override fun onMapReady(p0: GoogleMap?) {
            gMap = p0
            getMyLocation()
            //val city = LatLng(37.579600, 126.976998)

            //getDistance(userLng, des)
            //Log.d("getDistance() result", "getDistance result ${getDistance(userLng, des)}Km")


            //Log.d("final check >> ", "${res}km")
            gMap?.addMarker(MarkerOptions().position(des).title("경복궁"))
            gMap?.moveCamera(CameraUpdateFactory.newLatLng(des))
            gMap?.setOnInfoWindowClickListener {

                val intent = Intent(applicationContext, Main2::class.java)
                startActivity(intent)
            }
        }
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
            locManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 15f, listener)
        } else if (locManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)!! == true){
            locManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 15f, listener)
        }




    }

    fun setMyLocation(location: Location){
        var lat = location.latitude
        var lng = location.longitude
        Log.d("test", "위도 ${lat}")
        Log.d("test", "경도 ${lng}")
        var position = LatLng(lat, lng) //현위치
        userLng = position
        userLoc = location

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

        Log.d("des 확인 위도>> ", "${des.latitude}")
        Log.d("des 확인 경도>> ", "${des.longitude}")
/*
        desLoc?.latitude = des.latitude
        desLoc?.longitude = des.longitude
        var desLng = LatLng(des.latitude, des.longitude)
        Log.d("desLoc?.latitude 확인 >>", "${desLoc?.latitude}")
        Log.d("desLoc?.longitude 확인 >>", "${desLoc?.longitude}")
*/
        var loc2: Location = location
        loc2.longitude = des.longitude
        loc2.latitude = des.latitude

        Log.d("거리계산 test userLoc", "${userLoc}")
        Log.d("거리계산 test desLoc", "${loc2}")

        Log.d("확인", "${userLoc?.distanceTo(loc2)} km")





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
