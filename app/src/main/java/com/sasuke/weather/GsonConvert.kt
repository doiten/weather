package com.sasuke.weather

import com.drake.net.convert.JSONConvert
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

class GsonConvert : JSONConvert("1", "status", "info") {
    private val gson = GsonBuilder().serializeNulls().create()
    override fun <S> String.parseBody(succeed: Type): S? {
        return gson.fromJson(this, succeed)
    }
}