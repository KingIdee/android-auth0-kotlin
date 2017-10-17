package com.idee.android_auth0_kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.auth0.android.Auth0
import com.auth0.android.jwt.JWT
import com.auth0.android.lock.AuthenticationCallback
import com.auth0.android.lock.Lock
import com.auth0.android.lock.utils.LockException
import com.auth0.android.result.Credentials

class MainActivity : AppCompatActivity() {

    private lateinit var lock: Lock
    lateinit var preferenceUtils:PreferenceUtils
    lateinit var jwt:JWT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val auth0 = Auth0(this)
        auth0.isOIDCConformant = true

        preferenceUtils = PreferenceUtils(this@MainActivity)
        lock = Lock.newBuilder(auth0, callback).build(this)

        val token = preferenceUtils.getApiToken()
        if(token.equals("")){
            startActivity(lock.newIntent(this))
            return
        }

        jwt = JWT(token)

        if (jwt.isExpired(0)){
            // start LockActivity
            startActivity(lock.newIntent(this))

        } else {
            // still okay
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        lock.onDestroy(this)
    }

    private val callback = object : AuthenticationCallback() {
        override fun onAuthentication(credentials: Credentials) {
            //Authenticated
            val token = credentials.idToken!!
            jwt = JWT(token)
            if (jwt.issuer.equals("https://idee.auth0.com/")){
                // it is authentic

                if(jwt.audience?.get(0)!!.equals(getString(R.string.com_auth0_client_id))){
                    // it is authentic

                    if (jwt.signature.equals("RS256")){
                        //RS256 is the default
                        //Final point to accept a token

                        preferenceUtils.storeAPIToken(token)
                    } else {
                        // discard it
                    }

                } else {
                    // discard token
                }


            } else {
                // discard the token
            }

        }

        override fun onCanceled() {
            //User pressed back
            Log.d("TAG","onCancelled")
        }

        override fun onError(error: LockException) {
            //Exception occurred
            Log.d("TAG",error.message)
        }
    }

}
