package com.yg.coronamap

class MarkerFinal {
    lateinit var finalBaseInfoArray: ArrayList<MarkerBaseInfo>
    lateinit var finalMoveInfoArray: ArrayList<MarkerMoveInfo>

    lateinit var finalBaseInfo : MarkerBaseInfo
    lateinit var finalMoveInfo :MarkerMoveInfo

    constructor(
        _finalBaseInfoArray: ArrayList<MarkerBaseInfo>,
        _finalMoveInfoArray: ArrayList<MarkerMoveInfo>
    ) {
        this.finalBaseInfoArray = _finalBaseInfoArray
        this.finalMoveInfoArray = _finalMoveInfoArray
    }

    constructor(_finalBaseInfoArray: MarkerBaseInfo, _finalMoveInfo: MarkerMoveInfo){
        this.finalBaseInfo = _finalBaseInfoArray
        this.finalMoveInfo = _finalMoveInfo
    }

    fun matchInfo() : MarkerFinal{
        var result : MarkerFinal? = null
        for (i in this.finalMoveInfoArray) {
            for (j in this.finalBaseInfoArray) {
                if (i.movePatientNum == j.patientNum) {
                    result = MarkerFinal(j,i)
                }
            }
        }
        return result!!
    }
}