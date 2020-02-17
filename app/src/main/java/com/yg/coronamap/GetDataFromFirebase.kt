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
import kotlin.collections.ArrayList

val PATIENT: String = "Patient"

class GetDataFromFirebase {
    lateinit var db: FirebaseFirestore
    lateinit var activity: Activity
    lateinit var mMap: GoogleMap

    var patientCount: Int = 0
    var trackingCount: Int = 0
    var totalTrackingCount : Int = 0

    var tempMarkerBaseInfoArray: ArrayList<MarkerBaseInfo> = arrayListOf()
    var tempMarkerMoveInfoArray: ArrayList<MarkerMoveInfo> = arrayListOf()
    lateinit var tempMarkerFinal: MarkerFinal

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
        var tempMovePatientNum: Int = -1

        //환자 수
        db!!.collection("Data").get().addOnSuccessListener { documentSnapshot ->
            patientCount = documentSnapshot.size()
            Log.d("getDataFromFirebase", "patientCount : ${patientCount}")

        }.addOnCompleteListener {
            //환자 기본정보
            for (i in 0 until patientCount) {
                db!!.collection("Data").document(PATIENT.plus(i)).collection("BaseInfo").get()
                    .addOnSuccessListener { documentSnapshot ->
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.documentChanges[0].document}"
                        )

                        //나이
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.documentChanges[0].document.get("age")}"
                        )
                        tempAge = documentSnapshot.documentChanges[0].document.get("age").toString()
                            .toInt()
                        Log.d("myInfo", "age : ${tempAge}")

                        //접촉자 수
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.documentChanges[0].document.get("contactNum")}"
                        )
                        tempContactNum =
                            documentSnapshot.documentChanges[0].document.get("contactNum")
                                .toString().toInt()
                        Log.d("myInfo", "contactNum : ${tempContactNum}")

                        //확진 날짜
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.documentChanges[0].document.get("diagnosis")}"
                        )
                        var diaSeconds: Long =
                            documentSnapshot.documentChanges[0].document.getTimestamp("diagnosis")!!.seconds
                        val diaSMilliSeconds: Long = diaSeconds * 1000
                        calendar.timeInMillis = diaSMilliSeconds
                        tempDiagnosis = formatter.format(calendar.time)
                        Log.d("myInfo", "diagnosis : ${tempDiagnosis}")

                        //입국 날짜
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.documentChanges[0].document.get("entry")}"
                        )
                        var entrySeconds: Long =
                            documentSnapshot.documentChanges[0].document.getTimestamp("entry")!!.seconds
                        val entryMilliSeconds: Long = entrySeconds * 1000
                        calendar.timeInMillis = entryMilliSeconds
                        tempEntry = formatter.format(calendar.time)
                        Log.d("myInfo", "entry : ${tempEntry}")

                        //성별
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.documentChanges[0].document.get("gender")}"
                        )
                        tempGender =
                            documentSnapshot.documentChanges[0].document.get("gender").toString()
                        Log.d("myInfo", "gender : ${tempGender}")

                        //입원한 병원
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.documentChanges[0].document.get("hospital")}"
                        )
                        temphospital =
                            documentSnapshot.documentChanges[0].document.get("hospital").toString()
                        Log.d("myInfo", "hospital : ${temphospital}")

                        //국적
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.documentChanges[0].document.get("nationality")}"
                        )
                        tempNationality =
                            documentSnapshot.documentChanges[0].document.get("nationality")
                                .toString()
                        Log.d("myInfo", "nationality : ${tempNationality}")

                        //환자 번호
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.documentChanges[0].document.get("num")}"
                        )
                        tempNum =
                            documentSnapshot.documentChanges[0].document.get("num").toString()
                                .toInt()
                        Log.d("myInfo", "num : ${tempNum}")

                        //우한 방문 여부
                        Log.d(
                            "getDataFromFirebase",
                            "${documentSnapshot.documentChanges[0].document.get("wuhan")}"
                        )
                        tempWuhan =
                            documentSnapshot.documentChanges[0].document.get("wuhan").toString()
                                .toBoolean()
                        Log.d("myInfo", "wuhan : ${tempWuhan}")

                        //마커 기본정보 객체
                        var tempMarkerBaseInfo: MarkerBaseInfo =
                            MarkerBaseInfo(
                                tempNum,
                                tempGender,
                                tempAge,
                                tempNationality,
                                tempWuhan,
                                tempEntry,
                                tempDiagnosis,
                                temphospital,
                                tempContactNum
                            )
                        tempMarkerBaseInfoArray.add(tempMarkerBaseInfo)

                        Log.d("myCount", "base : ${tempMarkerBaseInfoArray.size}")
                    }
            }
        }.addOnCompleteListener {
            //환자 이동정보
            for (i in 0 until patientCount) {
                db!!.collection("Data").document(PATIENT.plus(i)).collection("MoveInfo").get()
                    .addOnSuccessListener { documentSnapshot ->
                        Log.d(
                            "getDataFromFirebase",
                            "TrackingCount : ${documentSnapshot.documentChanges.size}"
                        )
                        trackingCount = documentSnapshot.documentChanges.size
                        totalTrackingCount += trackingCount
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
                            var seconds: Long =
                                documentSnapshot.documentChanges[j].document.getTimestamp("date")!!.seconds
                            val milliSeconds: Long = seconds * 1000
                            calendar.timeInMillis = milliSeconds
                            tempDate = formatter.format(calendar.time)
                            Log.d("myInfo", "date : ${tempDate}")

                            //순서
                            Log.d(
                                "getDataFromFirebase",
                                "${documentSnapshot.documentChanges[j].document.get("sequence")}"
                            )
                            tempSequence =
                                documentSnapshot.documentChanges[j].document.get("sequence")
                                    .toString().toInt()
                            Log.d("myInfo", "sequence : ${tempSequence}")

                            //환자 번호
                            Log.d(
                                "getDataFromFirebase",
                                "${documentSnapshot.documentChanges[j].document.get("num")}"
                            )
                            tempMovePatientNum =
                                documentSnapshot.documentChanges[j].document.get("num")
                                    .toString().toInt()
                            Log.d("myInfo", "MovePatientNum : ${tempMovePatientNum}")

                            //마커 이동정보 객체
                            var tempMarkerMoveInfo: MarkerMoveInfo = MarkerMoveInfo(
                                tempMovePatientNum,
                                tempSequence,
                                tempPlace,
                                tempDate,
                                tempLatLng!!
                            )
                            tempMarkerMoveInfoArray.add(tempMarkerMoveInfo)

                            Log.d("myCount", "move : ${tempMarkerMoveInfoArray.size}")

                            if (tempMarkerBaseInfoArray.size == patientCount && tempMarkerMoveInfoArray.size == totalTrackingCount){
                                Log.d("myCount", "getAllDatas")
                            }
                        }
                    }
            }
        }
    }

    fun setMyMarker(_tempMarkerFinal: MarkerFinal): Marker {
        val markerOption: MarkerOptions = MarkerOptions()
        markerOption.position(_tempMarkerFinal.finalMoveInfo.movelatLng)
        markerOption.title(_tempMarkerFinal.finalMoveInfo.movePlace)

        when (_tempMarkerFinal.finalBaseInfo.patientNum) {
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