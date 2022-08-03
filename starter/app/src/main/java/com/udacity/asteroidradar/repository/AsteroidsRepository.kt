package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabase

class AsteroidsRepository (private val database: AsteroidDatabase) {

    private val _asteroids = MutableLiveData<List<Asteroid>>()

    val asteroids : LiveData<List<Asteroid>>
        get() = _asteroids

    fun refreshAsteroids () {
        //TODO: Query NW to get new Asteroids data and updateDB
    }


}