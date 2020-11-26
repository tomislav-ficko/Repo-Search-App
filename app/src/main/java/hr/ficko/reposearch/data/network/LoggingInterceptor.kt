package hr.ficko.reposearch.data.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val sendTimestamp = System.nanoTime()
        Timber.d(
            String.format(
                "Sending request %s on %s%n%s",
                request.url,
                chain.connection(),
                request.headers
            )
        )
        val response = chain.proceed(request)

        val receiveTimestamp = System.nanoTime()
        Timber.d(
            String.format(
                "Received response for %s in %.1fms%n%s",
                response.request.url,
                (receiveTimestamp - sendTimestamp),
                response.headers
            )
        )

        return response
    }

}