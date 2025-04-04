package com.newapp.composeapplicationstart.presentation.ui.compose

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

object GenericNavTypeFactory {
    inline fun <reified T> createNavType(
        isNullableAllowed: Boolean = false
    ): NavType<T> {
        val serializer = kotlinx.serialization.serializer<T>()
        return object : NavType<T>(isNullableAllowed) {
            override fun put(bundle: Bundle, key: String, value: T) {
                bundle.putString(key, Json.encodeToString(serializer, value))
            }

            override fun get(bundle: Bundle, key: String): T {
                return Json.decodeFromString(serializer, bundle.getString(key)!!)
            }

            override fun parseValue(value: String): T {
                return Json.decodeFromString(serializer, URLDecoder.decode(value, "UTF-8"))
            }

            override fun serializeAsValue(value: T): String {
                return URLEncoder.encode(Json.encodeToString(serializer, value), "UTF-8")
            }
        }
    }
}

