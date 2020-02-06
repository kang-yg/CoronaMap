package com.yg.coronamap

import com.google.android.gms.maps.model.LatLng
import java.util.*

class MarkerItem {
    var patientNum: Int = 0
    var patientGender: String = ""
    var patientAge: Int = 0
    var patientNationality: String = ""
    var patientWuhan: Boolean = false
    lateinit var patientEntry: Date
    lateinit var patientDiagnosis: Date
    var patientHospital: String = ""
    var patientContactNum: Int = 0

    var moveSequence: Int = 0
    var movePlace: String = ""
    lateinit var moveDate: Date
    lateinit var movelatLng: LatLng

    constructor(
        patientNum: Int,
        patientGender: String,
        patientAge: Int,
        patientNationality: String,
        patientWuhan: Boolean,
        patientEntry: Date,
        patientDiagnosis: Date,
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

    constructor(
        patientNum: Int,
        patientGender: String,
        patientAge: Int,
        patientNationality: String,
        patientWuhan: Boolean,
        patientEntry: Date,
        patientDiagnosis: Date,
        patientHospital: String,
        patientContactNum: Int,
        moveSequence: Int,
        movePlace: String,
        moveDate: Date,
        latLng: LatLng
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
        this.movelatLng = latLng
    }

    constructor(movePlace: String, movelatLng: LatLng) {
        this.movePlace = movePlace
        this.movelatLng = movelatLng
    }
}