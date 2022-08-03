package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.api.AsteroidsApi
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()

    val response : LiveData<String>
        get() = _response

    init {
        queryNW()
    }

    fun queryNW() {
        val nextDays = getNextSevenDaysFormattedDates()
        AsteroidsApi.retroFitService.getAsteroids(nextDays.first(), nextDays.last()).enqueue( object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                _response.value = response.body()
            }
        })
    }
}