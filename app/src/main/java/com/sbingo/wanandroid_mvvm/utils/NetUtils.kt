package com.sbingo.wanandroid_mvvm.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import android.text.TextUtils

class NetUtils private constructor() {

    companion object {
        /**
         * 没有网络
         */
        val NETWORK_TYPE_INVALID = 0
        /**
         * wap网络
         */
        val NETWORK_TYPE_WAP = 1
        /**
         * 2G网络
         */
        val NETWORK_TYPE_2G = 2
        /**
         * 3G和3G以上网络，或统称为快速网络
         */
        val NETWORK_TYPE_3G = 3
        /**
         * wifi网络
         */
        val NETWORK_TYPE_WIFI = 4

        /**
         * 判断网络是否连接
         *
         * @param context
         * @return
         */
        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val ni = cm.activeNetworkInfo
            return ni != null && ni.isConnected
        }

        /**
         * 判断是否是wifi连接
         */
        fun isWifi(context: Context): Boolean {
            return getNetWorkType(context) == NETWORK_TYPE_WIFI
        }

        fun getWifiName(context: Context): String {
            val wifiInfo =
                (context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager).connectionInfo
            return if (wifiInfo != null) wifiInfo.ssid else ""
        }

        /**
         * 获取网络状态，wifi,wap,2g,3g.
         *
         * @param context 上下文
         * @return int 网络状态 [.NETWORK_TYPE_2G],[.NETWORK_TYPE_3G],
         * [.NETWORK_TYPE_INVALID],[.NETWORK_TYPE_WAP],[.NETWORK_TYPE_WIFI]
         */
        fun getNetWorkType(context: Context): Int {
            var netWorkType = -1
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                val type = networkInfo.typeName
                if (type.equals("WIFI", ignoreCase = true)) {
                    netWorkType = NETWORK_TYPE_WIFI
                } else if (type.equals("MOBILE", ignoreCase = true)) {
                    val proxyHost = android.net.Proxy.getDefaultHost()
                    netWorkType = if (TextUtils.isEmpty(proxyHost))
                        if (isFastMobileNetwork(context)) NETWORK_TYPE_3G else NETWORK_TYPE_2G
                    else
                        NETWORK_TYPE_WAP
                }
            } else {
                netWorkType = NETWORK_TYPE_INVALID
            }
            return netWorkType
        }

        private fun isFastMobileNetwork(context: Context): Boolean {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            when (telephonyManager.networkType) {
                TelephonyManager.NETWORK_TYPE_1xRTT -> return false // ~ 50-100 kbps
                TelephonyManager.NETWORK_TYPE_CDMA -> return false // ~ 14-64 kbps
                TelephonyManager.NETWORK_TYPE_EDGE -> return false // ~ 50-100 kbps
                TelephonyManager.NETWORK_TYPE_EVDO_0 -> return true // ~ 400-1000 kbps
                TelephonyManager.NETWORK_TYPE_EVDO_A -> return true // ~ 600-1400 kbps
                TelephonyManager.NETWORK_TYPE_GPRS -> return false // ~ 100 kbps
                TelephonyManager.NETWORK_TYPE_HSDPA -> return true // ~ 2-14 Mbps
                TelephonyManager.NETWORK_TYPE_HSPA -> return true // ~ 700-1700 kbps
                TelephonyManager.NETWORK_TYPE_HSUPA -> return true // ~ 1-23 Mbps
                TelephonyManager.NETWORK_TYPE_UMTS -> return true // ~ 400-7000 kbps
                TelephonyManager.NETWORK_TYPE_EHRPD -> return true // ~ 1-2 Mbps
                TelephonyManager.NETWORK_TYPE_EVDO_B -> return true // ~ 5 Mbps
                TelephonyManager.NETWORK_TYPE_HSPAP -> return true // ~ 10-20 Mbps
                TelephonyManager.NETWORK_TYPE_IDEN -> return false // ~25 kbps
                TelephonyManager.NETWORK_TYPE_LTE -> return true // ~ 10+ Mbps
                TelephonyManager.NETWORK_TYPE_UNKNOWN -> return false
                else -> return false
            }
        }
    }
}