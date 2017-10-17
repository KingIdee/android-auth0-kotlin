package com.idee.android_auth0_kotlin

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by idee on 10/17/17.
 */
class PreferenceUtils(private val context: Context) {

    private lateinit var sharedPref: SharedPreferences
    private val API_TOKEN = "token"

    init {
        sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE)
    }

    fun storeAPIToken(token: String) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putString(API_TOKEN, token)
        editor.apply()
    }

    fun getApiToken(): String {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(API_TOKEN,
                        "")
    }

}