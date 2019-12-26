package com.johnathan.tgskelompok.MVC

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {

        var retrofit : Retrofit? = null

        var base_url : String = "http://172.16.10.246/BERITA/public/api/"

        fun getClient(): Retrofit? {
            if (retrofit == null) {
                var loggingInterceptor : HttpLoggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                var builder: OkHttpClient.Builder = OkHttpClient.Builder()
                builder.addInterceptor(loggingInterceptor)
                builder.connectTimeout(300, TimeUnit.SECONDS)
                builder.readTimeout(80, TimeUnit.SECONDS)
                builder.writeTimeout(90, TimeUnit.SECONDS)
                var gson: Gson = GsonBuilder().setLenient().create()
                retrofit = Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(builder.build())
                    .build()
            }
            return retrofit

        }

    }

}