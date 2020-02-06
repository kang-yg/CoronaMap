package com.yg.coronamap

import android.app.Application

class GlobarVariable : Application(){
    companion object{
        lateinit var getData : GetDataFromFirebase
    }
}