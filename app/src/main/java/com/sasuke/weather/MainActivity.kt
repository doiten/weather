package com.sasuke.weather

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.net.Get
import com.drake.net.utils.scope
import com.dylanc.viewbinding.binding
import com.sasuke.weather.data.Forecast
import com.sasuke.weather.data.Weather
import com.sasuke.weather.databinding.ActivityMainBinding
import com.sasuke.weather.databinding.LayoutWeatherCardBinding
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by binding()
    private val cities = listOf(
        "110000", //北京
        "310000", //上海
        "440100", //广州
        "440300", //深圳
        "320500", //苏州
        "210100", //沈阳
    )
    private val weathers = mutableListOf<Forecast>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        binding.apply {
            recyclerView.linear().setup {
                addType<Forecast>(R.layout.layout_weather_card)
                onBind {
                    val model = getModel<Forecast>()
                    val tomorrow = model.casts[1]
                    getBinding<LayoutWeatherCardBinding>().apply {
                        cityName.text = model.city
                        weather.text =
                            if (tomorrow.dayweather == tomorrow.nightweather) tomorrow.dayweather else "${tomorrow.dayweather}/${tomorrow.nightweather}"
                        temperature.text = "最高${tomorrow.daytemp}° 最低${tomorrow.nighttemp}°"
                        windPower.text = "${tomorrow.daywind}风${tomorrow.daypower}级"
                        card.setCardBackgroundColor(
                            ColorStateList.valueOf(
                                if (weather.text.contains("阴")
                                    || weather.text.contains("云")
                                ) {
                                    ContextCompat.getColor(
                                        context,
                                        R.color.wind
                                    )
                                } else if (weather.text.contains("晴")) {
                                    ContextCompat.getColor(
                                        context,
                                        R.color.sun
                                    )
                                } else if (weather.text.contains("雨")
                                    || weather.text.contains("雷")
                                ) {
                                    ContextCompat.getColor(
                                        context,
                                        R.color.rain
                                    )
                                } else if (weather.text.contains("雾")) {
                                    ContextCompat.getColor(
                                        context,
                                        R.color.fog
                                    )
                                } else {
                                    ContextCompat.getColor(
                                        context,
                                        androidx.cardview.R.color.cardview_light_background
                                    )
                                }

                            )
                        )
                    }
                }
            }.models = weathers
            state.onRefresh {
                scope {
                    delay(500L)
                    val request1 = Get<Weather>("/weatherInfo") {
                        param("key", Config.key)
                        param("extensions", "all")
                        param("city", cities[0])
                    }
                    val request2 = Get<Weather>("/weatherInfo") {
                        param("key", Config.key)
                        param("extensions", "all")
                        param("city", cities[1])
                    }
                    val request3 = Get<Weather>("/weatherInfo") {
                        param("key", Config.key)
                        param("extensions", "all")
                        param("city", cities[2])
                    }
                    val request4 = Get<Weather>("/weatherInfo") {
                        param("key", Config.key)
                        param("extensions", "all")
                        param("city", cities[3])
                    }
                    val request5 = Get<Weather>("/weatherInfo") {
                        param("key", Config.key)
                        param("extensions", "all")
                        param("city", cities[4])
                    }
                    val request6 = Get<Weather>("/weatherInfo") {
                        param("key", Config.key)
                        param("extensions", "all")
                        param("city", cities[5])
                    }

                    val result1 = request1.await()
                    val result2 = request2.await()
                    val result3 = request3.await()
                    val result4 = request4.await()
                    val result5 = request5.await()
                    val result6 = request6.await()
                    if ((result1.forecasts?.get(0)?.casts?.size ?: 0) > 2) {
                        result1.forecasts?.get(0)?.let {
                            weathers.add(it)
                        }
                    }
                    if ((result2.forecasts?.get(0)?.casts?.size ?: 0) > 2) {
                        result2.forecasts?.get(0)?.let {
                            weathers.add(it)
                        }
                    }
                    if ((result3.forecasts?.get(0)?.casts?.size ?: 0) > 2) {
                        result3.forecasts?.get(0)?.let {
                            weathers.add(it)
                        }
                    }
                    if ((result4.forecasts?.get(0)?.casts?.size ?: 0) > 2) {
                        result4.forecasts?.get(0)?.let {
                            weathers.add(it)
                        }
                    }
                    if ((result5.forecasts?.get(0)?.casts?.size ?: 0) > 2) {
                        result5.forecasts?.get(0)?.let {
                            weathers.add(it)
                        }
                    }
                    if ((result6.forecasts?.get(0)?.casts?.size ?: 0) > 2) {
                        result6.forecasts?.get(0)?.let {
                            weathers.add(it)
                        }
                    }
                }.catch {
                    it.printStackTrace()
                    showError()
                }
            }.refreshing()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}