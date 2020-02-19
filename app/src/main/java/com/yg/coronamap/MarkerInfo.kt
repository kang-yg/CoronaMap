package com.yg.coronamap

import com.google.android.gms.maps.model.LatLng

class MarkerInfo{
    var patientNum: Int = -1
    var patientGender: String = ""
    var patientAge: Int = -1
    var patientNationality: String = ""
    var patientWuhan: Boolean = false
    lateinit var patientEntry: String
    lateinit var patientDiagnosis: String
    var patientHospital: String = ""
    var patientContactNum: Int = -1

    var moveSequence: Int = -1
    var movePlace: String = ""
    lateinit var moveDate: String
    lateinit var movelatLng: LatLng

    constructor(
        patientNum: Int,
        patientGender: String,
        patientAge: Int,
        patientNationality: String,
        patientWuhan: Boolean,
        patientEntry: String,
        patientDiagnosis: String,
        patientHospital: String,
        patientContactNum: Int,
        moveSequence: Int,
        movePlace: String,
        moveDate: String,
        movelatLng: LatLng
    ) {
        this.patientNum = patientNum
        this.patientGender = patientGender
        this.patientAge = patientAge
        this.patientNationality = patientNationality
        this.patientWuhan = patientWuhan
        this.patientEntry = patientEntry
        this.patientDiagnosis = patientDiagnosis
        this.patientHospital = patientHospital
        this.patientContactNum = patientContactNum
        this.moveSequence = moveSequence
        this.movePlace = movePlace
        this.moveDate = moveDate
        this.movelatLng = movelatLng
    }
}