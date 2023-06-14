package com.bancom.core.extension

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.view.WindowManager
import androidx.annotation.RequiresApi

fun Context.isAirplaneModeActive(): Boolean {
    return Settings.Global.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
}


@RequiresApi(Build.VERSION_CODES.M)
fun Context.isConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.getNetworkCapabilities(cm.activeNetwork)?.hasCapability((NetworkCapabilities.NET_CAPABILITY_INTERNET)) ?: false
}

fun Activity.setStatusBarColor(color: Int) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.statusBarColor = color
}

fun Context.screenSize(sizeType: CoreSizeType) : Int {
    val displayMetrics = resources.displayMetrics
    return if (sizeType == CoreSizeType.HEIGHT) displayMetrics.heightPixels else displayMetrics.widthPixels
}

enum class CoreSizeType {
    HEIGHT,
    WIDTH;
}