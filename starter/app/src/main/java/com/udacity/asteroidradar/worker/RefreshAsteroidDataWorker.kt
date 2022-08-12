package com.udacity.asteroidradar.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.asteroid.getAsteroidDatabase
import com.udacity.asteroidradar.database.iodt.getPictureDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import retrofit2.HttpException

class RefreshAsteroidDataWorker (appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshAsteroidDataWorker"
    }

    override suspend fun doWork(): Result {
        val asteroidDatabase = getAsteroidDatabase(applicationContext)
        val pictureDatabase = getPictureDatabase(applicationContext)
        val repository = AsteroidsRepository(asteroidDatabase, pictureDatabase)

        Log.d("Worker", "working")

        return try {
            repository.refreshAsteroids()
            repository.refreshImageOfTheDay()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}