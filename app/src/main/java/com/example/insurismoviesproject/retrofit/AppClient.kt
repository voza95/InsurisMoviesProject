package com.example.insurismoviesproject.retrofit

import android.content.Context
import com.example.insurismoviesproject.retrofit.ConnectivityInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppClient {

    private var retrofit: Retrofit? = null

    val BASE_URL = "https://api.themoviedb.org/3/movie/"

    //550?api_key=e1ac10246df8f2617dee99db3dc1ac5f

    //eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlMWFjMTAyNDZkZjhmMjYxN2RlZTk5ZGIzZGMxYWM1ZiIsInN1YiI6IjVmZjJmNTVkMTU2Y2M3MDA0MGYyYmViMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.q4OiGZKWSjxzH621nFEl8yFn8r8CXt_cxQDKWs8RRgM

    //https://image.tmdb.org/t/p/original/srYya1ZlI97Au4jUYAktDe3avyA.jpg

    fun getClient(context: Context): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
            .readTimeout(300, TimeUnit.SECONDS)
            .connectTimeout(300, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(ConnectivityInterceptor(context))
            .addInterceptor(interceptor)


        val client = builder.build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return (retrofit)!!
    }

}