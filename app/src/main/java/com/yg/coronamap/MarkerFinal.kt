package com.yg.coronamap

class MarkerFinal {
    lateinit var finalBaseInfo: MarkerBaseInfo
    lateinit var finalMoveInfo: MarkerMoveInfo

    constructor(_finalBaseInfo: MarkerBaseInfo, _finalMoveInfo: MarkerMoveInfo) {
        this.finalBaseInfo = _finalBaseInfo
        this.finalMoveInfo = _finalMoveInfo
    }

    fun matchInfo(_finalBaseInfo: MarkerBaseInfo, _finalMoveInfo: MarkerMoveInfo) {
        if (finalBaseInfo.patientNum == _finalMoveInfo.movePatientNum) {
            MarkerFinal(_finalBaseInfo, _finalMoveInfo)
        }
    }
}