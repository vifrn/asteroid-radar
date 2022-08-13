package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.getTodayFormattedDate

import com.udacity.asteroidradar.database.asteroid.getAsteroidDatabase
import com.udacity.asteroidradar.database.iodt.getPictureDatabase
import com.udacity.asteroidradar.network.asDomainModel
import com.udacity.asteroidradar.repository.AsteroidFilter
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application) {

    private val asteroidDatabase = getAsteroidDatabase(getApplication<Application>().applicationContext)
    private val pictureDatabase = getPictureDatabase(getApplication<Application>().applicationContext)
    private val repository = AsteroidsRepository(asteroidDatabase, pictureDatabase)

    private val _filter = MutableLiveData<AsteroidFilter>(AsteroidFilter.ALL)

    val asteroids = Transformations.switchMap(_filter) { filter ->
        when(filter) {
            AsteroidFilter.WEEK -> {
                asteroidDatabase.asteroidDao.getWeekAsteroids(getTodayFormattedDate()).map {
                    it.asDomainModel()
                }
            }
            AsteroidFilter.TODAY -> {
                asteroidDatabase.asteroidDao.getTodayAsteroids(getTodayFormattedDate()).map {
                    it.asDomainModel() }
            }
            AsteroidFilter.ALL -> {
                asteroidDatabase.asteroidDao.getAsteroids().map {
                    it.asDomainModel()
                }
            }
        }
    }
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

    fun updateFilter (filter : AsteroidFilter) {
        _filter.value = filter
    }

}