package com.gaoliuyang.expandabletreelistview

import android.app.Application
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits

/**
 * Created by gaoliuyang on 2019/2/28.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initAutoSize()
    }

    private fun initAutoSize() {
        AutoSizeConfig.getInstance()
            .setBaseOnWidth(true)
            .unitsManager
            .setSupportDP(false)
            .setSupportSP(false)
            .supportSubunits = Subunits.MM
    }
}