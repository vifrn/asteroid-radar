package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.network.AsteroidsApi
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _response = MutableLiveData<List<Asteroid>>()

    val response : LiveData<List<Asteroid>>
        get() = _response

    init {
        queryNW()
    }

    fun queryNW() {
        val nextDays = getNextSevenDaysFormattedDates()
        AsteroidsApi.retroFitService.getAsteroids(nextDays.first(), nextDays.last()).enqueue( object: Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("NW Failure", t.message.toString())
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    val asteroids = parseAsteroidsJsonResult(JSONObject(it))
                    _response.value = asteroids
                    return
                }
                Log.d("NW Response", "There's no asteroids around")
            }
        })
    }
}