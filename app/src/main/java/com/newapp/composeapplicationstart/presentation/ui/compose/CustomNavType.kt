package com.newapp.composeapplicationstart.presentation.ui.compose

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.newapp.composeapplicationstart.domain.model.response.TrendingMovieResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {
    val Movie = object : NavType<TrendingMovieResponse?>(isNullableAllowed = true) {
        override fun get(
            bundle: Bundle,
            key: String
        ): TrendingMovieResponse? {
        return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): TrendingMovieResponse? {
        return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(
            bundle: Bundle,
            key: String,
            value: TrendingMovieResponse?
        ) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: TrendingMovieResponse?): String {
            return Uri.encode(Json.encodeToString(value))
        }
    }
}
