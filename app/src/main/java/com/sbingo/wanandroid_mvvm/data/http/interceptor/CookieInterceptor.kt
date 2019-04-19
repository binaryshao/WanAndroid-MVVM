package com.sbingo.wanandroid_mvvm.data.http.interceptor

import com.sbingo.wanandroid_mvvm.HttpConstants
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Author: Sbingo666
 * Date:   2019/4/3
 */
class CookieInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val requestUrl = request.url().toString()
        val host = request.url().host()
        if ((requestUrl.contains(HttpConstants.SAVE_USER_LOGIN_KEY)
                    || requestUrl.contains(HttpConstants.SAVE_USER_REGISTER_KEY))
            && response.headers(HttpConstants.COOKIE_HEADER_RESPONSE).isNotEmpty()
        ) {
            val cookies = response.headers(HttpConstants.COOKIE_HEADER_RESPONSE)
            val cookie = HttpConstants.encodeCookie(cookies)
            HttpConstants.saveCookie(requestUrl, host, cookie)
        }
        return response
    }
}