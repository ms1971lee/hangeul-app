package com.prs.hangeul

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast
import com.google.gson.Gson

class WebAppInterface(private val context: Context) {

    @JavascriptInterface
    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun showLongToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    @JavascriptInterface
    fun getDeviceInfo(): String {
        val deviceInfo = mapOf(
            "model" to android.os.Build.MODEL,
            "manufacturer" to android.os.Build.MANUFACTURER,
            "androidVersion" to android.os.Build.VERSION.RELEASE,
            "sdkVersion" to android.os.Build.VERSION.SDK_INT,
            "isTablet" to isTablet()
        )
        return Gson().toJson(deviceInfo)
    }

    @JavascriptInterface
    fun getFCMToken(): String {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return prefs.getString("fcm_token", "") ?: ""
    }

    @JavascriptInterface
    fun isTablet(): Boolean {
        val screenLayout = context.resources.configuration.screenLayout
        val screenSize = screenLayout and android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK
        return screenSize >= android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    @JavascriptInterface
    fun getAppVersion(): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: Exception) {
            "1.0.0"
        }
    }

    @JavascriptInterface
    fun vibrate(duration: Long = 100) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as android.os.Vibrator
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            vibrator.vibrate(
                android.os.VibrationEffect.createOneShot(
                    duration,
                    android.os.VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
        }
    }

    @JavascriptInterface
    fun log(message: String) {
        android.util.Log.d("WebAppInterface", message)
    }
}
