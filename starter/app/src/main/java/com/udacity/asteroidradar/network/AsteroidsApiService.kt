package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val asteroid = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

private val picture = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

object AsteroidsApi {
    val asteroidService : AsteroidsApiService by lazy {
        asteroid.create(AsteroidsApiService::class.java)
    }

    val pictureService : PictureApiService by lazy {
        picture.create(PictureApiService::class.java)
    }
}

interface AsteroidsApiService {
    @GET(Constants.ASTEROIDS_ENDPOINT)
    fun getAsteroids(
        @Query("start_date") startDate : String,
        @Query("end_date") endDate : String,
        @Query("api_key") apiKey : String = BuildConfig.NASA_API_KEY) : Call<String>
}

interface PictureApiService {
    @GET(Constants.IOTD_ENDPOINT)
    fun getImageOfTheDay (
        @Query("api_key") apiKey : String = BuildConfig.NASA_API_KEY) : Call<PictureOfDay>
}