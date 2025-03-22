package com.newapp.mocks

import com.newapp.composeapplicationstart.di.EncryptedSharedPref
import com.newapp.composeapplicationstart.presentation.utils.EncryptedSharedPrefKeyValue
import io.mockk.mockk

class MockEncryptedSharedPref : EncryptedSharedPrefKeyValue(context = mockk(relaxed = true)){
    override fun addAccessToken() {

    }

    override fun getAccessToken(): String? {
        return "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5NzYwZWM2MzQ2ZGU4ZjhiODc2OTVhNGVmNDFkNzZhNCIsIm5iZiI6MTUwMjc3OTY3MC4yOTksInN1YiI6IjU5OTI5OTEzYzNhMzY4NDBkNDAwOGVlNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.IPQzTLy7NBX7RIDNXD_xNW73_7al_WaYQmMRMWbLdqI"
    }
}