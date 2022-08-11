package com.udacity.asteroidradar.repository

import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.network.AsteroidsApi
import com.udacity.asteroidradar.network.asDatabaseModel
import com.udacity.asteroidradar.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.await

class AsteroidsRepository (private val database: AsteroidDatabase) {

    val asteroids = Transformations.map(database.asteroidDao.getAsteroids()) {
        it.asDomainModel()
    }

    suspend fun refreshAsteroids () {
        withContext(Dispatchers.IO) {
            val nextDays = getNextSevenDaysFormattedDates()
            val asteroids = parseAsteroidsJsonResult(
                JSONObject(
                    AsteroidsApi.retroFitService.getAsteroids(nextDays.first(), nextDays.last()).await()
                )
            )
            database.asteroidDao.insertAsteroids(*asteroids.asDatabaseModel())
        }
    }


}