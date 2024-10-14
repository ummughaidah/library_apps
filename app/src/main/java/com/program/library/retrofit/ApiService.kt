package com.program.library.retrofit

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiService {

    val HOSTNAME: String = "www.googleapis.com"
    val BASE_URL: String = "https://www.googleapis.com/books/v1/"
    val endpoint: ApiEndpoint
    get() {
        // retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiEndpoint::class.java)
    }

    // interceptor okhttp
    private val client: OkHttpClient
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .certificatePinner(certificatePinner)
                .build()
        }

    // certificate pinner
    val certificatePinner = CertificatePinner.Builder()
        .add(HOSTNAME, "sha256/I2gten7TWVtctfEmZjVPmk3P+F6MLHtKQe++RnKCw/Q=")
        .add(HOSTNAME, "sha256/YPtHaftLw6/0vnc2BnNKGF54xiCA28WFcccjkA4ypCM=")
        .add(HOSTNAME, "sha256/hxqRlPTu1bMS/0DITB1SSu0vd4u/8l8TjPgfaAp63Gc=")
        .add(HOSTNAME, "sha256/eOZSzqff7I3KnC9InTaRS++pdqQrB3arBD7CtCKnA1c=")
        .build()
}