package com.yg.coronamap

import com.google.android.gms.maps.model.LatLng

class MarkerBaseInfo {
    var patientNum: Int = -1
    var patientGender: String = ""
    var patientAge: Int = -1
    var patientNationality: String = ""
    var patientWuhan: Boolean = false
    lateinit var patientEntry: String
    lateinit var patientDiagnosis: String
    var patientHospital: String = ""
    var patientContactNum: Int = -1

    constructor(
        patientNum: Int,
        patientGender: String,
        patientAge: Int,
        patientNationality: String,
        patientWuhan: Boolean,
        patientEntry: String,
        patientDiagnosis: String,
        patientHospital: String,
        patientContactNum: Int
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
    }
}