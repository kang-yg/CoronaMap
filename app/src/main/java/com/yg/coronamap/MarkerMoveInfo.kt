package com.yg.coronamap

import com.google.android.gms.maps.model.LatLng

class MarkerMoveInfo {
    var movePatientNum: Int = -1
    var moveSequence: Int = -1
    var movePlace: String = ""
    lateinit var moveDate: String
    lateinit var movelatLng: LatLng

    constructor(
        _movePatientNum: Int,
        _moveSequence: Int,
        _movePlace: String,
        _moveDate: String,
        _movelatLng: LatLng
    ) {
        this.moveSequence = _moveSequence
        this.movePlace = _movePlace
        this.moveDate = _moveDate
        this.movelatLng = _movelatLng
    }
}