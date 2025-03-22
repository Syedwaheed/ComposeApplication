package com.newapp.mocks

import com.newapp.composeapplicationstart.presentation.utils.EncryptedSharedPrefKeyValue
import io.mockk.mockk

class EncryptedSharedPrefKeyValue : EncryptedSharedPrefKeyValue(context = mockk(relaxed = true)) {
}