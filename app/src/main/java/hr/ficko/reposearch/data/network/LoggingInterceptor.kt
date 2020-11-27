package hr.ficko.reposearch.data.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Timber.d(String.format("Sending request to " + request.url))

        val response = chain.proceed(request)
        Timber.d(String.format("Response: " + response.code + " " + response.message))

        return response
    }
}