package com.sasuke.weather.data

data class Weather(
    val count: String? = null,
    val forecasts: List<Forecast>? = null,
    val info: String,
    val infocode: String,
    val status: String              //值为0或1 1：成功；0：失败
)

data class Forecast(
    val adcode: String,             //城市编码
    val casts: List<Cast>,          //预报数据list结构，元素cast,按顺序为当天、第二天、第三天的预报数据
    val city: String,               //城市名称
    val province: String,           //省份名称
    val reporttime: String          //预报发布时间
)

data class Cast(
    val date: String,               //日期
    val week: String,               //星期几
    val daypower: String,           //白天风力
    val daytemp: String,            //白天温度
    val dayweather: String,         //白天天气现象
    val daywind: String,            //白天风向
    val daytemp_float: String,
    val nightpower: String,         //晚上风力
    val nighttemp: String,          //晚上温度
    val nightweather: String,       //晚上天气现象
    val nightwind: String,          //晚上风向
    val nighttemp_float: String,
)