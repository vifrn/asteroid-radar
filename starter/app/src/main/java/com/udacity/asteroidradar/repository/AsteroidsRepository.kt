package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.asteroid.AsteroidDatabase
import com.udacity.asteroidradar.database.iodt.PictureOfDayDatabase
import com.udacity.asteroidradar.network.AsteroidsApi
import com.udacity.asteroidradar.network.asDatabaseModel
import com.udacity.asteroidradar.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.await

class AsteroidsRepository (private val asteroidDatabase: AsteroidDatabase, private val pictureDatabase : PictureOfDayDatabase) {

    val asteroids = Transformations.map(asteroidDatabase.asteroidDao.getAsteroids()) {
        it.asDomainModel()
    }

    val imageOfTheDay = Transformations.map(pictureDatabase.pictureDao.getPictureOfDay()) {
        it?.asDomainModel()
    }

    suspend fun refreshAsteroids () {
        withContext(Dispatchers.IO) {
            try{
                val nextDays = getNextSevenDaysFormattedDates()
                val asteroids = parseAsteroidsJsonResult(
                    JSONObject(
                        AsteroidsApi.asteroidService.getAsteroids(nextDays.first(), nextDays.last()).await()
                    )
                )
                asteroidDatabase.asteroidDao.insertAsteroids(*asteroids.asDatabaseModel())
            } catch (e : Exception) {
                Log.e("Failure", "Something went wrong: " + e.message)
            }
        }
    }

    suspend fun refreshImageOfTheDay () {
        withContext(Dispatchers.IO) {
            try{
                fetchAndMaybeSavePicture()
            } catch (e : Exception) {
                Log.e("Failure", "Something went wrong: " + e.message)
            }
        }
    }

    private suspend fun fetchAndMaybeSavePicture() {
        val picture = AsteroidsApi.pictureService.getImageOfTheDay().await()
        if (picture.mediaType == "image"){
            pictureDatabase.pictureDao.insertPictureOfDay(picture.asDatabaseModel())
        }
    }

}