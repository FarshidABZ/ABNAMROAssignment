package com.abnamro.core.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit


open class BaseNetworkTest {
    protected lateinit var mockWebServer: MockWebServer
    private lateinit var retrofit: Retrofit

    @Before
    open fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val jsonConfiguration = Json { ignoreUnknownKeys = true }

        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(jsonConfiguration.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    fun <T> getApiService(apiServiceInterfaceClass: Class<T>): T {
        return retrofit.create(apiServiceInterfaceClass)
    }

    @After
    open fun tearDown() {
        mockWebServer.shutdown()
    }
}