package com.example.insurismoviesproject.retrofit

import com.example.insurismoviesproject.retrofit.responces.*
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.*

interface ApiInterface {

    @GET(URLHelper.popular)
    fun popularMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String?="en-US"
    ): Observable<PopularMoviesListResponse>


    @GET("{movie_id}")
    fun popularMoviesDetails(
        @Path("movie_id") movie_id:String,
        @Query("api_key") api_key: String,
        @Query("language") language: String?="en-US"
    ): Observable<MoviesDetailsResponse>

    @POST("{movie_id}/"+URLHelper.rating)
    @FormUrlEncoded
    fun planHistoryCurrent(
        @Path("movie_id") movie_id:String,
        @Query("api_key") api_key: String,
        @Query("language") language: String?="en-US",
        @Field("value") value: String = "0.0"
    ): Observable<Any>
}