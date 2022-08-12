package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid

import com.udacity.asteroidradar.database.asteroid.getAsteroidDatabase
import com.udacity.asteroidradar.database.iodt.getPictureDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application) {

    private val asteroidDatabase = getAsteroidDatabase(getApplication<Application>().applicationContext)
    private val pictureDatabase = getPictureDatabase(getApplication<Application>().applicationContext)
    private val repository = AsteroidsRepository(asteroidDatabase, pictureDatabase)

    val asteroids = repository.asteroids
    val imageOfTheDay = repository.imageOfTheDay

    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetails : LiveData<Asteroid>
        get() = _navigateToAsteroidDetails


    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
            repository.refreshImageOfTheDay()
        }
    }

    fun onAsteroidClicked (asteroid : Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }

    fun navigationToAsteroidDetailsDone() {
        _navigateToAsteroidDetails.value = null
    }

}