package com.idee.android_auth0_kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.auth0.android.Auth0
import com.auth0.android.lock.AuthenticationCallback
import com.auth0.android.lock.Lock
import com.auth0.android.lock.LockCallback
import com.auth0.android.lock.utils.LockException
import com.auth0.android.result.Credentials

class MainActivity : AppCompatActivity() {

    private lateinit var lock: Lock

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val auth0 = Auth0(this)
        auth0.setOIDCConformant(true)
        lock = Lock.newBuilder(auth0, callback)
                .build(this)
        startActivity(lock.newIntent(this));
    }

    override fun onDestroy() {
        super.onDestroy();
        lock.onDestroy(this)
    }

    private val callback = object : AuthenticationCallback() {
        override fun onAuthentication(credentials: Credentials) {
            //Authenticated
        }

        override fun onCanceled() {
            //User pressed back
        }

        override fun onError(error: LockException) {
            //Exception occurred
        }
    }

}
