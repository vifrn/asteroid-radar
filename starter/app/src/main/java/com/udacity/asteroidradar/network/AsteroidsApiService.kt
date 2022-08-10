package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

object AsteroidsApi {
    val retroFitService : AsteroidsApiService by lazy {
        retrofit.create(AsteroidsApiService::class.java)
    }
}

interface AsteroidsApiService {

    @GET(Constants.ASTEROIDS_ENDPOINT)
    fun getAsteroids(
        @Query("start_date") startDate : String,
        @Query("end_date") endDate : String,
        @Query("api_key") apiKey : String = Constants.API_KEY) : Call<String>
}