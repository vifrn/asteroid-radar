package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid

import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(getApplication<Application>().applicationContext)
    private val repository = AsteroidsRepository(database)

    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
        }
    }

    val asteroids = repository.asteroids

    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid>()

    val navigateToAsteroidDetails : LiveData<Asteroid>
        public get() = _navigateToAsteroidDetails

    fun onAsteroidClicked (asteroid : Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }

    fun navigationToAsteroidDetailsDone() {
        _navigateToAsteroidDetails.value = null
    }

}