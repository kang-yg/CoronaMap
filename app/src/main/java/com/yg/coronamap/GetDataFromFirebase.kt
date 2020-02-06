package com.yg.coronamap

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

val PATIENT: String = "Patient"

class GetDataFromFirebase {
    lateinit var db: FirebaseFirestore
    lateinit var activity: Activity
    lateinit var mMap: GoogleMap
    lateinit var markerRootView: View
    lateinit var myMarker: TextView

    var patientCount: Int = 0
    var trackingCount: Int = 0

    constructor()

    constructor(
        _db: FirebaseFirestore,
        _activity: Activity,
        _mMap: GoogleMap,
        _markerRootView: View,
        _myMarker: TextView
    ) {
        this.db = _db
        this.activity = _activity
        this.mMap = _mMap
        this.markerRootView = _markerRootView
        this.myMarker = _myMarker
    }

    //TODO GlobarVariable.markerList에 저장하기
    fun getDataFromFirebase() {
        Log.d("getDataFromFirebase", "getDataFromFirebase")

        var tempName: String
        var tempAge: Int
        var tempPlace: String = ""
        var tempGeoPoint: GeoPoint
        var xGeo: Double
        var yGeo: Double
        var tempLatLng: LatLng? = null

        //환자 수
        db!!.collection("Data").get().addOnSuccessListener { documentSnapshot ->
            patientCount = documentSnapshot.size()
            Log.d("getDataFromFirebase", "patientCount : ${patientCount}")

            //환자 기본정보
            for (i in 0 until patientCount) {
                db!!.collection("Data").document(PATIENT.plus(i)).collection("BaseInfo").get()
                    .addOnSuccessListener { documentSnapshot ->
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.documentChanges[0].document}"
                        )

                        //이름
                        //TODO 환자정보에 이름은 없으므로 Firebase와 코드를 삭제해야 함.
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.documentChanges[0].document.get("name")}"
                        )
                        tempName =
                            documentSnapshot.documentChanges[0].document.get("name").toString()
                        Log.d("myInfo", "name : ${tempName}")

                        //나이
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.documentChanges[0].document.get("age")}"
                        )
                        tempAge = documentSnapshot.documentChanges[0].document.get("age").toString()
                            .toInt()
                        Log.d("myInfo", "age : ${tempAge}")
                    }
            }

            //환자 이동정보
            for (i in 0 until patientCount) {
                db!!.collection("Data").document(PATIENT.plus(i)).collection("MoveInfo").get()
                    .addOnSuccessListener { documentSnapshot ->
                        Log.d(
                            "getDataFromFirebase",
                            "TrackingCount : ${documentSnapshot.documentChanges.size}"
                        )
                        trackingCount = documentSnapshot.documentChanges.size
                        //환자의 Tracking갯수에 따른 반복
                        for (j in 0 until trackingCount) {
                            Log.d(
                                "getDataFromFirebase",
                                "${documentSnapshot.documentChanges[j].document}"
                            )

                            //장소명
                            Log.d(
                                "getDataFromFirebase",
                                "${documentSnapshot.documentChanges[j].document.get("place")}"
                            )
                            tempPlace =
                                documentSnapshot.documentChanges[j].document.get("place").toString()
                            Log.d("myInfo", "place : ${tempPlace}")

                            //좌표
                            Log.d(
                                "getDataFromFirebase",
                                "${documentSnapshot.documentChanges[j].document.get("coordinate")}"
                            )
                            tempGeoPoint =
                                documentSnapshot.documentChanges[j].document.getGeoPoint("coordinate")!!
                            xGeo = tempGeoPoint.latitude
                            yGeo = tempGeoPoint.longitude
                            tempLatLng = LatLng(xGeo, yGeo)
                            Log.d("myInfo", "geoPoint : ${tempGeoPoint}")

                            //날짜
                            Log.d(
                                "getDataFromFirebase",
                                "${documentSnapshot.documentChanges[j].document.get("date")}"
                            )

                            var tempMarkerItem: MarkerItem = MarkerItem(tempPlace, tempLatLng!!)
                            myAddMarker(tempMarkerItem, false)

//                            GlobarVariable.markerList.add(tempMarkerItem)
                        }
                    }
            }
        }.addOnFailureListener { exception -> Log.w("getDataFromFirebase", exception) }
    }

//    fun getMarkerItem() {
//        for (i in GlobarVariable.markerList) {
//            myAddMarker(i, false)
//        }
//    }

    //마커 추가01
    fun myAddMarker(_markerItem: MarkerItem, isSelectMarker: Boolean): Marker {
        val position: LatLng =
            LatLng(_markerItem.movelatLng.latitude, _markerItem.movelatLng.longitude)
        val place: String = _markerItem.movePlace
        myMarker.text = place

        if (isSelectMarker) {
            myMarker.setBackgroundResource(R.drawable.ic_marker_phone_blue)
            myMarker.setTextColor(Color.WHITE)
        } else {
            myMarker.setBackgroundResource(R.drawable.ic_marker_phone)
            myMarker.setTextColor(Color.BLACK)
        }

        val markerOption: MarkerOptions = MarkerOptions()
        markerOption.title(place)
        markerOption.position(position)
        markerOption.icon(
            BitmapDescriptorFactory.fromBitmap(
                createDrawableFromView(
                    activity,
                    markerRootView
                )
            )
        )

        return mMap.addMarker(markerOption)
    }

    //마커 추가02
    fun myAddMarker(_marker: Marker, isSelectMarker: Boolean): Marker {
        val lat: Double = _marker.position.latitude
        val lon: Double = _marker.position.longitude
        val place: String = _marker.title
        val tempLatLng: LatLng = LatLng(lat, lon)

        val tempMarkerItem: MarkerItem = MarkerItem(place, tempLatLng)

        return myAddMarker(tempMarkerItem, isSelectMarker)
    }

    //View를 Bitmap으로 변화
    fun createDrawableFromView(_context: Context, _view: View): Bitmap {
        val displayMetrics: DisplayMetrics = DisplayMetrics()
        (_context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        _view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        _view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        _view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)

        val bitmap: Bitmap =
            Bitmap.createBitmap(_view.measuredWidth, _view.measuredHeight, Bitmap.Config.ARGB_8888)
        val myCanvas: Canvas = Canvas(bitmap)
        _view.draw(myCanvas)

        return bitmap
    }
}