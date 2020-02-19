package com.yg.coronamap

import android.app.Activity

import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

val INFO: String = "Info"

class GetDataFromFirebase {
    lateinit var db: FirebaseFirestore
    lateinit var activity: Activity
    lateinit var mMap: GoogleMap

    var infoCount: Int = 0

    constructor(
        _db: FirebaseFirestore,
        _activity: Activity,
        _mMap: GoogleMap
    ) {
        this.db = _db
        this.activity = _activity
        this.mMap = _mMap
    }

    fun getDataFromFirebase() {
        Log.d("getDataFromFirebase", "getDataFromFirebase")

        val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()

        //기본정보
        var tempAge: Int = -1 //나이
        var tempContactNum: Int = -1 //접촉자 수
        var tempDiagnosis: String = "" //확진 날짜
        var tempEntry: String = "" //입국 날짜
        var tempGender: String = "" //성별
        var temphospital: String = "" //입원한 병원
        var tempNationality: String = "" //국적
        var tempNum: Int = -1 //환자 번호
        var tempWuhan: Boolean = false //우한 방문 여부

        //이동정보
        var tempGeoPoint: GeoPoint //좌표
        var xGeo: Double
        var yGeo: Double
        var tempLatLng: LatLng? = null
        var tempDate: String = "" //날짜
        var tempPlace: String = "" //장소
        var tempSequence: Int = -1 //순서

        //정보 수
        db!!.collection("Data").get().addOnSuccessListener { documentSnapshot ->
            infoCount = documentSnapshot.size()
            Log.d("getDataFromFirebase", "infoCount : ${infoCount}")

        }.addOnCompleteListener {
            //환자 기본정보
            for (i in 0 until infoCount) {
                db!!.collection("Data").document(INFO.plus(i)).get()
                    .addOnSuccessListener { documentSnapshot ->
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.data}"
                        )

                        //나이
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.get("age")}"
                        )
                        tempAge = documentSnapshot.get("age").toString()
                            .toInt()
                        Log.d("myInfo", "age : ${tempAge}")

                        //접촉자 수
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.get("contactNum")}"
                        )
                        tempContactNum =
                            documentSnapshot.get("contactNum")
                                .toString().toInt()
                        Log.d("myInfo", "contactNum : ${tempContactNum}")

                        //확진 날짜
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.get("diagnosis")}"
                        )
                        var diaSeconds: Long =
                            documentSnapshot.getTimestamp("diagnosis")!!.seconds
                        val diaSMilliSeconds: Long = diaSeconds * 1000
                        calendar.timeInMillis = diaSMilliSeconds
                        tempDiagnosis = formatter.format(calendar.time)
                        Log.d("myInfo", "diagnosis : ${tempDiagnosis}")

                        //입국 날짜
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.get("entry")}"
                        )
                        var entrySeconds: Long =
                            documentSnapshot.getTimestamp("entry")!!.seconds
                        val entryMilliSeconds: Long = entrySeconds * 1000
                        calendar.timeInMillis = entryMilliSeconds
                        tempEntry = formatter.format(calendar.time)
                        Log.d("myInfo", "entry : ${tempEntry}")

                        //성별
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.get("gender")}"
                        )
                        tempGender =
                            documentSnapshot.get("gender").toString()
                        Log.d("myInfo", "gender : ${tempGender}")

                        //입원한 병원
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.get("hospital")}"
                        )
                        temphospital =
                            documentSnapshot.get("hospital").toString()
                        Log.d("myInfo", "hospital : ${temphospital}")

                        //국적
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.get("nationality")}"
                        )
                        tempNationality =
                            documentSnapshot.get("nationality")
                                .toString()
                        Log.d("myInfo", "nationality : ${tempNationality}")

                        //환자 번호
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.get("num")}"
                        )
                        tempNum =
                            documentSnapshot.get("num").toString()
                                .toInt()
                        Log.d("myInfo", "num : ${tempNum}")

                        //우한 방문 여부
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.get("wuhan")}"
                        )
                        tempWuhan =
                            documentSnapshot.get("wuhan").toString()
                                .toBoolean()
                        Log.d("myInfo", "wuhan : ${tempWuhan}")

                        //장소명
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.get("place")}"
                        )
                        tempPlace =
                            documentSnapshot.get("place").toString()
                        Log.d("myInfo", "place : ${tempPlace}")

                        //좌표
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.get("coordinate")}"
                        )
                        tempGeoPoint =
                            documentSnapshot.getGeoPoint("coordinate")!!
                        xGeo = tempGeoPoint.latitude
                        yGeo = tempGeoPoint.longitude
                        tempLatLng = LatLng(xGeo, yGeo)
                        Log.d("myInfo", "geoPoint : ${tempGeoPoint}")

                        //날짜
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.get("date")}"
                        )
                        var seconds: Long =
                            documentSnapshot.getTimestamp("date")!!.seconds
                        val milliSeconds: Long = seconds * 1000
                        calendar.timeInMillis = milliSeconds
                        tempDate = formatter.format(calendar.time)
                        Log.d("myInfo", "date : ${tempDate}")

                        //순서
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.get("sequence")}"
                        )
                        tempSequence =
                            documentSnapshot.get("sequence")
                                .toString().toInt()
                        Log.d("myInfo", "sequence : ${tempSequence}")

                        //환자 번호
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.get("num")}"
                        )

                        Log.d(
                            "myInfo",
                            "------------------------------end------------------------------"
                        )

                        val tempMarkerInfo: MarkerInfo = MarkerInfo(
                            tempNum,
                            tempGender,
                            tempAge,
                            tempNationality,
                            tempWuhan,
                            tempEntry,
                            tempDiagnosis,
                            temphospital,
                            tempContactNum,
                            tempSequence,
                            tempPlace,
                            tempDate,
                            tempLatLng!!
                        )

                        setMyMarker(tempMarkerInfo)
                    }
            }
        }
    }

    fun setMyMarker(_tempMarkerInfo: MarkerInfo): Marker {
        val markerOption: MarkerOptions = MarkerOptions()
        markerOption.position(_tempMarkerInfo.movelatLng)
        markerOption.title(_tempMarkerInfo.movePlace)

        when (_tempMarkerInfo.patientNum) {
            0 -> {
                markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            }

            1 -> {
                markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))

            }
        }

        return mMap.addMarker(markerOption)
    }

}