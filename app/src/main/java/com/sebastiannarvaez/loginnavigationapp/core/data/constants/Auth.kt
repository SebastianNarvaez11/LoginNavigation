package com.sebastiannarvaez.loginnavigationapp.core.data.constants

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    val USER_DATA_KEY = stringPreferencesKey("user_data")
}