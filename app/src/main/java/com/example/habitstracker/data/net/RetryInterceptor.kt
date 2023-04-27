package com.example.habitstracker.data.net

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

private const val delay = 30000L

// Класс для реализации перехватчика повторов
class RetryInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        while (!response.isSuccessful) {
            Log.d("RetryInterceptor", "Request failed")
            // Задержка перед повтором
            Thread.sleep(delay)
            response = chain.proceed(request)
        }
        return response
    }
}