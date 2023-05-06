package com.sasuke.weather

import android.app.Application
import com.drake.net.NetConfig
import com.drake.net.okhttp.setConverter
import com.drake.net.okhttp.setDebug
import com.drake.statelayout.StateConfig
import java.util.concurrent.TimeUnit

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        NetConfig.initialize("https://restapi.amap.com/v3/weather") {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            setConverter(GsonConvert())
            setDebug(true)
        }
        StateConfig.apply {
            errorLayout = R.layout.layout_error
            loadingLayout = R.layout.layout_loading
            setRetryIds(R.id.retryBtn)
        }
    }
}