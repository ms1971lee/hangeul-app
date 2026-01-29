package com.prs.hangeul

import android.app.Application
import android.util.Log
// Firebase disabled
// import com.google.firebase.FirebaseApp
// import com.google.firebase.messaging.FirebaseMessaging

class MyApplication : Application() {

    companion object {
        private const val TAG = "MyApplication"
    }

    override fun onCreate() {
        super.onCreate()

        // Firebase disabled
        // FirebaseApp.initializeApp(this)
        // getFCMToken()

        Log.d(TAG, "Application initialized")
    }

    // Firebase disabled
    /*
    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            val token = task.result
            Log.d(TAG, "FCM Token: $token")

            val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            prefs.edit().putString("fcm_token", token).apply()
        }
    }
    */
}
