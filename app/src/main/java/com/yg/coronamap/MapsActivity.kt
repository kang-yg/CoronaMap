package com.yg.coronamap


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.firebase.firestore.FirebaseFirestore


//TODO 마커에 환자의 번호를 표시하며, 색을 다르게 한다.
//TODO 같은 환자의 마커를 선으로 이어야 한다.
//TODO 마커를 클릭하면 세부 정보를 보여줘야 한다.
//TODO 지도에서 특정 환자만 선택해서 볼 수 있어야 한다.
//TODO 메뉴를 만들어서 지도가 아닌 리스트의 형태로 환자별로 확인이 가능하다.
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    var selectedMarker : Marker? = null
    lateinit var markerRootView: View

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("myInfo", "onMapReady()")
        mMap = googleMap

/*        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        val myZoomLevel : Float = 14F
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(37.537523, 126.96558),
                myZoomLevel
            )
        )
        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener(this)

        markerRootView = LayoutInflater.from(this).inflate(R.layout.marker, null)
        val markerTextView : TextView = markerRootView.findViewById(R.id.myMarker_textView)
        GlobarVariable.getData = GetDataFromFirebase(db, this, mMap, markerRootView, markerTextView)
        GlobarVariable.getData.getDataFromFirebase()
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        val center : CameraUpdate = CameraUpdateFactory.newLatLng(marker!!.position)
        mMap.animateCamera(center)

        changeSelectedMarker(marker,  GlobarVariable.getData)

        return true
    }

    override fun onMapClick(p0: LatLng?) {
        changeSelectedMarker(null,  GlobarVariable.getData)
    }

    fun changeSelectedMarker(_marker: Marker?, _getData : GetDataFromFirebase?){
        //선택한 마커 선택해제
        if (selectedMarker != null){
            _getData!!.myAddMarker(selectedMarker!!, false)
            selectedMarker!!.remove()
        }

        //선택한 마커 표시
        if (_marker != null){
            selectedMarker = _getData!!.myAddMarker(_marker, true)
            _marker.remove()
        }
    }
}
