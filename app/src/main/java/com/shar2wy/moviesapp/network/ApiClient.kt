package com.shar2wy.moviesapp.network


import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Log
import com.shar2wy.moviesapp.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit


const val CONNECT_TIMEOUT = "CONNECT_TIMEOUT"
const val READ_TIMEOUT = "READ_TIMEOUT"
const val WRITE_TIMEOUT = "WRITE_TIMEOUT"

/**
 * Created by shar2wy
 * on 4/2/18.
 * Description: description goes here
 */
class ApiClient {

    private val TAG = ApiClient::class.java.simpleName


    //new
    private fun authInterceptor(): Interceptor {
        return Interceptor {
            val newRequest: Request = it.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Request-Type", "Android")
                    .addHeader("Content-Type", "application/json")

                    .build()
            it.proceed(newRequest)
        }
    }

    private var services: ConcurrentHashMap<Class<*>, Any> = ConcurrentHashMap()
    private var okHttpClient: OkHttpClient = buildOkHttpClient()
    private var retrofit: Retrofit = buildRetrofit(okHttpClient)

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(authInterceptor())
                .addInterceptor(loggingInterceptor())
                .addInterceptor(timeoutInterceptor())
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    private fun buildRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
    }


    private fun loggingInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private fun timeoutInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()

            var connectTimeout = chain.connectTimeoutMillis()
            var readTimeout = chain.readTimeoutMillis()
            var writeTimeout = chain.writeTimeoutMillis()

            val connectNew = request.header(CONNECT_TIMEOUT)
            val readNew = request.header(READ_TIMEOUT)
            val writeNew = request.header(WRITE_TIMEOUT)

            Log.d(TAG, "connectTimeout: $connectTimeout, readTimeout: $readTimeout, writeTimeout: $writeTimeout")
            Log.d(TAG, "connectNew: $connectNew, readNew: $readNew, writeNew: $writeNew")

            if (!TextUtils.isEmpty(connectNew)) {
                connectTimeout = Integer.valueOf(connectNew)
            }
            if (!TextUtils.isEmpty(readNew)) {
                readTimeout = Integer.valueOf(readNew)
            }
            if (!TextUtils.isEmpty(writeNew)) {
                writeTimeout = Integer.valueOf(writeNew)
            }

            val builder = request.newBuilder()
            builder.removeHeader(CONNECT_TIMEOUT)
            builder.removeHeader(READ_TIMEOUT)
            builder.removeHeader(WRITE_TIMEOUT)

            chain
                    .withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                    .withReadTimeout(readTimeout, TimeUnit.MILLISECONDS)
                    .withWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                    .proceed(builder.build())
        }
    }


    @Suppress("UNCHECKED_CAST")
    fun <T> getService(cls: Class<T>): T {
        if (!services.contains(cls)) {
            services.putIfAbsent(cls, retrofit.create(cls)!!)
        }
        return services[cls] as T
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: ApiClient? = null

        @Synchronized
        @JvmStatic
        fun getInstance(): ApiClient {
            INSTANCE?.let { instance ->
                return instance
            } ?: run {
                INSTANCE = ApiClient()
                return INSTANCE as ApiClient
            }
        }
    }
}
